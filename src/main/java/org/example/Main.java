package org.example;

import org.example.dao.MuebleDao;
import org.example.servicios.MueblesAPIREST;

public class Main {
    public static void main(String[] args) {

        MueblesAPIREST api = new MueblesAPIREST(new MuebleDao());
    }
}