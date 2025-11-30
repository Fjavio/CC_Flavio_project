package conservatory.api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import conservatory.api.dto.CreaDocenteRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import java.util.Random;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


// End-to-End (E2E) integration test for the Secretariat API. 
// This test interacts with H2 database.

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SegreteriaApiTest { //secretariat

    @LocalServerPort
    private int port; //Spring will inject the random port here

    @BeforeEach
    public void setup() {
    	// Set the base URL for all REST Assured tests
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    //Each error message will depend on what gestore throws
    @Test
    void testCreateTeacher_Success() {
        
        //A unique teacher ID to avoid conflicts
        String teacherId = "DOC" + (1000 + new Random().nextInt(9000));
        
        CreaDocenteRequest requestBody = new CreaDocenteRequest();
        requestBody.setName("Mario");
        requestBody.setSurname("Rossi");
        requestBody.setId(teacherId);

        //REST Assured
        given()
            .contentType(ContentType.JSON) //Let's say we're sending JSON
            .body(requestBody)             //The DTO object is converted to JSON
        .when()
            .post("/api/segreteria/docenti") //The endpoint to call
        .then()
            .statusCode(HttpStatus.CREATED.value()) //Check code 201
            .body(equalTo("Teacher successfully created")); //Check the response message
    }

    @Test
    void testCreateTeacher_Fail_WrongID() {
       
        //A deliberately incorrect ID
        CreaDocenteRequest requestBody = new CreaDocenteRequest();
        requestBody.setName("Luca");
        requestBody.setSurname("Verdi");
        requestBody.setId("ID_TOO_LONG");

        given()
            .contentType(ContentType.JSON)
            .body(requestBody)
        .when()
            .post("/api/segreteria/docenti")
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value()) //Check code 400
            .body("error", equalTo("Teacher ID must be 7 characters long")); //Check the error
    }
    
    @Test
    void testAssociateTeacher_Fail_AssignedCourse() {
    	
    	String courseCode = "A5555"; //assigned in data.sql
        String teacherId = "C123456"; //A valid teacher

        given()
            .contentType(ContentType.JSON)
            .body("{ \"teacherId\": \"" + teacherId + "\" }")
        .when()
            .put("/api/segreteria/corsi/" + courseCode + "/docente")
        .then()
            .statusCode(HttpStatus.CONFLICT.value()) //Check code 400
            .body("error", equalTo("Course has already been assigned to a teacher")); //Check the error
    }

    @Test
    void testAssociateTeacher_Success_FreeCourse() {
        
        //Using course 'A1234', which data.sql created specifically with teacherID = NULL
        String courseCode = "A1234";
        String teacherId = "C123456"; 

        given()
            .contentType(ContentType.JSON)
            .body("{ \"teacherId\": \"" + teacherId + "\" }")
        .when()
            .put("/api/segreteria/corsi/" + courseCode + "/docente")
        .then()
            .statusCode(HttpStatus.OK.value()) // 200 OK
            .body(equalTo("Association successfully completed")); 
    }
}