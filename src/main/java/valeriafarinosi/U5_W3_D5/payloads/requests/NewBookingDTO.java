package valeriafarinosi.U5_W3_D5.payloads.requests;

import java.util.UUID;

public record NewBookingDTO(
        UUID eventId
//        userId from JWT
) {
}
