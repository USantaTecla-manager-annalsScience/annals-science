package es.upm.annalsscience.infrastructure.api.resources;

import es.upm.annalsscience.configuration.security.service.JwtService;
import es.upm.annalsscience.domain.model.User;
import es.upm.annalsscience.domain.services.UserService;
import es.upm.annalsscience.infrastructure.api.dto.CreateUserDTO;
import es.upm.annalsscience.infrastructure.api.mappers.UserDTOMapper;
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
    private final UserDTOMapper userDTOMapper;

    @Autowired
    private UserResource(UserService userService, JwtService jwtService, UserDTOMapper userDTOMapper) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.userDTOMapper = userDTOMapper;
    }

    @PostMapping
    public ResponseEntity<CreateUserDTO> register(@RequestBody CreateUserDTO createUserDTO) {
        User user = userService.createUser(userDTOMapper.map(createUserDTO));
        return ResponseEntity.ok(userDTOMapper.map(user));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@AuthenticationPrincipal org.springframework.security.core.userdetails.User activeUser) {
        User user = userService.findByEmail(activeUser.getUsername()).get();
        return ResponseEntity.ok(jwtService.createToken(user.getEmail(), user.getId()));
    }
}
