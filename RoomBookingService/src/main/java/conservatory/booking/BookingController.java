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
        logger.info("Richiesta prenotazione: {} per {} ({})", 
                    booking.getRoomName(), booking.getDate(), booking.getTimeSlot());

        // 1. Controllo se l'aula è già occupata
        boolean occupata = repository.existsByRoomNameAndDateAndTimeSlot(
                booking.getRoomName(), booking.getDate(), booking.getTimeSlot());

        if (occupata) {
            logger.warn("Conflitto: Aula già occupata!");
            return ResponseEntity.status(HttpStatus.CONFLICT) // 409 Conflict
                    .body("Aula occupata per questa data e orario.");
        }

        // 2. Salva
        repository.save(booking);
        logger.info("Prenotazione confermata ID: {}", booking.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body("Prenotazione confermata.");
    }

    @GetMapping("/teacher/{teacherId}")
    public List<Booking> getTeacherBookings(@PathVariable String teacherId) {
        return repository.findByTeacherId(teacherId);
    }
    
    @GetMapping("/status")
    public String status() {
        return "Room Booking Service is UP";
    }
}