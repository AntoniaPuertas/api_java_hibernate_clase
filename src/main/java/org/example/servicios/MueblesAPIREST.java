package org.example.servicios;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.github.cdimascio.dotenv.Dotenv;
import org.example.dao.MuebleDAOInterface;
import org.example.entidades.Mueble;
import spark.Spark;

import java.util.ArrayList;
import java.util.List;

public class MueblesAPIREST {
    private MuebleDAOInterface dao;
    private Gson gson = new Gson();

    public MueblesAPIREST(MuebleDAOInterface implementacion){

        String puerto = System.getenv("PORT");

        // Configura el puerto
        int port = puerto != null ?
                Integer.parseInt(puerto) :
                Integer.parseInt(puerto);
        Spark.port(port);

        dao = implementacion;

        //endpoint para obtener todos los muebles
        Spark.get("/muebles", (request, response) -> {
            List<Mueble> muebles = dao.devolverTodos();
            response.type("application/json");
            response.status(200);
            return createJsonResponse("200", muebles);
        });

        //endpoint para obtener un mueble por su id
        Spark.get("muebles/id/:id", (request, response) -> {
            Long id = Long.parseLong(request.params(":id"));
            Mueble mueble = dao.buscarPorId(id);
            response.type("application/json");
            List<Mueble> muebleList = new ArrayList<>();
            if(mueble != null){
                muebleList.add(mueble);
                response.status(200);
                return createJsonResponse("200", muebleList);
            }else{
                response.status(404);
                return createJsonResponse("Mueble no encontrado", muebleList);
            }
        });

        Spark.post("/muebles", (request, response) -> {
            String body = request.body();
            //TODO comprobar si han llegado los datos correctos
            Mueble nuevoMueble = gson.fromJson(body, Mueble.class);

            //TODO comprobar si se creado el nuevoMueble
            Mueble creado = dao.create(nuevoMueble);

            response.type("application/json");
            List<Mueble> muebleList = new ArrayList<>();
            muebleList.add(creado);
            response.status(200);
            return createJsonResponse("200", muebleList);
        });

        Spark.put("muebles/id/:id", (request, response) -> {
            Long id = Long.parseLong(request.params(":id"));
            //TODO comprobar si han llegado los datos correctos
            String body = request.body();
            Mueble muebleActualizado = gson.fromJson(body, Mueble.class);
            muebleActualizado.setId(id);
            List<Mueble> muebleList = new ArrayList<>();
            Mueble actualizado = dao.actualizar(muebleActualizado);
            response.type("application/json");
            if(actualizado != null){

                muebleList.add(actualizado);
                response.status(200);
                return createJsonResponse("200", muebleList);
            }else{
                response.status(404);
                return createJsonResponse("No se ha podido realizar la actualización", muebleList);
            }
        });

        Spark.delete("/muebles/id/:id", (request, response) -> {
            Long id = Long.parseLong(request.params(":id"));
            boolean eliminado = dao.deleteById(id);
            response.type("application/json");
            List<Mueble> muebleList = new ArrayList<>();
            if(eliminado){
                response.status(200);
                return createJsonResponse("Mueble eliminado correctamente", muebleList);
            }else{
                response.status(404);
                return createJsonResponse("No se puedo realizar la eliminación", muebleList);
            }
        });
    }

//    public String createJsonResponse(String status, String message) {
//        Gson gson = new Gson();
//
//        JsonObject jsonResponse = new JsonObject();
//        jsonResponse.addProperty("status", status);
//        jsonResponse.addProperty("message", message);
//        return gson.toJson(jsonResponse);
//    }

    public String createJsonResponse(String status, List<Mueble> messages) {
        Gson gson = new Gson();

        JsonObject jsonResponse = new JsonObject();
        jsonResponse.addProperty("status", status);

        // Convertir la lista de objetos a JsonArray
        JsonArray messageArray = gson.toJsonTree(messages).getAsJsonArray();
        jsonResponse.add("message", messageArray);

        return gson.toJson(jsonResponse);
    }
}
