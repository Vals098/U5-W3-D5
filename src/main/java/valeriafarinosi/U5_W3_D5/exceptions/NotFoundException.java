package valeriafarinosi.U5_W3_D5.exceptions;

import java.util.UUID;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(UUID userId) {
        super("The id: " + userId + " - has not been found!");
    }
}
