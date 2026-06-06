package com.example.demo.controlador;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.EventoDTO;
import com.example.demo.entidad.Evento;
import com.example.demo.servicio.EventoServicio;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/eventos")
@Validated
@Tag(name = "Eventos", description = "Gestión de eventos universitarios UCO")
public class EventoControlador {

    private final EventoServicio eventoServicio;

    public EventoControlador(EventoServicio eventoServicio) {
        this.eventoServicio = eventoServicio;
    }

    @GetMapping
    @Operation(summary = "Listar todos los eventos")
    @ApiResponse(responseCode = "200", description = "Lista de eventos obtenida correctamente")
    public ResponseEntity<List<Evento>> listarTodos() {
        return ResponseEntity.ok(eventoServicio.listarTodos());
    }

    @GetMapping("/abiertos")
    @Operation(summary = "Listar eventos con inscripciones abiertas")
    @ApiResponse(responseCode = "200", description = "Lista de eventos con inscripciones abiertas")
    public ResponseEntity<List<Evento>> listarAbiertos() {
        return ResponseEntity.ok(eventoServicio.listarAbiertos());
    }

    @GetMapping("/disponibles")
    @Operation(summary = "Listar eventos con inscripciones abiertas y cupos disponibles")
    @ApiResponse(responseCode = "200", description = "Lista de eventos con cupos disponibles")
    public ResponseEntity<List<Evento>> listarDisponibles() {
        return ResponseEntity.ok(eventoServicio.listarDisponibles());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar evento por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Evento encontrado"),
        @ApiResponse(responseCode = "404", description = "Evento no encontrado")
    })
    public ResponseEntity<Evento> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(eventoServicio.buscarPorId(id));
    }

    @PostMapping
    @Operation(summary = "Crear nuevo evento")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Evento creado correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    public ResponseEntity<Evento> crear(@Valid @RequestBody EventoDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(eventoServicio.crear(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar datos de un evento")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Evento actualizado correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @ApiResponse(responseCode = "404", description = "Evento no encontrado")
    })
    public ResponseEntity<Evento> actualizar(@PathVariable Long id, @Valid @RequestBody EventoDTO dto) {
        return ResponseEntity.ok(eventoServicio.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar evento por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Evento eliminado correctamente"),
        @ApiResponse(responseCode = "404", description = "Evento no encontrado")
    })
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        eventoServicio.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
