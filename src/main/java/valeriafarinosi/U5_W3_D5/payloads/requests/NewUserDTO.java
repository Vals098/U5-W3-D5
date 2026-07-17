package valeriafarinosi.U5_W3_D5.payloads.requests;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record NewUserDTO(
        @NotBlank(message = "Required field, please add a name.")
        @Size(min = 2, max = 50, message = "The name must be between 2 and 50 characters long.")
        String name,
        @NotBlank(message = "Required field, please add a surname.")
        @Size(min = 2, max = 50, message = "The surname must be between 2 and 50 characters long.")
        String surname,
        @NotNull(message = "Required field, please insert a date of birth.")
        @Past(message = "The date of birth must be in the past.")
        LocalDate dateOfBirth,
        @NotBlank(message = "Required field, please add an email address.")
        @Email(message = "Please insert a valid email.")
        String email,
        @NotBlank(message = "Required field, please add a password.")
        @Size(min = 8, message = "The password must be at least 8 characters long.")
//        @Pattern() maybe implement later
        String password) {
}
