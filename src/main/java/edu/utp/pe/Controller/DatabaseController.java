package edu.utp.pe.Controller;

import edu.utp.pe.Service.DatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class DatabaseController {

    @Autowired
    private DatabaseService databaseService;

    @GetMapping("/api/database-content")
    public List<Map<String, Object>> getDatabaseContent() {
        // Llamar al servicio para obtener el contenido de la base de datos
        List<Map<String, Object>> content = databaseService.getDatabaseContent();

        // Verificar el contenido recibido antes de devolverlo
        if (content == null) {
            System.out.println("El contenido es null");
        } else if (content.isEmpty()) {
            System.out.println("El contenido está vacío");
        } else {
            System.out.println("Este es el contenido desde el controlador: " + content);
        }

        return content;
    }
}
