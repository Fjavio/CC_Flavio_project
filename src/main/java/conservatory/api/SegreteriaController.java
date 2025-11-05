package conservatory.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import conservatory.api.dto.*;
import conservatory.control.GestoreCorsiDiStudioConservatorio;
import conservatory.exception.OperationException;

@RestController
@RequestMapping("/api/segreteria") // Tutte le rotte qui iniziano con /api/segreteria
public class SegreteriaController {

    private final GestoreCorsiDiStudioConservatorio gestore;

    // Spring inietta automaticamente il Bean "Gestore" creato in Application.java
    public SegreteriaController(GestoreCorsiDiStudioConservatorio gestore) {
        this.gestore = gestore;
    }

    @PostMapping("/docenti")
    public ResponseEntity<String> creaDocente(@RequestBody CreaDocenteRequest request) 
            throws OperationException {
        gestore.createAndInsertTeacher(request.getName(), request.getSurname(), request.getId());
        return new ResponseEntity<>("Docente creato con successo", HttpStatus.CREATED);
    }

    @PostMapping("/corsi")
    public ResponseEntity<String> creaCorso(@RequestBody CreaCorsoRequest request) 
            throws OperationException {
        gestore.createAndInsertCourse(request.getCode(), request.getName(), request.getCfu(), request.getPreOf(), request.getPreFor());
        return new ResponseEntity<>("Corso creato con successo", HttpStatus.CREATED);
    }
    
    @PutMapping("/corsi/{courseCode}/docente")
    public ResponseEntity<String> associaDocente(@PathVariable String courseCode, @RequestBody AssociationRequest request) 
    		throws OperationException {
        gestore.AssociationTeacherCourse(courseCode, request.getTeacherId());
        return ResponseEntity.ok("Associazione completata con successo");
    }
}