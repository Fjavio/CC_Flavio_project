package conservatory.control;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.HttpClientErrorException;
import java.util.Map;

import org.slf4j.Logger;   
import org.slf4j.LoggerFactory;

@Service
public class ExternalService {

    private final RestTemplate restTemplate = new RestTemplate();
    private static final Logger logger = LoggerFactory.getLogger(ExternalService.class);

    @Value("${service.booking.url:http://localhost:8083/api/bookings}")
    private String bookingUrl;

    @Value("${service.notification.url:http://localhost:8082/api/notifications}")
    private String notificationUrl;

    public void bookRoom(String roomName, String date, String timeSlot, String teacherId) throws RuntimeException {
        
        //JSON to send to the other service
        Map<String, Object> bookingRequest = Map.of(
            "roomName", roomName,
            "date", date,
            "timeSlot", timeSlot,
            "teacherId", teacherId
        );

        try {
            // HTTP POST call to 8083 service
            restTemplate.postForEntity(bookingUrl, bookingRequest, String.class);
            
        } catch (HttpClientErrorException.Conflict e) {
            throw new RuntimeException("The room " + roomName + " is already occupied for that date!");
        } catch (Exception e) {
            throw new RuntimeException("Communication error with the room service: " + e.getMessage());
        }
    }
    
    public void sendNotification(String recipient, String message) {
        
    	logger.info("Attempting to send notification to: {}", recipient);
    	
        Map<String, String> notificationRequest = Map.of(
            "recipient", recipient,
            "message", message
        );

        try {
            //POST call to 8082
            restTemplate.postForEntity(notificationUrl, notificationRequest, String.class);
            logger.info("Notification successfully send to {}", recipient);
        } catch (Exception e) {
            //Here we throw the exception, but the Gestore will decide to ignore it
            throw new RuntimeException("Send notification error: " + e.getMessage());
        }
    }
}