package com.example.demo.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.servicio.InscripcionServicio;

@RestController
@RequestMapping("/inscripciones")
public class InscripcionControlador {

    @Autowired
    private InscripcionServicio servicio;

    @PostMapping
    public String inscribir(
            @RequestParam Long usuarioId,
            @RequestParam Long eventoId){

        return servicio.inscribirUsuario(usuarioId, eventoId);
    }
}