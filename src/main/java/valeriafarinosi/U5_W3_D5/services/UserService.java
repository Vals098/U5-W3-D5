package valeriafarinosi.U5_W3_D5.services;


import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import valeriafarinosi.U5_W3_D5.entities.User;
import valeriafarinosi.U5_W3_D5.enums.ROLE;
import valeriafarinosi.U5_W3_D5.exceptions.BadRequestException;
import valeriafarinosi.U5_W3_D5.exceptions.NotFoundException;
import valeriafarinosi.U5_W3_D5.payloads.requests.NewUserDTO;
import valeriafarinosi.U5_W3_D5.repositories.UserRepository;

import java.util.UUID;

@Service
@Slf4j
public class UserService {

    //    DIs
    private final UserRepository userRepository;
    private final PasswordEncoder bcrypt;

    public UserService(UserRepository userRepository, PasswordEncoder bcrypt) {
        this.userRepository = userRepository;
        this.bcrypt = bcrypt;
    }

    //   GENERIC SAVE
    public User save(NewUserDTO payload, ROLE role) {
//      1. CONTROLS:
//       email already existing in db
        if (this.userRepository.existsByEmail(payload.email())) {
            throw new BadRequestException("The email address " + payload.email() + " is already registered! Please log-in.");
        }
//        2. CREATE NEW USER
        User newUser = new User(payload.name(), payload.surname(), payload.email(), bcrypt.encode(payload.password()), payload.dateOfBirth(), role);

//        3. SAVE IN DB
        User saved = this.userRepository.save(newUser);

//        4.LOG
        log.info("The user " + saved.getName() + " " + saved.getSurname() + " has been saved!");

//        5. RETURN
        return saved;

    }

    //    USER SAVE
    public User saveUser(NewUserDTO payload) {
        return save(payload, ROLE.USER);
    }

    //    ORGANIZER SAVE
    public User saveOrganizer(NewUserDTO payload) {
        return save(payload, ROLE.EVENT_ORGANIZER);
    }

    //    FINDALL
    public Page<User> getAll(int page, int size, String orderBy) {
        if (size > 50) size = 50;
        if (size < 0) size = 10;
        if (page < 0) page = 0;
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return this.userRepository.findAll(pageable);
    }

    //    FINDBYID
    public User findById(UUID userId) {
        return this.userRepository.findById(userId).orElseThrow(() -> new NotFoundException(userId));
    }

    //    FINDBYEMAIL
    public User findByEmail(String email) {
        return this.userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("The user with email: " + email + " has not been found!"));
    }


}
