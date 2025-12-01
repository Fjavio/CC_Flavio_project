package conservatory.booking;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private static final Logger logger = LoggerFactory.getLogger(BookingController.class);

    @Autowired
    private BookingRepository repository;

    @PostMapping
    public ResponseEntity<String> createBooking(@RequestBody Booking booking) {
        logger.info("Reservation Request: {} for {} ({})", 
                    booking.getRoomName(), booking.getDate(), booking.getTimeSlot());

        //Check if room is already occupied
        boolean occupied = repository.existsByRoomNameAndDateAndTimeSlot(
                booking.getRoomName(), booking.getDate(), booking.getTimeSlot());

        if (occupied) {
            logger.warn("Conflict: room already occupied!");
            return ResponseEntity.status(HttpStatus.CONFLICT) // 409 Conflict
                    .body("Room occupied for this date and hour.");
        }

        //Save
        repository.save(booking);
        logger.info("Booking confirmed with ID: {}", booking.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body("Booking confirmed.");
    }

    @GetMapping("/teacher/{teacherID}")
    public List<Booking> getTeacherBookings(@PathVariable String teacherID) {
        return repository.findByTeacherID(teacherID);
    }
    
    @GetMapping("/status")
    public String status() {
        return "Room Booking Service is UP";
    }
}