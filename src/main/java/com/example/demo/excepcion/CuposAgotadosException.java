package com.example.demo.excepcion;

public class CuposAgotadosException extends RuntimeException {

    public CuposAgotadosException(String mensaje) {
        super(mensaje);
    }
}
