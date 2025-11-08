package conservatory.api.exception;

import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import conservatory.exception.OperationException;

@ControllerAdvice //Tells Spring that this class handles exceptions globally
public class GlobalExceptionHandler {

    //Logger istance
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(OperationException.class)
    public ResponseEntity<Map<String, String>> handleOperationException(OperationException ex) {
        //Log the business error as an advice (WARN)
        logger.warn("Business error (Conflict 409): {}", ex.getMessage());
        //If a business operation fails (e.g. course already assigned)
        return new ResponseEntity<>(Map.of("error", ex.getMessage()), HttpStatus.CONFLICT); //409
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgumentException(IllegalArgumentException ex) {
        //Log invalid input as an advice (WARN)
        logger.warn("Invalid request (Bad Request 400): {}", ex.getMessage());
        //If the input data is in an incorrect format
        return new ResponseEntity<>(Map.of("error", ex.getMessage()), HttpStatus.BAD_REQUEST); //400
    }

    @ExceptionHandler(Exception.class) //For any other unexpected errors
    public ResponseEntity<Map<String, String>> handleGenericException(Exception ex) {
        //Log the unexpected error as ERROR, including the entire stack trace (for debugging)
        logger.error("Unhandled internal error (Internal Server 500):", ex); 
        return new ResponseEntity<>(Map.of("error", "An internal server error occurred."), HttpStatus.INTERNAL_SERVER_ERROR); //500
    }
}