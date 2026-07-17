package valeriafarinosi.U5_W3_D5.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import valeriafarinosi.U5_W3_D5.entities.Booking;
import valeriafarinosi.U5_W3_D5.entities.Event;
import valeriafarinosi.U5_W3_D5.entities.User;
import valeriafarinosi.U5_W3_D5.exceptions.BadRequestException;
import valeriafarinosi.U5_W3_D5.exceptions.NotFoundException;
import valeriafarinosi.U5_W3_D5.payloads.requests.NewBookingDTO;
import valeriafarinosi.U5_W3_D5.repositories.BookingRepository;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final UserService userService;
    private final EventService eventService;

    public BookingService(BookingRepository bookingRepository,
                          UserService userService,
                          EventService eventService) {

        this.bookingRepository = bookingRepository;
        this.userService = userService;
        this.eventService = eventService;
    }

    //    GET ALL
    public Page<Booking> getAll(int page, int size, String orderBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return bookingRepository.findAll(pageable);
    }

    //    FIND BY ID
    public Booking findById(UUID bookingId) {
        return bookingRepository.findById(bookingId)
                .orElseThrow(() -> new NotFoundException("Booking not found."));
    }

    public Booking save(NewBookingDTO payload) {

        User user = userService.findById(payload.userId());
        Event event = eventService.findById(payload.eventId());

//        CONTROLS
        if (bookingRepository.existsByUserAndEvent(user, event)) {
            throw new BadRequestException("You already booked this event.");
        }
        if (event.getBookedSeats() >= event.getCapacity()) {
            throw new BadRequestException("No seats available.");
        }
//        update bookedSeats
        event.setBookedSeats(event.getBookedSeats() + 1);
//        new booking
        Booking booking = new Booking();

        booking.setBookingDate(LocalDate.now());
        booking.setUser(user);
        booking.setEvent(event);

        return bookingRepository.save(booking);
    }

}
