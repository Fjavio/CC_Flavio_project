package conservatory.booking;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames = {"roomName", "date", "timeSlot"}) 
}) // Questo vincolo nel DB impedisce le doppie prenotazioni!
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String roomName;   
    private LocalDate date;    
    private String timeSlot;   
    private String teacherId;

    public Booking() {}

    public Booking(String roomName, LocalDate date, String timeSlot, String teacherId) {
        this.roomName = roomName;
        this.date = date;
        this.timeSlot = timeSlot;
        this.teacherId = teacherId;
    }

    // Getters e Setters standard...
    public Long getId() { return id; }
    public String getRoomName() { return roomName; }
    public void setRoomName(String roomName) { this.roomName = roomName; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public String getTimeSlot() { return timeSlot; }
    public void setTimeSlot(String timeSlot) { this.timeSlot = timeSlot; }
    public String getTeacherId() { return teacherId; }
    public void setTeacherId(String teacherId) { this.teacherId = teacherId; }
}