package valeriafarinosi.U5_W3_D5.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import valeriafarinosi.U5_W3_D5.entities.User;
import valeriafarinosi.U5_W3_D5.exceptions.UnauthorizedException;
import valeriafarinosi.U5_W3_D5.payloads.LoginDTO;
import valeriafarinosi.U5_W3_D5.security.JWTTools;

@Service
public class AuthService {

    private final UserService userService;
    private final JWTTools jwtTools;
    private final PasswordEncoder bcrypt;

    public AuthService(UserService userService,
                       JWTTools jwtTools,
                       PasswordEncoder bcrypt) {

        this.userService = userService;
        this.jwtTools = jwtTools;
        this.bcrypt = bcrypt;
    }

    public String checkCredentialsAndGenerateToken(LoginDTO body) {

        User found = this.userService.findByEmail(body.email());

        if (bcrypt.matches(body.password(), found.getPassword())) {
            return jwtTools.generateToken(found);
        } else {
            throw new UnauthorizedException("The credentials are wrong.");
        }
    }
}