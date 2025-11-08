package conservatory.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import conservatory.api.dto.*;
import conservatory.control.GestoreCorsiDiStudioConservatorio;
import conservatory.exception.OperationException;

@RestController
@RequestMapping("/api/docente")
public class DocenteController { //teacher

    //Logger istance
    private static final Logger logger = LoggerFactory.getLogger(DocenteController.class);

    private final GestoreCorsiDiStudioConservatorio gestore;

    public DocenteController(GestoreCorsiDiStudioConservatorio gestore) {
        this.gestore = gestore;
    }

    @PostMapping("/verbali")
    public ResponseEntity<String> openReport(@RequestBody OpenReportRequest request) 
            throws OperationException, IllegalArgumentException {
        
    	//Log the incoming action
        logger.info("Request received: openReport (Code: {}, Teacher: {})", 
                    request.getReportCode(), request.getTeacherID());
        
        gestore.OpeningReport(request.getReportCode(), request.getReportDate(), request.getTeacherID());
        
        logger.info("Report {} opened successfully", request.getReportCode());
        return ResponseEntity.ok("Report opened successfully");
    }

    @PostMapping("/verbali/{reportCode}/esami")
    public ResponseEntity<String> addExam(@PathVariable String reportCode, @RequestBody AddExamRequest request) 
            throws OperationException, IllegalArgumentException {
        
        logger.info("Request received: addExam (Report: {}, Student: {}, Course: {})", 
                    reportCode, request.getUsername(), request.getCourseCode());
        
        gestore.createAndInsertExam(request.getVote(), request.isHonors(), request.getNotes(), 
                                  reportCode, request.getCourseCode(), request.getUsername());
        
        logger.info("Exam for the student {} added to the report {}", request.getUsername(), reportCode);
        return ResponseEntity.ok("Exam added to the report");
    }
    
    @PostMapping("/verbali/{reportCode}/chiusura")
    public ResponseEntity<String> closeReport(@PathVariable String reportCode, @RequestBody CloseReportRequest request) 
            throws OperationException, IllegalArgumentException {
        
        logger.info("Request received: closeReport (Verbale: {}) per {} studenti.", 
                    reportCode, request.getStudentPins().size());
    
        //The closing logic iterates over all students and submitted PINs
        for (StudentPinDto studentPin : request.getStudentPins()) {
            logger.debug("Check PIN for report {} and student {}", reportCode, studentPin.getUsername());
            gestore.checkPIN(studentPin.getPin(), reportCode, studentPin.getUsername());
            
            logger.debug("Check prerequisites (ClosingReport1) for report {} and student {}", 
                         reportCode, studentPin.getUsername());
            gestore.ClosingReport1(reportCode, studentPin.getUsername());
        }
        
        logger.info("Execute ClosingReport2 (check votes and finalize) for report {}", reportCode);
        gestore.ClosingReport2(reportCode);
        
        logger.info("Report {} closed successfully", reportCode);
        return ResponseEntity.ok("Report closed successfully");
    }
}