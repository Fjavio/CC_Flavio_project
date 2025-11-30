package conservatory.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    // Ci serve per verificare se esiste già una prenotazione
    boolean existsByRoomNameAndDateAndTimeSlot(String roomName, LocalDate date, String timeSlot);
    
    List<Booking> findByTeacherId(String teacherId);
}