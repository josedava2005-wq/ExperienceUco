package com.example.demo.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.repositorio.EventoRepositorio;
import com.example.demo.repositorio.InscripcionRepositorio;
import com.example.demo.repositorio.UsuarioRepositorio;

@Service
public class InscripcionServicio {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private EventoRepositorio eventoRepositorio;

    @Autowired
    private InscripcionRepositorio inscripcionRepositorio;

    public String inscribirUsuario(Long usuarioId, Long eventoId) {

        return "Inscripción realizada correctamente";

    }

}