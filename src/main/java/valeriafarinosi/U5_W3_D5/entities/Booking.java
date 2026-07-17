package valeriafarinosi.U5_W3_D5.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "bookings")
@NoArgsConstructor
@Getter
@Setter
public class Booking {

    @Id
    @GeneratedValue
    private UUID bookingId;

    @Column(nullable = false)
    private LocalDate bookingDate;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    public Booking(Event event, User user) {
        this.event = event;
        this.user = user;
        this.bookingDate = LocalDate.now();
    }
}
