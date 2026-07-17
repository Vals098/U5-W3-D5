package valeriafarinosi.U5_W3_D5.payloads.requests;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record NewEventDTO(
        @NotBlank(message = "Required field, please add a title for the event.")
        @Size(min = 2, max = 50, message = "The title must be between 2 and 50 characters long.")
        String title,
        @NotNull(message = "Required field, please insert a date for the event.")
        @FutureOrPresent(message = "The date of the event can't be in the past.")
        LocalDate eventDate,
        @NotBlank(message = "Required field, please add a location for the event.")
        @Size(min = 2, max = 50, message = "The location name must be between 2 and 50 characters long.")
        String place,
        @Size(max = 500, message = "The description can't exceed 500 characters.")
        String description,
        @NotNull(message = "Required field, please insert the max capacity for this event.")
        @Positive(message = "The capacity number must be greater then 0.")
        int capacity
) {
}
