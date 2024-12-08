// Chatbot.js

document.getElementById("send-btn").addEventListener("click", sendMessage);


function sendMessage() {
  const userInput = document.getElementById("user-input").value.trim();
  if (userInput) {
    // Muestra el mensaje del usuario en el chat-box
    displayMessage(userInput, "user");

    // Genera una respuesta automática
    const botResponse = getBotResponse(userInput);
    displayMessage(botResponse, "bot");

    // Limpia el campo de entrada
    document.getElementById("user-input").value = "";
  }
}

function displayMessage(message, sender) {
  const chatBox = document.getElementById("chat-box");
  const messageElement = document.createElement("div");
  messageElement.className = sender === "user" ? "user-message" : "bot-message";
  messageElement.textContent = message;
  chatBox.appendChild(messageElement);
  chatBox.scrollTop = chatBox.scrollHeight;
}


function getBotResponse(input) {
  if (input.includes("hola")) return "¡Hola! ¿En qué puedo ayudarte?";
  if (input.includes("cita"))
    return "Puedes consultar o crear una cita ingresando tu DNI y datos en el formulario correspondiente.";
  if (input.includes("ayuda"))
    return "Estoy aquí para ayudarte con información sobre citas, horarios, y especialidades.";
  return "Lo siento, no entiendo esa pregunta. Por favor intenta de nuevo.";
}
