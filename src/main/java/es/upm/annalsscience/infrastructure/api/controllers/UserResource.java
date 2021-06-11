package es.upm.annalsscience.infrastructure.api.controllers;

import es.upm.annalsscience.configuration.security.service.JwtService;
import es.upm.annalsscience.domain.model.User;
import es.upm.annalsscience.domain.services.UserService;
import es.upm.annalsscience.infrastructure.api.dto.CreateUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("users")
public class UserResource {

    private final UserService userService;
    private final JwtService jwtService;

    @Autowired
    private UserResource(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping
    public ResponseEntity<CreateUserDTO> register(@RequestBody CreateUserDTO createUserDTO) {
        User user = userService.createUser(map(createUserDTO));
        return ResponseEntity.ok(map(user));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@AuthenticationPrincipal org.springframework.security.core.userdetails.User activeUser) {
        User user = userService.findByEmail(activeUser.getUsername()).get();
        return ResponseEntity.ok(jwtService.createToken(user.getEmail(), user.getId()));
    }

    private User map(CreateUserDTO createUserDTO) {
        User user = new User();
        user.setName(createUserDTO.name);
        user.setEmail(createUserDTO.email);
        user.setPassword(createUserDTO.password);
        user.setSurname(createUserDTO.surname);
        return user;
    }

    private CreateUserDTO map(User user) {
        CreateUserDTO createUserDTO = new CreateUserDTO();
        createUserDTO.name = user.getName();
        createUserDTO.email = user.getEmail();
        createUserDTO.password = "***";
        createUserDTO.surname = user.getSurname();
        return createUserDTO;
    }
}
