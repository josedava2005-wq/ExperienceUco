package com.example.demo.controlador;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.repositorio.EventoRepositorio;
import com.example.demo.repositorio.InscripcionRepositorio;
import com.example.demo.repositorio.UsuarioRepositorio;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/estadisticas")
@Tag(name = "Estadísticas", description = "Métricas y resumen general de la plataforma")
public class EstadisticasControlador {

    private final UsuarioRepositorio usuarioRepositorio;
    private final EventoRepositorio eventoRepositorio;
    private final InscripcionRepositorio inscripcionRepositorio;

    public EstadisticasControlador(UsuarioRepositorio usuarioRepositorio,
                                   EventoRepositorio eventoRepositorio,
                                   InscripcionRepositorio inscripcionRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
        this.eventoRepositorio = eventoRepositorio;
        this.inscripcionRepositorio = inscripcionRepositorio;
    }

    @GetMapping
    @Operation(summary = "Obtener estadísticas generales de la plataforma")
    @ApiResponse(responseCode = "200", description = "Estadísticas obtenidas correctamente")
    public ResponseEntity<Map<String, Object>> obtenerEstadisticas() {
        Map<String, Object> estadisticas = Map.of(
                "totalUsuarios", usuarioRepositorio.count(),
                "totalEventos", eventoRepositorio.count(),
                "eventosAbiertos", eventoRepositorio.findByInscripcionesAbiertasTrue().size(),
                "eventosDisponibles", eventoRepositorio.findEventosDisponibles().size(),
                "totalInscripciones", inscripcionRepositorio.count(),
                "inscripcionesActivas", inscripcionRepositorio.countByEstado("ACTIVA"),
                "inscripcionesCanceladas", inscripcionRepositorio.countByEstado("CANCELADA")
        );
        return ResponseEntity.ok(estadisticas);
    }
}
