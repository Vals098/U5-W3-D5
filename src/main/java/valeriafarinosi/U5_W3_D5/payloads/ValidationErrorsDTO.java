package valeriafarinosi.U5_W3_D5.payloads;

import java.time.LocalDateTime;
import java.util.List;

public record ValidationErrorsDTO(String message, List<String> errors, LocalDateTime timestamp) {
}
