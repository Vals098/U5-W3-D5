package valeriafarinosi.U5_W3_D5.controllers;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import valeriafarinosi.U5_W3_D5.entities.Event;
import valeriafarinosi.U5_W3_D5.exceptions.ValidationException;
import valeriafarinosi.U5_W3_D5.payloads.requests.NewEventDTO;
import valeriafarinosi.U5_W3_D5.services.EventService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/events")
public class EventController {

    // DI
    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    //    POST http://localhost:3003/events + payload -> newEvent
    @PostMapping("/{organizerId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Event createEvent(@PathVariable UUID organizerId,
                             @RequestBody @Validated NewEventDTO payload,
                             BindingResult validationResult) {

        if (validationResult.hasErrors()) {
            List<String> errorsList = validationResult.getFieldErrors()
                    .stream().map(fieldError -> fieldError.getDefaultMessage()).toList();

            throw new ValidationException(errorsList);
        }

        return eventService.save(payload, organizerId);
    }

    //    GET http://localhost:3003/events -> get all Events
    @GetMapping
    public Page<Event> getAllEvents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "eventDate") String orderBy) {

        return eventService.getAll(page, size, orderBy);
    }

    // GET http://localhost:3003/events/{eventId}
    @GetMapping("/{eventId}")
    public Event getEventById(@PathVariable UUID eventId) {
        return eventService.findById(eventId);
    }

    //    PUT http://localhost:3003/events/{eventId} + payload
    @PutMapping("/{eventId}/{organizerId}")
    public Event updateEvent(@PathVariable UUID eventId,
                             @PathVariable UUID organizerId,
                             @RequestBody @Validated NewEventDTO payload,
                             BindingResult validationResult) {

        if (validationResult.hasErrors()) {
            List<String> errorsList = validationResult.getFieldErrors()
                    .stream()
                    .map(fieldError -> fieldError.getDefaultMessage())
                    .toList();

            throw new ValidationException(errorsList);
        }

        return eventService.findByIdAndUpdate(eventId, organizerId, payload);
    }

    // DELETE http://localhost:3003/events/{eventId}
    @DeleteMapping("/{eventId}/{organizerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEvent(@PathVariable UUID eventId,
                            @PathVariable UUID organizerId) {

        eventService.findByIdAndDelete(eventId, organizerId);
    }
}