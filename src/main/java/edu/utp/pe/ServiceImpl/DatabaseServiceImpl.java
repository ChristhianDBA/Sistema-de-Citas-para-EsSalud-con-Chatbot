package edu.utp.pe.ServiceImpl;

import edu.utp.pe.Service.DatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@Service
public class DatabaseServiceImpl implements DatabaseService {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public List<Map<String, Object>> getDatabaseContent() {
        try {
            // Usar SimpleJdbcCall para manejar el procedimiento
            SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                    .withProcedureName("sp_VerContenidoTablas");

            // Ejecutar el procedimiento almacenado y obtener los resultados
            Map<String, Object> result = jdbcCall.execute();

            // Verifica todo lo que devuelve el procedimiento almacenado
            System.out.println("Resultado del procedimiento almacenado: " + result);

            // Si el procedimiento devuelve múltiples conjuntos de resultados
            if (result != null && !result.isEmpty()) {
                List<Map<String, Object>> allResults = new ArrayList<>();  // Lista para almacenar todos los resultados

                // Iterar sobre todos los conjuntos de resultados
                for (String key : result.keySet()) {
                    System.out.println("Clave del resultado: " + key);
                    Object value = result.get(key);

                    // Verifica si el valor es un List (esto representa un conjunto de resultados)
                    if (value instanceof List) {
                        List<Map<String, Object>> resultList = (List<Map<String, Object>>) value;

                        // Si el conjunto tiene datos, agregarlo a la lista de resultados
                        if (!resultList.isEmpty()) {
                            System.out.println("Datos del conjunto de resultados (" + key + "): " + resultList);
                            allResults.addAll(resultList);  // Agregar los resultados a la lista final
                        } else {
                            System.out.println("El conjunto de resultados (" + key + ") está vacío.");
                        }
                    } else {
                        System.out.println("Valor del resultado: " + value);
                    }
                }

                // Devuelve todos los resultados recopilados
                return allResults;
            }

            return null;  // Si no se encuentra ningún conjunto de resultados

        } catch (DataAccessException e) {
            System.out.println("Error ejecutando el procedimiento almacenado: " + e.getMessage());
            return null;
        }
    }
}
