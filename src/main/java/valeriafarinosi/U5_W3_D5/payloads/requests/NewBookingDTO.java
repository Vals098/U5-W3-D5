package valeriafarinosi.U5_W3_D5.payloads.requests;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record NewBookingDTO(
        @NotNull(message = "User id is required.")
        UUID userId,
        @NotNull(message = "Required field, please insert the id of the event you want to book.")
        UUID eventId


) {
}
