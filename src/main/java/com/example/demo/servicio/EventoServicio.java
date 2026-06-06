package com.example.demo.servicio;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.dto.EventoDTO;
import com.example.demo.entidad.Evento;
import com.example.demo.excepcion.RecursoNoEncontradoException;
import com.example.demo.repositorio.EventoRepositorio;

@Service
public class EventoServicio {

    private final EventoRepositorio eventoRepositorio;

    public EventoServicio(EventoRepositorio eventoRepositorio) {
        this.eventoRepositorio = eventoRepositorio;
    }

    public List<Evento> listarTodos() {
        return eventoRepositorio.findAll();
    }

    public List<Evento> listarAbiertos() {
        return eventoRepositorio.findByInscripcionesAbiertasTrue();
    }

    public List<Evento> listarDisponibles() {
        return eventoRepositorio.findEventosDisponibles();
    }

    public Evento buscarPorId(Long id) {
        return eventoRepositorio.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Evento no encontrado con id: " + id));
    }

    public Evento crear(EventoDTO dto) {
        Evento evento = new Evento();
        mapearDesdeDTO(evento, dto);
        return eventoRepositorio.save(evento);
    }

    public Evento actualizar(Long id, EventoDTO dto) {
        Evento evento = buscarPorId(id);
        mapearDesdeDTO(evento, dto);
        return eventoRepositorio.save(evento);
    }

    public void eliminar(Long id) {
        buscarPorId(id);
        eventoRepositorio.deleteById(id);
    }

    private void mapearDesdeDTO(Evento evento, EventoDTO dto) {
        evento.setNombre(dto.nombre());
        evento.setDescripcion(dto.descripcion());
        evento.setFechaEvento(dto.fechaEvento());
        evento.setHoraInicio(dto.horaInicio());
        evento.setHoraFin(dto.horaFin());
        evento.setCuposDisponibles(dto.cuposDisponibles());
        evento.setInscripcionesAbiertas(dto.inscripcionesAbiertas());
    }
}
