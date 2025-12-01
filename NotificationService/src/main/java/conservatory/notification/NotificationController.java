package conservatory.notification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private static final Logger logger = LoggerFactory.getLogger(NotificationController.class);

    @Autowired
    private NotificationRepository repository;

    //Send a new notification (Used by GestoreConservatorio)
    @PostMapping
    public ResponseEntity<String> sendNotification(@RequestBody NotificationRequest request) {
        logger.info("Saving notification for: {}", request.getRecipient());
        
        Notification n = new Notification(request.getRecipient(), request.getMessage());
        repository.save(n);
        
        return ResponseEntity.ok("Notification saved with ID: " + n.getId());
    }

    //Read all user's notifications (Used by student)
    @GetMapping("/{username}")
    public List<Notification> getNotifications(@PathVariable String username) {
        return repository.findByRecipient(username);
    }

    //Mark a notification as read
    @PutMapping("/{id}/read")
    public ResponseEntity<String> markAsRead(@PathVariable Long id) {
        return repository.findById(id)
                .map(notification -> {
                    notification.setRead(true);
                    repository.save(notification);
                    return ResponseEntity.ok("Notification " + id + " marked as read.");
                })
                .orElse(ResponseEntity.notFound().build());
    }
    
    //Health check for Docker
    @GetMapping("/status")
    public String status() {
        return "Notification Service is UP";
    }  
}