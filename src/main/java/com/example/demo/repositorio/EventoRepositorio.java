package com.example.demo.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entidad.Evento;

@Repository
public interface EventoRepositorio extends JpaRepository<Evento, Long> {

    // Eventos con inscripciones abiertas
    List<Evento> findByInscripcionesAbiertasTrue();

    // Eventos con inscripciones abiertas y cupos disponibles
    @Query("SELECT e FROM Evento e WHERE e.inscripcionesAbiertas = true AND e.cuposDisponibles > 0")
    List<Evento> findEventosDisponibles();
}
