package com.example.demo.entidad;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "evento")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre del evento es obligatorio")
    @Column(nullable = false)
    private String nombre;

    private String descripcion;

    @NotNull(message = "La fecha del evento es obligatoria")
    @Column(nullable = false)
    private LocalDate fechaEvento;

    @NotNull(message = "La hora de inicio es obligatoria")
    @Column(nullable = false)
    private LocalTime horaInicio;

    @NotNull(message = "La hora de fin es obligatoria")
    @Column(nullable = false)
    private LocalTime horaFin;

    @NotNull(message = "Los cupos disponibles son obligatorios")
    @Min(value = 0, message = "Los cupos no pueden ser negativos")
    @Column(nullable = false)
    private Integer cuposDisponibles;

    @NotNull(message = "El estado de inscripciones es obligatorio")
    @Column(nullable = false)
    private Boolean inscripcionesAbiertas;
}
