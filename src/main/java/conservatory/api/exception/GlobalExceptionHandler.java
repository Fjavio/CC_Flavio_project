package conservatory.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import conservatory.exception.OperationException;

import java.util.Map;

@ControllerAdvice // Indica a Spring che questa classe gestisce eccezioni globalmente
public class GlobalExceptionHandler {

    @ExceptionHandler(OperationException.class)
    public ResponseEntity<Map<String, String>> handleOperationException(OperationException ex) {
        // Se un'operazione di business fallisce (es. corso già assegnato)
        return new ResponseEntity<>(Map.of("errore", ex.getMessage()), HttpStatus.CONFLICT); // 409
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgumentException(IllegalArgumentException ex) {
        // Se i dati in input hanno un formato errato
        return new ResponseEntity<>(Map.of("errore", ex.getMessage()), HttpStatus.BAD_REQUEST); // 400
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGenericException(Exception ex) {
        // Per qualsiasi altro errore non previsto
        ex.printStackTrace(); // È importante loggare l'errore completo
        return new ResponseEntity<>(Map.of("errore", "Si è verificato un errore interno."), HttpStatus.INTERNAL_SERVER_ERROR); // 500
    }
}
