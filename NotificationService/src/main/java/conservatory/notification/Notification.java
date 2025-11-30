package conservatory.notification;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String recipient; // es. "flavio"
    private String message;   // es. "Verbale chiuso"
    
    private boolean isRead;   // true = letta, false = non letta
    private LocalDateTime timestamp;

    // Costruttore vuoto (richiesto da JPA)
    public Notification() {}

    // Costruttore utile
    public Notification(String recipient, String message) {
        this.recipient = recipient;
        this.message = message;
        this.isRead = false; // Nasce non letta
        this.timestamp = LocalDateTime.now();
    }

    // Getters e Setters
    public Long getId() { return id; }
    public String getRecipient() { return recipient; }
    public String getMessage() { return message; }
    public boolean isRead() { return isRead; }
    public void setRead(boolean read) { isRead = read; }
    public LocalDateTime getTimestamp() { return timestamp; }
}