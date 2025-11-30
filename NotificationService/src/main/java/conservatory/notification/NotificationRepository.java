package conservatory.notification;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

//Interfaccia Spring per parlare con DB
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    // Metodo custom per trovare le notifiche di un utente
    List<Notification> findByRecipient(String recipient);
    
    // Metodo custom per trovare solo quelle non lette
    List<Notification> findByRecipientAndIsReadFalse(String recipient);
}