package conservatory.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import conservatory.api.dto.*;
import conservatory.control.GestoreCorsiDiStudioConservatorio;
import conservatory.exception.OperationException;

@RestController
@RequestMapping("/api/segreteria")
public class SegreteriaController { //secretariat

    private final GestoreCorsiDiStudioConservatorio gestore;

    //Spring automatically injects the "Gestore" Bean created in Application.java
    public SegreteriaController(GestoreCorsiDiStudioConservatorio gestore) {
        this.gestore = gestore;
    }

    @PostMapping("/docenti")
    public ResponseEntity<String> createTeacher(@RequestBody CreaDocenteRequest request) 
            throws OperationException {
        gestore.createAndInsertTeacher(request.getName(), request.getSurname(), request.getId());
        return new ResponseEntity<>("Teacher successfully created", HttpStatus.CREATED);
    }

    @PostMapping("/corsi")
    public ResponseEntity<String> createCourse(@RequestBody CreaCorsoRequest request) 
            throws OperationException {
        gestore.createAndInsertCourse(request.getCode(), request.getName(), request.getCfu(), request.getPreOf(), request.getPreFor());
        return new ResponseEntity<>("Course successfully created", HttpStatus.CREATED);
    }
    
    @PutMapping("/corsi/{courseCode}/docente")
    public ResponseEntity<String> associateTeacher(@PathVariable String courseCode, @RequestBody AssociationRequest request) 
    		throws OperationException {
        gestore.AssociationTeacherCourse(courseCode, request.getTeacherId());
        return ResponseEntity.ok("Association successfully completed");
    }
}