package conservatory.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
	//To check if a reservation already exists
    boolean existsByRoomNameAndDateAndTimeSlot(String roomName, LocalDate date, String timeSlot);
    
    List<Booking> findByTeacherID(String teacherID);
}