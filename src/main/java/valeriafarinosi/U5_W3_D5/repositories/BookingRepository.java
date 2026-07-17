package valeriafarinosi.U5_W3_D5.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import valeriafarinosi.U5_W3_D5.entities.Booking;
import valeriafarinosi.U5_W3_D5.entities.Event;
import valeriafarinosi.U5_W3_D5.entities.User;

import java.util.UUID;

@Repository
public interface BookingRepository extends JpaRepository<Booking, UUID> {
    boolean existsByUserAndEvent(User user, Event event);

//    existsByBookingDateAndUserId
//    findByUser
//    findByEvent

}
