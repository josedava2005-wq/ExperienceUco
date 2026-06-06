package com.example.demo.controlador;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.InscripcionRequestDTO;
import com.example.demo.dto.InscripcionResponseDTO;
import com.example.demo.servicio.InscripcionServicio;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/inscripciones")
@Validated
@Tag(name = "Inscripciones", description = "Gestión de inscripciones a eventos")
public class InscripcionControlador {

    private final InscripcionServicio inscripcionServicio;

    public InscripcionControlador(InscripcionServicio inscripcionServicio) {
        this.inscripcionServicio = inscripcionServicio;
    }

    @GetMapping
    @Operation(summary = "Listar todas las inscripciones")
    @ApiResponse(responseCode = "200", description = "Lista de inscripciones obtenida correctamente")
    public ResponseEntity<List<InscripcionResponseDTO>> listarTodas() {
        return ResponseEntity.ok(inscripcionServicio.listarTodos());
    }

    @GetMapping("/usuario/{usuarioId}")
    @Operation(summary = "Listar inscripciones de un usuario")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Inscripciones del usuario encontradas"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<List<InscripcionResponseDTO>> listarPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(inscripcionServicio.listarPorUsuario(usuarioId));
    }

    @GetMapping("/evento/{eventoId}")
    @Operation(summary = "Listar inscripciones de un evento")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Inscripciones del evento encontradas"),
        @ApiResponse(responseCode = "404", description = "Evento no encontrado")
    })
    public ResponseEntity<List<InscripcionResponseDTO>> listarPorEvento(@PathVariable Long eventoId) {
        return ResponseEntity.ok(inscripcionServicio.listarPorEvento(eventoId));
    }

    @PostMapping
    @Operation(summary = "Inscribir un usuario a un evento")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Inscripción realizada correctamente"),
        @ApiResponse(responseCode = "400", description = "Inscripciones cerradas para el evento"),
        @ApiResponse(responseCode = "404", description = "Usuario o evento no encontrado"),
        @ApiResponse(responseCode = "409", description = "El usuario ya está inscrito o no hay cupos disponibles")
    })
    public ResponseEntity<InscripcionResponseDTO> inscribir(@Valid @RequestBody InscripcionRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(inscripcionServicio.inscribirUsuario(dto.usuarioId(), dto.eventoId()));
    }

    @PatchMapping("/{id}/cancelar")
    @Operation(summary = "Cancelar una inscripción (cambia estado a CANCELADA y devuelve el cupo al evento)")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Inscripción cancelada correctamente"),
        @ApiResponse(responseCode = "400", description = "La inscripción ya fue cancelada anteriormente"),
        @ApiResponse(responseCode = "404", description = "Inscripción no encontrada")
    })
    public ResponseEntity<InscripcionResponseDTO> cancelar(@PathVariable Long id) {
        return ResponseEntity.ok(inscripcionServicio.cancelarInscripcion(id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar inscripción por ID (elimina el registro; si estaba ACTIVA devuelve el cupo)")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Inscripción eliminada correctamente"),
        @ApiResponse(responseCode = "404", description = "Inscripción no encontrada")
    })
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        inscripcionServicio.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
