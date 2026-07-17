package valeriafarinosi.U5_W3_D5.controllers;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import valeriafarinosi.U5_W3_D5.payloads.LoginDTO;
import valeriafarinosi.U5_W3_D5.services.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public String login(@RequestBody @Valid LoginDTO body) {

        return authService.checkCredentialsAndGenerateToken(body);

    }
}