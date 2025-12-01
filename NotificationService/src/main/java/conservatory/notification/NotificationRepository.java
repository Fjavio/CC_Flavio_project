package conservatory.notification;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

//Spring interface to talk with DB
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    //Custom method to find user's notifications
    List<Notification> findByRecipient(String recipient);
    
    //Custom method to find not read notifications
    List<Notification> findByRecipientAndIsReadFalse(String recipient);
}