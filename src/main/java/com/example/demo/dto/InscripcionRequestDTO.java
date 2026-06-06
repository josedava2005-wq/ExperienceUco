package com.example.demo.dto;

import jakarta.validation.constraints.NotNull;

public record InscripcionRequestDTO(

        @NotNull(message = "El ID del usuario es obligatorio")
        Long usuarioId,

        @NotNull(message = "El ID del evento es obligatorio")
        Long eventoId
) {}
