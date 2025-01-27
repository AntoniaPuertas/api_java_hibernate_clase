package org.example.servicios;

import com.google.gson.Gson;
import org.example.dao.MuebleDAOInterface;
import org.example.entidades.Mueble;
import spark.Spark;

import java.util.List;

public class MueblesAPIREST {
    private MuebleDAOInterface dao;
    private Gson gson = new Gson();

    public MueblesAPIREST(MuebleDAOInterface implementacion){
        Spark.port(8080);

        dao = implementacion;

        //endpoint para obtener todos los muebles
        Spark.get("/muebles", (request, response) -> {
            List<Mueble> muebles = dao.devolverTodos();
            response.type("application/json");
            return gson.toJson(muebles);
        });

        //endpoint para obtener un mueble por su id
        Spark.get("muebles/id/:id", (request, response) -> {
            Long id = Long.parseLong(request.params(":id"));
            Mueble mueble = dao.buscarPorId(id);
            response.type("application/json");
            if(mueble != null){
                return gson.toJson(mueble);
            }else{
                response.status(404);
                return "mueble no encontrado";
            }
        });

        Spark.post("/muebles", (request, response) -> {
            String body = request.body();
            //TODO comprobar si han llegado los datos correctos
            Mueble nuevoMueble = gson.fromJson(body, Mueble.class);

            //TODO comprobar si se creado el nuevoMueble
            Mueble creado = dao.create(nuevoMueble);

            response.type("application/json");
            return gson.toJson(creado);
        });

        Spark.put("muebles/id/:id", (request, response) -> {
            Long id = Long.parseLong(request.params(":id"));
            //TODO comprobar si han llegado los datos correctos
            String body = request.body();
            Mueble muebleActualizado = gson.fromJson(body, Mueble.class);
            muebleActualizado.setId(id);

            Mueble actualizado = dao.actualizar(muebleActualizado);
            if(actualizado != null){
                return gson.toJson(actualizado);
            }else{
                response.status(404);
                return "No se ha podido realizar la actualización";
            }
        });

    }
}
