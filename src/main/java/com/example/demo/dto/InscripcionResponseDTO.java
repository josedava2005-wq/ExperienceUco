package com.example.demo.dto;

import java.time.LocalDate;

import com.example.demo.entidad.Inscripcion;

public record InscripcionResponseDTO(
        Long id,
        Long usuarioId,
        String usuarioNombre,
        Long eventoId,
        String eventoNombre,
        LocalDate fechaInscripcion,
        String estado
) {
    public static InscripcionResponseDTO desde(Inscripcion inscripcion) {
        return new InscripcionResponseDTO(
                inscripcion.getId(),
                inscripcion.getUsuario().getId(),
                inscripcion.getUsuario().getNombre(),
                inscripcion.getEvento().getId(),
                inscripcion.getEvento().getNombre(),
                inscripcion.getFechaInscripcion(),
                inscripcion.getEstado()
        );
    }
}
