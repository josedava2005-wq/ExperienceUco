package com.example.demo.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EventoDTO(
        Long id,

        @NotBlank(message = "El nombre del evento es obligatorio")
        String nombre,

        String descripcion,

        @NotNull(message = "La fecha del evento es obligatoria")
        LocalDate fechaEvento,

        @NotNull(message = "La hora de inicio es obligatoria")
        LocalTime horaInicio,

        @NotNull(message = "La hora de fin es obligatoria")
        LocalTime horaFin,

        @NotNull(message = "Los cupos disponibles son obligatorios")
        @Min(value = 1, message = "El evento debe tener al menos 1 cupo disponible")
        Integer cuposDisponibles,

        @NotNull(message = "El estado de inscripciones es obligatorio")
        Boolean inscripcionesAbiertas
) {}
