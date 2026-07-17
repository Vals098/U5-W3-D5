package valeriafarinosi.U5_W3_D5.payloads.requests;

import java.time.LocalDate;

public record NewEventDTO(
        String title,
        LocalDate eventDate,
        String place,
        String description,
        int capacity
) {
}
