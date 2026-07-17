package valeriafarinosi.U5_W3_D5.exceptions;

import lombok.Getter;

import java.util.List;

@Getter
public class ValidationException extends RuntimeException {
    private List<String> errorsList;

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(List<String> errorsList) {
        super("Validation Errors");
        this.errorsList = errorsList;
    }

}
