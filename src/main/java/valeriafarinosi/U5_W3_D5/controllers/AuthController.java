package valeriafarinosi.U5_W3_D5.controllers;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import valeriafarinosi.U5_W3_D5.entities.User;
import valeriafarinosi.U5_W3_D5.payloads.LoginDTO;
import valeriafarinosi.U5_W3_D5.payloads.requests.NewUserDTO;
import valeriafarinosi.U5_W3_D5.services.AuthService;
import valeriafarinosi.U5_W3_D5.services.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    public AuthController(AuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }

    @PostMapping("/login")
    public String login(@RequestBody @Valid LoginDTO body) {

        return authService.checkCredentialsAndGenerateToken(body);

    }

    @PostMapping("/register")
    public User register(@RequestBody @Valid NewUserDTO body) {
        return userService.saveUser(body);

    }

    @PostMapping("/register_organizer")
    public User registerOrganizer(@RequestBody @Valid NewUserDTO body) {
        return userService.saveOrganizer(body);

    }

}