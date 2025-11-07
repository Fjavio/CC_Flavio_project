package conservatory.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import conservatory.api.dto.*;
import conservatory.control.GestoreCorsiDiStudioConservatorio;
import conservatory.exception.OperationException;

@RestController
@RequestMapping("/api/docente")
public class DocenteController { //teacher

    private final GestoreCorsiDiStudioConservatorio gestore;

    public DocenteController(GestoreCorsiDiStudioConservatorio gestore) {
        this.gestore = gestore;
    }

    @PostMapping("/verbali")
    public ResponseEntity<String> openReport(@RequestBody OpenReportRequest request) 
            throws OperationException, IllegalArgumentException {
        gestore.OpeningReport(request.getReportCode(), request.getReportDate(), request.getTeacherID());
        return ResponseEntity.ok("Report opened successfully");
    }

    @PostMapping("/verbali/{reportCode}/esami")
    public ResponseEntity<String> addExam(@PathVariable String reportCode, @RequestBody AddExamRequest request) 
            throws OperationException, IllegalArgumentException {
        gestore.createAndInsertExam(request.getVote(), request.isHonors(), request.getNotes(), 
                                  reportCode, request.getCourseCode(), request.getUsername());
        return ResponseEntity.ok("Exam added to the report");
    }
    
    @PostMapping("/verbali/{reportCode}/chiusura")
    public ResponseEntity<String> closeReport(@PathVariable String reportCode, @RequestBody CloseReportRequest request) 
            throws OperationException, IllegalArgumentException {
    	//The closing logic iterates over all students and submitted PINs
        for (StudentPinDto studentPin : request.getStudentPins()) {
            gestore.checkPIN(studentPin.getPin(), reportCode, studentPin.getUsername());
            gestore.ClosingReport1(reportCode, studentPin.getUsername());
        }
        gestore.ClosingReport2(reportCode);
        return ResponseEntity.ok("Report closed successfully");
    }
}