package com.example.demo.excepcion;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // ── Excepciones de negocio ──────────────────────────────────────────────

    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<Map<String, Object>> handleRecursoNoEncontrado(RecursoNoEncontradoException ex) {
        return buildError(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(InscripcionDuplicadaException.class)
    public ResponseEntity<Map<String, Object>> handleInscripcionDuplicada(InscripcionDuplicadaException ex) {
        return buildError(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(CuposAgotadosException.class)
    public ResponseEntity<Map<String, Object>> handleCuposAgotados(CuposAgotadosException ex) {
        return buildError(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(InscripcionCerradaException.class)
    public ResponseEntity<Map<String, Object>> handleInscripcionCerrada(InscripcionCerradaException ex) {
        return buildError(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(CorreoDuplicadoException.class)
    public ResponseEntity<Map<String, Object>> handleCorreoDuplicado(CorreoDuplicadoException ex) {
        return buildError(HttpStatus.CONFLICT, ex.getMessage());
    }

    // ── Errores de validación de campos (@Valid) ────────────────────────────

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidacion(MethodArgumentNotValidException ex) {
        Map<String, String> errores = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errores.put(error.getField(), error.getDefaultMessage());
        }
        Map<String, Object> cuerpo = new HashMap<>();
        cuerpo.put("timestamp", LocalDateTime.now());
        cuerpo.put("estado", HttpStatus.BAD_REQUEST.value());
        cuerpo.put("error", "Error de validación");
        cuerpo.put("errores", errores);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(cuerpo);
    }

    // ── JSON mal formado o tipo de dato incorrecto ──────────────────────────

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> handleMensajeNoLegible(HttpMessageNotReadableException ex) {
        return buildError(HttpStatus.BAD_REQUEST, "El cuerpo de la petición tiene un formato inválido o un tipo de dato incorrecto.");
    }

    // ── Violación de restricción única en base de datos (fallback) ──────────

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, Object>> handleIntegridad(DataIntegrityViolationException ex) {
        return buildError(HttpStatus.CONFLICT, "Ya existe un registro con esos datos. Verifique los campos únicos.");
    }

    // ── Fallback genérico ───────────────────────────────────────────────────

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenerico(Exception ex) {
        ex.printStackTrace();
        return buildError(HttpStatus.INTERNAL_SERVER_ERROR, "Error interno del servidor. Contacte al administrador.");
    }

    // ── Utilidad ────────────────────────────────────────────────────────────

    private ResponseEntity<Map<String, Object>> buildError(HttpStatus status, String mensaje) {
        Map<String, Object> cuerpo = new HashMap<>();
        cuerpo.put("timestamp", LocalDateTime.now());
        cuerpo.put("estado", status.value());
        cuerpo.put("error", status.getReasonPhrase());
        cuerpo.put("mensaje", mensaje);
        return ResponseEntity.status(status).body(cuerpo);
    }
}
