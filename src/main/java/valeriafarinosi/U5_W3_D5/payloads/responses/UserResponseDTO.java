package valeriafarinosi.U5_W3_D5.payloads.responses;

import java.util.UUID;

public record UserResponseDTO(
        UUID userId,
        String name,
        String surname,
        String email,
        String role) {
}
