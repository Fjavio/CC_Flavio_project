package conservatory.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import conservatory.exception.OperationException;

import java.util.Map;

@ControllerAdvice //Tells Spring that this class handles exceptions globally
public class GlobalExceptionHandler {

    @ExceptionHandler(OperationException.class)
    public ResponseEntity<Map<String, String>> handleOperationException(OperationException ex) {
    	//If a business operation fails (e.g. course already assigned)
        return new ResponseEntity<>(Map.of("error", ex.getMessage()), HttpStatus.CONFLICT); // 409
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgumentException(IllegalArgumentException ex) {
    	//If the input data is in an incorrect format
        return new ResponseEntity<>(Map.of("error", ex.getMessage()), HttpStatus.BAD_REQUEST); // 400
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGenericException(Exception ex) {
    	//For any other unexpected errors
        ex.printStackTrace(); //The complete error
        return new ResponseEntity<>(Map.of("error", "An internal error occurred"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
    }
}
