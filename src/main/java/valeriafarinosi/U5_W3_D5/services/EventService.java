package valeriafarinosi.U5_W3_D5.services;


import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import valeriafarinosi.U5_W3_D5.entities.Event;
import valeriafarinosi.U5_W3_D5.entities.User;
import valeriafarinosi.U5_W3_D5.enums.ROLE;
import valeriafarinosi.U5_W3_D5.exceptions.BadRequestException;
import valeriafarinosi.U5_W3_D5.exceptions.ForbiddenException;
import valeriafarinosi.U5_W3_D5.exceptions.NotFoundException;
import valeriafarinosi.U5_W3_D5.payloads.requests.NewEventDTO;
import valeriafarinosi.U5_W3_D5.repositories.EventRepository;

import java.util.UUID;

@Service
@Slf4j
public class EventService {

    //    DIs
    private final EventRepository eventRepository;
    private final UserService userService;

    public EventService(EventRepository eventRepository, UserService userService) {
        this.eventRepository = eventRepository;
        this.userService = userService;
    }


    //    SAVE
    public Event save(NewEventDTO payload, UUID organizerId) {
        User organizer = userService.findById(organizerId);

//        1. CONTROLS:
//        the user must have role = EVENT_ORGANIZER
        if (organizer.getRole() != ROLE.EVENT_ORGANIZER) {
            throw new ForbiddenException("Only event organizers can create events."); //403
        }
//        location available in that date
        if (this.eventRepository.existsByEventDateAndPlace(payload.eventDate(), payload.place())) {
            throw new BadRequestException("There is already an event scheduled for the day " + payload.eventDate() + " in " + payload.place() + ", please select another date or place.");
        }

//        2. CREATE NEW EVENT
        Event newEvent = new Event(payload.title(), payload.eventDate(), payload.place(), payload.description(), payload.capacity(), organizer);

//        3. SAVE IN DB
        Event saved = this.eventRepository.save(newEvent);

//        4. LOG
        log.info("The event " + saved.getTitle() + " with id: " + saved.getEventId() + " - has been saved!");

//        5. RETURN
        return saved;

    }

    //    FINDALL
    public Page<Event> getAll(int page, int size, String orderBy) {
        if (size > 50) size = 50;
        if (size < 0) size = 10;
        if (page < 0) page = 0;
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return this.eventRepository.findAll(pageable);
    }

    //    FINDBYID
    public Event findById(UUID eventId) {
        return this.eventRepository.findById(eventId).orElseThrow(() -> new NotFoundException(eventId));
    }

    //    UPDATE
    public Event findByIdAndUpdate(UUID eventId, UUID organizerId, NewEventDTO payload) {

        Event event = findById(eventId);
        User organizer = userService.findById(organizerId);

//        CONRTOLS:
//        the user must have role = EVENT_ORGANIZER
        if (organizer.getRole() != ROLE.EVENT_ORGANIZER) {
            throw new ForbiddenException("Only event organizers can create events.");
        }
//        only the creator can update
        if (event.getOrganizer().getUserId().equals(organizerId)) {
            throw new ForbiddenException("You can only edit the events you created.");
        }

//        UPDATE
        event.setTitle(payload.title());
        event.setEventDate(payload.eventDate());
        event.setPlace(payload.place());
        event.setDescription(payload.description());
        event.setCapacity(payload.capacity());

//        RETURN AND SAVE IN DB
        return eventRepository.save(event);

    }

    public void findByIdAndDelete(UUID eventId, UUID organizerId) {
        Event event = this.findById(eventId);
        User organizer = this.userService.findById(organizerId);

//        CONRTOLS:
//        the user must have role = EVENT_ORGANIZER
        if (organizer.getRole() != ROLE.EVENT_ORGANIZER) {
            throw new ForbiddenException("Only event organizers can delete events.");
        }

        //        only the creator can delete
        if (event.getOrganizer().getUserId().equals(organizerId)) {
            throw new ForbiddenException("You can only delete the events you created.");
        }

//        DELETE
        this.eventRepository.delete(event);

//        LOG
        log.info("The event with id: " + event.getEventId() + " has been deleted.");

    }

}
