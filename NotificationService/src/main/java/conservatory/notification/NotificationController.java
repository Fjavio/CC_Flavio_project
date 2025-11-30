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

    // 1. Invia una nuova notifica (Usato dal tuo GestoreConservatorio)
    @PostMapping
    public ResponseEntity<String> sendNotification(@RequestBody NotificationRequest request) {
        logger.info("Salvataggio notifica per: {}", request.getRecipient());
        
        Notification n = new Notification(request.getRecipient(), request.getMessage());
        repository.save(n);
        
        return ResponseEntity.ok("Notifica salvata con ID: " + n.getId());
    }

    // 2. Leggi tutte le notifiche di un utente (Usato dallo studente)
    @GetMapping("/{username}")
    public List<Notification> getNotifications(@PathVariable String username) {
        return repository.findByRecipient(username);
    }

    // 3. Segna una notifica come letta
    @PutMapping("/{id}/read")
    public ResponseEntity<String> markAsRead(@PathVariable Long id) {
        return repository.findById(id)
                .map(notification -> {
                    notification.setRead(true);
                    repository.save(notification);
                    return ResponseEntity.ok("Notifica " + id + " segnata come letta.");
                })
                .orElse(ResponseEntity.notFound().build());
    }
    
    //Health check for Docker
    @GetMapping("/status")
    public String status() {
        return "Notification Service is UP";
    }  
}