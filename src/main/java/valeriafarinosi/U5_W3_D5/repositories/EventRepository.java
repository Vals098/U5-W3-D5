package valeriafarinosi.U5_W3_D5.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import valeriafarinosi.U5_W3_D5.entities.Event;
import valeriafarinosi.U5_W3_D5.entities.User;

import java.time.LocalDate;
import java.util.UUID;

@Repository
public interface EventRepository extends JpaRepository<Event, UUID> {

    boolean existsByEventDateAndPlace(LocalDate eventDate, String place);

    //    page
    Page<Event> findByOrganizer(User organizer, Pageable pageable);

}
