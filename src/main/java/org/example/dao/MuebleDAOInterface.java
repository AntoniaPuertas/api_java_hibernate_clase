package org.example.dao;

import org.example.entidades.Mueble;

import java.util.List;

public interface MuebleDAOInterface {

    List<Mueble> devolverTodos();

    Mueble buscarPorId(long id);

    Mueble create(Mueble mueble);

    Mueble actualizar(Mueble mueble);

    boolean deleteById(long id);
}
