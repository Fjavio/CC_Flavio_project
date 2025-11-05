package conservatory.api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import conservatory.api.dto.*; //Import all your DTOs
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

/* End-to-End (E2E) integration test for the Teacher API. 
 * This test launches the entire Spring Boot application and interacts with the live database.
 * So make sure XAMPP (MySQL) is running */

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DocenteApiTest { //teacher

    @LocalServerPort
    private int port;
   
    //N.B. In E2E testing, it's best to make the tests independent, but for this flow it makes sense to create a report and then close it
    private static String testReportCode;

    @BeforeEach
    public void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
        
        //A unique report code
        testReportCode = "VR" + (100 + new Random().nextInt(800));
    }

    //Each error message will depend on what gestore throws
    @Test
    void testCompleteReportFlow() throws Exception {
        
        //OPEN REPORT
        OpenReportRequest openRequest = new OpenReportRequest();
        openRequest.setReportCode(testReportCode);
        openRequest.setReportDate(Date.valueOf(LocalDate.now()));
        openRequest.setTeacherId("C123456"); //Make sure this teacher exists in the DB

        given()
            .contentType(ContentType.JSON)
            .body(openRequest)
        .when()
            .post("/api/docente/verbali")
        .then()
            .statusCode(HttpStatus.OK.value())
            .body(equalTo("Report opened successfully"));

        //ADD EXAM
        AddExamRequest examRequest = new AddExamRequest();
        examRequest.setVote(28);
        examRequest.setHonors(false);
        examRequest.setNotes("very good");
        examRequest.setCourseCode("A1234"); //Make sure it exists
        examRequest.setUsername("flavio");  //Make sure it exists

        given()
            .contentType(ContentType.JSON)
            .body(examRequest)
        .when()
            .post("/api/docente/verbali/" + testReportCode + "/esami")
        .then()
            .statusCode(HttpStatus.OK.value())
            .body(equalTo("Exam added to the report"));

        //CLOSE REPORT
        StudentPinDto pinDto = new StudentPinDto();
        pinDto.setUsername("flavio");
        pinDto.setPin(1234567); //Make sure this is the correct PIN for 'flavio'

        CloseReportRequest closeRequest = new CloseReportRequest();
        closeRequest.setStudentPins(List.of(pinDto));

        given()
            .contentType(ContentType.JSON)
            .body(closeRequest)
        .when()
            .post("/api/docente/verbali/" + testReportCode + "/chiusura")
        .then()
            .statusCode(HttpStatus.OK.value())
            .body(equalTo("Report closed successfully"));
    }
    
    @Test
    void testAddExam_Fail_ReportNotOpen() {
        
    	//Add an exam to a report that doesn't exist
        String reportCodeInexistent = "XXXXX";
        
        AddExamRequest examRequest = new AddExamRequest();
        examRequest.setVote(30);
        examRequest.setHonors(true);
        examRequest.setNotes("ok");
        examRequest.setCourseCode("A1234");
        examRequest.setUsername("flavio");

        given()
            .contentType(ContentType.JSON)
            .body(examRequest)
        .when()
            .post("/api/docente/verbali/" + reportCodeInexistent + "/esami")
        .then()
            .statusCode(HttpStatus.CONFLICT.value()) // 409 Conflict
            .body("error", equalTo("Oops, something went wrong..."));
    }
}