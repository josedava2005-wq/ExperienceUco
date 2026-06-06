package com.example.demo.excepcion;

public class InscripcionDuplicadaException extends RuntimeException {

    public InscripcionDuplicadaException(String mensaje) {
        super(mensaje);
    }
}
