package conservatory.notification;

public class NotificationRequest {
    private String recipient; // es. "studenti del corso A1234"
    private String message;   // es. "Verbale chiuso."

    // Getters e Setters
    public String getRecipient() { return recipient; }
    public void setRecipient(String recipient) { this.recipient = recipient; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}