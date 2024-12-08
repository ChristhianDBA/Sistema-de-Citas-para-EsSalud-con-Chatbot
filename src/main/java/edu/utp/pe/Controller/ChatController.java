package edu.utp.pe.Controller;

import edu.utp.pe.Repository.EspecialidadRepository;
import edu.utp.pe.Repository.DoctorRepository;
import edu.utp.pe.Repository.CitaRepository;
import edu.utp.pe.Entity.Especialidad;
import edu.utp.pe.Entity.Doctor;
import edu.utp.pe.Entity.Cita;

import jakarta.servlet.http.HttpSession;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    @Value("${openai.api.key}")
    private String apiKey;

    @Autowired
    private EspecialidadRepository especialidadRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private CitaRepository citaRepository;

    @GetMapping("/welcome")
    public String getWelcomeMessage() {
        return "¡Bienvenido al chatbot de EsSalud! Estoy aquí para ayudarte con información sobre nuestros servicios médicos, doctores disponibles y citas. ¿En qué puedo asistirte hoy?";
    }

    @PostMapping("/ask")
    public String askChatbot(HttpSession session, @RequestBody String userMessage) throws Exception {
        List<JSONObject> chatHistory = (List<JSONObject>) session.getAttribute("chatHistory");
        if (chatHistory == null) {
            chatHistory = new ArrayList<>();
            session.setAttribute("chatHistory", chatHistory);

            JSONObject contextMessage = new JSONObject();
            contextMessage.put("role", "system");
            contextMessage.put("content", getInitialContextMessage());
            chatHistory.add(contextMessage);
        }

        JSONObject userMessageJson = new JSONObject();
        userMessageJson.put("role", "user");
        userMessageJson.put("content", userMessage);
        chatHistory.add(userMessageJson);

        JSONArray messages = new JSONArray(chatHistory);

        JSONObject requestBody = new JSONObject();
        requestBody.put("model", "gpt-3.5-turbo");
        requestBody.put("messages", messages);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.openai.com/v1/chat/completions"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + apiKey)
                .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
                .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        String responseBody = response.body();
        JSONObject jsonResponse = new JSONObject(responseBody);

        if (jsonResponse.has("choices")) {
            String content = jsonResponse
                    .getJSONArray("choices")
                    .getJSONObject(0)
                    .getJSONObject("message")
                    .getString("content");

            JSONObject assistantMessageJson = new JSONObject();
            assistantMessageJson.put("role", "assistant");
            assistantMessageJson.put("content", content);
            chatHistory.add(assistantMessageJson);

            return content;
        } else {
            return "Error: No se encontró 'choices' en la respuesta de la API.";
        }
    }

    private String getInitialContextMessage() {
        StringBuilder context = new StringBuilder();
        context.append("Eres un asistente virtual para una clínica médica. Tu tarea es ayudar a los pacientes a obtener información sobre nuestros servicios, doctores y citas disponibles. Responde de manera amable y profesional la solicitud del usuario.");
        context.append("Si el usuario solicita algo no relacionado con servicios, responde cortésmente y trata de redirigir la conversación hacia la salud  . Utiliza la siguiente información para responder a las consultas de los pacientes:\n\n");

        List<Especialidad> especialidades = especialidadRepository.findAll();
        context.append("Especialidades disponibles:\n");
        for (Especialidad esp : especialidades) {
            context.append("- ").append(esp.getNombre()).append("\n");
        }

        List<Doctor> doctores = doctorRepository.findAll();
        context.append("\nDoctores disponibles:\n");
        for (Doctor doc : doctores) {
            context.append("- Dr. ").append(doc.getNombre()).append(" ").append(doc.getApellido())
                    .append(" (Especialidad: ").append(doc.getEspecialidad().getNombre()).append(")\n");
        }
        List<Cita> citas = citaRepository.findAll();
        context.append("\nHorarios de citas reservados:\n");
        for (Cita cit : citas) {
            context.append("- ").append(cit.getFecha()).append(" ").append(cit.getHora()).append(" ").append(cit.getDoctor().getNombre()).append(" ").append(cit.getDoctor().getApellido()).append(" ").append(cit.getHora()).append("\n");
        }
        context.append("\nInformación general sobre citas:\n");
        context.append("- Total de citas programadas: ").append(citas.size()).append("\n");
        context.append("En caso el usuario requiera las citas disponibles devolverías rangos de horario que no se relacionen con las citas programadas.\n");
        context.append("- Horarios de atención: de 8:00 AM a 6:00 PM, de lunes a viernes\n");
        
        context.append("\nPor favor, utiliza esta información para responder a las preguntas de los pacientes sobre servicios recomendados, ");
        context.append("qué doctor podría atenderlos, horarios disponibles, etc. Si no tienes suficiente información para responder ");
        context.append("una pregunta específica, sugiere al paciente que se comunique directamente con la clínica para obtener más detalles.");

        return context.toString();
    }
}

