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
        logger.info("Request received: createTeacher (ID: {})", request.getTeacherID());
        
        gestore.createAndInsertTeacher(request.getTeacherID(), request.getTeacherName(), request.getTeacherSurname());
        
        logger.info("Teacher {} successfully created", request.getTeacherID());
        return new ResponseEntity<>("Teacher successfully created", HttpStatus.CREATED);
    }

    @PostMapping("/corsi")
    public ResponseEntity<String> createCourse(@RequestBody CreaCorsoRequest request) 
            throws OperationException, IllegalArgumentException {
        
        logger.info("Request received: createCourse (Code: {})", request.getCourseCode());
        gestore.createAndInsertCourse(request.getCourseCode(), request.getCourseName(), request.getCfu(), request.getPreOf(), request.getPreFor());
        logger.info("Coirse {} successfully created", request.getCourseCode());
        
        return new ResponseEntity<>("Course successfully created", HttpStatus.CREATED);
    }
    
    @PutMapping("/corsi/{courseCode}/docente")
    public ResponseEntity<String> associateTeacher(@PathVariable String courseCode, @RequestBody AssociationRequest request) 
    		throws OperationException, IllegalArgumentException {
        
        logger.info("Request received: associateTeacher (Course: {}, Teacher: {})", courseCode, request.getTeacherID());
        gestore.AssociationTeacherCourse(courseCode, request.getTeacherID());
        logger.info("Association for the course {} completed", courseCode);

        return ResponseEntity.ok("Association successfully completed");
    }
    
    @PostMapping("/studenti")
    public ResponseEntity<String> createStudent(@RequestBody CreateStudentRequest request) 
            throws OperationException, IllegalArgumentException {
        
        gestore.createAndInsertStudent(
            request.getUsername(), 
            request.getPassword(), 
            request.getPin(), 
            request.getIdCds()
        );
        return new ResponseEntity<>("Student successfully created", HttpStatus.CREATED);
    }
}