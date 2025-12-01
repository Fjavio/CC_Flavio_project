package conservatory.notification;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String recipient; // e.g. "flavio"
    private String message;   // e.g. "Report closed"
    
    private boolean isRead;   // true = read
    private LocalDateTime timestamp;

    public Notification() {}

    public Notification(String recipient, String message) {
        this.recipient = recipient;
        this.message = message;
        this.isRead = false;
        this.timestamp = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public String getRecipient() { return recipient; }
    public String getMessage() { return message; }
    public boolean isRead() { return isRead; }
    public void setRead(boolean read) { isRead = read; }
    public LocalDateTime getTimestamp() { return timestamp; }
}