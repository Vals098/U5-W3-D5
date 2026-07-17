package valeriafarinosi.U5_W3_D5.payloads.responses;

import java.time.LocalDate;
import java.util.UUID;

public record EventResponseDTO(
        UUID eventId,
        String title,
        LocalDate eventDate,
        String place
) {
}
