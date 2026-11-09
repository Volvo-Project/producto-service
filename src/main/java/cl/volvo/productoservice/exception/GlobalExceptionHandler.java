package cl.volvo.productoservice.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.LinkedHashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<Map<String, String>> handleNotFound(RecursoNoEncontradoException ex) {
        log.warn(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("ERROR", ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationerrors(MethodArgumentNotValidException ex) {
        Map<String, String> errores = new LinkedHashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> errores.put(error.getField(), error.getDefaultMessage()));
        log.warn("[VALIDACIÓN] PETICIÓN RECHAZADA. CAMPOS CON ERRORES: {}", errores);
        return ResponseEntity.badRequest().body(errores);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> handleJsonInvalido(HttpMessageNotReadableException ex) {
        log.warn("JSON INVALIDO: {}", ex.getMessage());
        return ResponseEntity.badRequest().body(Map.of("ERROR", "EL CUERPO DE LA PETICIÓN NO ES VÁLIDO"));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, String>> handleTipoInvalido(MethodArgumentTypeMismatchException ex) {
        log.warn("PARÁMETRO INVÁLIDO: {} = {}", ex.getName(), ex.getValue());
        return ResponseEntity.badRequest().body(Map.of("ERROR", "EL VALOR: '" + ex.getValue() + "' NO ES VÁLIDO PARA " + ex.getName()));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, String>> handleDuplicado(DataIntegrityViolationException ex) {
        log.error("RESTRICCIÓN DE CONSTRAINT: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("ERROR", "YA EXISTE UN REGISTRO CON ESOS DATOS"));

    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Map<String, String>> handleSinPermiso(AccessDeniedException ex) {
        log.warn("ACCESO DENEGADO: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("ERROR", "NO TIENES PERMISOS PARA REALIZAR ESTA ACCIÓN"));

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGenerico(Exception ex) {
        log.error("ERROR INESPERADO", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("ERROR", "ERROR INTERNO EN EL SERVIDOR"));
    }


}
