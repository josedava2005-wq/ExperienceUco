package com.example.demo.excepcion;

public class InscripcionCerradaException extends RuntimeException {

    public InscripcionCerradaException(String mensaje) {
        super(mensaje);
    }
}
