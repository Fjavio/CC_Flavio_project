package conservatory.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import conservatory.api.dto.*;
import conservatory.control.GestoreCorsiDiStudioConservatorio;
import conservatory.exception.OperationException;

@RestController
@RequestMapping("/api/segreteria")
public class SegreteriaController { //secretariat

    //Logger istance
    private static final Logger logger = LoggerFactory.getLogger(SegreteriaController.class);

    private final GestoreCorsiDiStudioConservatorio gestore;

    //Spring automatically injects the "Gestore" Bean created in Application.java
    public SegreteriaController(GestoreCorsiDiStudioConservatorio gestore) {
        this.gestore = gestore;
    }

    @PostMapping("/docenti")
    public ResponseEntity<String> createTeacher(@RequestBody CreaDocenteRequest request) 
            throws OperationException, IllegalArgumentException {
        
        //Log the incoming action
        logger.info("Request received: createTeacher (ID: {})", request.getId());
        
        gestore.createAndInsertTeacher(request.getId(), request.getName(), request.getSurname());
        
        logger.info("Teacher {} successfully created", request.getId());
        return new ResponseEntity<>("Teacher successfully created", HttpStatus.CREATED);
    }

    @PostMapping("/corsi")
    public ResponseEntity<String> createCourse(@RequestBody CreaCorsoRequest request) 
            throws OperationException, IllegalArgumentException {
        
        logger.info("Request received: createCourse (Code: {})", request.getCode());
        gestore.createAndInsertCourse(request.getCode(), request.getName(), request.getCfu(), request.getPreOf(), request.getPreFor());
        logger.info("Coirse {} successfully created", request.getCode());
        
        return new ResponseEntity<>("Course successfully created", HttpStatus.CREATED);
    }
    
    @PutMapping("/corsi/{courseCode}/docente")
    public ResponseEntity<String> associateTeacher(@PathVariable String courseCode, @RequestBody AssociationRequest request) 
    		throws OperationException, IllegalArgumentException {
        
        logger.info("Request received: associateTeacher (Course: {}, Teacher: {})", courseCode, request.getTeacherId());
        gestore.AssociationTeacherCourse(courseCode, request.getTeacherId());
        logger.info("Association for the course {} completed", courseCode);

        return ResponseEntity.ok("Association successfully completed");
    }
}