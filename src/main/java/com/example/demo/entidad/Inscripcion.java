package com.example.demo.entidad;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
    name = "inscripcion",
    uniqueConstraints = @UniqueConstraint(columnNames = {"usuario_id", "evento_id"})
)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Inscripcion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne(optional = false)
    @JoinColumn(name = "evento_id", nullable = false)
    private Evento evento;

    @Column(nullable = false)
    private LocalDate fechaInscripcion;

    @Column(nullable = false)
    private String estado;
}
