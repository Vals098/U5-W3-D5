package valeriafarinosi.U5_W3_D5.payloads.responses;

import java.time.LocalDate;
import java.util.UUID;

public record BookingResponseDTO(
        UUID bookingId,
        LocalDate bookingDate
) {
}
