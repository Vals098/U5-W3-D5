package valeriafarinosi.U5_W3_D5.controllers;


import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import valeriafarinosi.U5_W3_D5.entities.Booking;
import valeriafarinosi.U5_W3_D5.exceptions.ValidationException;
import valeriafarinosi.U5_W3_D5.payloads.requests.NewBookingDTO;
import valeriafarinosi.U5_W3_D5.services.BookingService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    //    DI
    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    //    POST
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Booking createBooking(
            @RequestBody @Validated NewBookingDTO payload,
            BindingResult validationResult) {

        if (validationResult.hasErrors()) {
            List<String> errors = validationResult.getFieldErrors()
                    .stream().map(fieldError -> fieldError.getDefaultMessage()).toList();

            throw new ValidationException(errors);
        }

        return bookingService.save(payload);
    }

    //    GET ALL
    @GetMapping
    public Page<Booking> getAllBookings(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "bookingDate") String orderBy) {

        return bookingService.getAll(page, size, orderBy);
    }

    //GET BY ID
    @GetMapping("/{bookingId}")
    public Booking getBookingById(@PathVariable UUID bookingId) {
        return bookingService.findById(bookingId);
    }

}
