package com.example.demo.servicio;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.InscripcionResponseDTO;
import com.example.demo.entidad.Evento;
import com.example.demo.entidad.Inscripcion;
import com.example.demo.entidad.Usuario;
import com.example.demo.excepcion.CuposAgotadosException;
import com.example.demo.excepcion.InscripcionCerradaException;
import com.example.demo.excepcion.InscripcionDuplicadaException;
import com.example.demo.excepcion.RecursoNoEncontradoException;
import com.example.demo.repositorio.EventoRepositorio;
import com.example.demo.repositorio.InscripcionRepositorio;
import com.example.demo.repositorio.UsuarioRepositorio;

@Service
public class InscripcionServicio {

    private final UsuarioRepositorio usuarioRepositorio;
    private final EventoRepositorio eventoRepositorio;
    private final InscripcionRepositorio inscripcionRepositorio;

    public InscripcionServicio(UsuarioRepositorio usuarioRepositorio,
                               EventoRepositorio eventoRepositorio,
                               InscripcionRepositorio inscripcionRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
        this.eventoRepositorio = eventoRepositorio;
        this.inscripcionRepositorio = inscripcionRepositorio;
    }

    public List<InscripcionResponseDTO> listarTodos() {
        return inscripcionRepositorio.findAll()
                .stream()
                .map(InscripcionResponseDTO::desde)
                .toList();
    }

    @Transactional
    public void eliminar(Long id) {
        Inscripcion inscripcion = inscripcionRepositorio.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Inscripción no encontrada con id: " + id));

        if ("ACTIVA".equals(inscripcion.getEstado())) {
            Evento evento = inscripcion.getEvento();
            evento.setCuposDisponibles(evento.getCuposDisponibles() + 1);
            eventoRepositorio.save(evento);
        }

        inscripcionRepositorio.deleteById(id);
    }

    @Transactional
    public InscripcionResponseDTO inscribirUsuario(Long usuarioId, Long eventoId) {
        Usuario usuario = usuarioRepositorio.findById(usuarioId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado con id: " + usuarioId));

        Evento evento = eventoRepositorio.findById(eventoId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Evento no encontrado con id: " + eventoId));

        if (!evento.getInscripcionesAbiertas()) {
            throw new InscripcionCerradaException(
                    "Las inscripciones para el evento '" + evento.getNombre() + "' están cerradas.");
        }

        if (evento.getCuposDisponibles() <= 0) {
            throw new CuposAgotadosException(
                    "No hay cupos disponibles para el evento '" + evento.getNombre() + "'.");
        }

        if (inscripcionRepositorio.existsByUsuarioAndEvento(usuario, evento)) {
            throw new InscripcionDuplicadaException(
                    "El usuario '" + usuario.getNombre() + "' ya está inscrito en el evento '" + evento.getNombre() + "'.");
        }

        evento.setCuposDisponibles(evento.getCuposDisponibles() - 1);
        eventoRepositorio.save(evento);

        Inscripcion inscripcion = new Inscripcion();
        inscripcion.setUsuario(usuario);
        inscripcion.setEvento(evento);
        inscripcion.setFechaInscripcion(LocalDate.now());
        inscripcion.setEstado("ACTIVA");

        Inscripcion guardada = inscripcionRepositorio.save(inscripcion);
        return InscripcionResponseDTO.desde(guardada);
    }

    public List<InscripcionResponseDTO> listarPorUsuario(Long usuarioId) {
        Usuario usuario = usuarioRepositorio.findById(usuarioId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado con id: " + usuarioId));
        return inscripcionRepositorio.findByUsuario(usuario)
                .stream()
                .map(InscripcionResponseDTO::desde)
                .toList();
    }

    public List<InscripcionResponseDTO> listarPorEvento(Long eventoId) {
        Evento evento = eventoRepositorio.findById(eventoId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Evento no encontrado con id: " + eventoId));
        return inscripcionRepositorio.findByEvento(evento)
                .stream()
                .map(InscripcionResponseDTO::desde)
                .toList();
    }

    @Transactional
    public InscripcionResponseDTO cancelarInscripcion(Long inscripcionId) {
        Inscripcion inscripcion = inscripcionRepositorio.findById(inscripcionId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Inscripción no encontrada con id: " + inscripcionId));

        if ("CANCELADA".equals(inscripcion.getEstado())) {
            throw new InscripcionCerradaException("La inscripción con id " + inscripcionId + " ya fue cancelada anteriormente.");
        }

        inscripcion.setEstado("CANCELADA");

        Evento evento = inscripcion.getEvento();
        evento.setCuposDisponibles(evento.getCuposDisponibles() + 1);
        eventoRepositorio.save(evento);

        return InscripcionResponseDTO.desde(inscripcionRepositorio.save(inscripcion));
    }
}
