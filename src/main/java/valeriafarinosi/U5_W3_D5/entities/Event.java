package valeriafarinosi.U5_W3_D5.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "events")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Event {

    @Id
    @GeneratedValue
    private UUID eventId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private LocalDate eventDate;

    @Column(nullable = false)
    private String place;

    @Column(length = 500)
    private String description;

    @Column(nullable = false)
    private int capacity;

    @Column(nullable = false)
    private int bookedSeats;

    @ManyToOne
    @JoinColumn(name = "organizer_id", nullable = false)
    private User organizer;

    public Event(String title, LocalDate eventDate, String place, String description, int capacity, User organizer) {
        this.title = title;
        this.eventDate = eventDate;
        this.place = place;
        this.description = description;
        this.capacity = capacity;
        this.bookedSeats = 0;
        this.organizer = organizer;
    }
}
