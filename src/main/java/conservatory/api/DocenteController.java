package conservatory.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import conservatory.api.dto.*;
import conservatory.control.GestoreCorsiDiStudioConservatorio;
import conservatory.exception.OperationException;

@RestController
@RequestMapping("/api/docente")
public class DocenteController {

    private final GestoreCorsiDiStudioConservatorio gestore;

    public DocenteController(GestoreCorsiDiStudioConservatorio gestore) {
        this.gestore = gestore;
    }

    @PostMapping("/verbali")
    public ResponseEntity<String> apriVerbale(@RequestBody OpenReportRequest request) 
            throws OperationException {
        gestore.OpeningReport(request.getReportCode(), request.getReportDate(), request.getTeacherId());
        return ResponseEntity.ok("Verbale aperto con successo.");
    }

    @PostMapping("/verbali/{reportCode}/esami")
    public ResponseEntity<String> aggiungiEsame(@PathVariable String reportCode, @RequestBody AddExamRequest request) 
            throws OperationException {
        gestore.createAndInsertExam(request.getVote(), request.isHonors(), request.getNotes(), 
                                  reportCode, request.getCourseCode(), request.getUsername());
        return ResponseEntity.ok("Esame aggiunto al verbale.");
    }
    
    @PostMapping("/verbali/{reportCode}/chiusura")
    public ResponseEntity<String> chiudiVerbale(@PathVariable String reportCode, @RequestBody CloseReportRequest request) 
            throws OperationException {
        // La logica di chiusura itera su tutti gli studenti e i PIN inviati
        for (StudentPinDto studentPin : request.getStudentPins()) {
            gestore.checkPIN(studentPin.getPin(), reportCode, studentPin.getUsername());
            gestore.ClosingReport1(reportCode, studentPin.getUsername());
        }
        gestore.ClosingReport2(reportCode);
        return ResponseEntity.ok("Verbale chiuso con successo.");
    }
}