package valeriafarinosi.U5_W3_D5.payloads.requests;

import java.time.LocalDate;

public record NewUserDTO(
        String name,
        String surname,
        LocalDate dateOfBirth,
        String email,
        String password) {
}
