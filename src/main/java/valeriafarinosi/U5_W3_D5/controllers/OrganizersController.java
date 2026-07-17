package valeriafarinosi.U5_W3_D5.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import valeriafarinosi.U5_W3_D5.entities.User;
import valeriafarinosi.U5_W3_D5.exceptions.ValidationException;
import valeriafarinosi.U5_W3_D5.payloads.requests.NewUserDTO;
import valeriafarinosi.U5_W3_D5.payloads.responses.UserResponseDTO;
import valeriafarinosi.U5_W3_D5.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/organizers")
public class OrganizersController {

    //    DI
    private final UserService userService;

    public OrganizersController(UserService userService) {
        this.userService = userService;
    }

    //    POST http://localhost:3003/organizers + payload -> newUser
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) //201
    public UserResponseDTO createOrganizer(@RequestBody @Validated NewUserDTO payload, BindingResult validationResult) {

        if (validationResult.hasErrors()) {
            List<String> errorsList = validationResult.getFieldErrors().stream().map(fieldError -> fieldError.getDefaultMessage()).toList();
            throw new ValidationException(errorsList);
        }

        User saved = this.userService.saveOrganizer(payload);
        return new UserResponseDTO(saved.getUserId(), saved.getName(), saved.getSurname(), saved.getEmail(), saved.getRole().name());

    }

}
