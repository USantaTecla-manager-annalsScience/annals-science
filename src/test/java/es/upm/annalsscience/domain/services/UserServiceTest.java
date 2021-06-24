package es.upm.annalsscience.domain.services;

import es.upm.annalsscience.domain.exceptions.UserAlreadyExistException;
import es.upm.annalsscience.domain.model.User;
import es.upm.annalsscience.domain.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    public void shouldCreateUser() {
        //GIVEN
        User expectedUser = getUser();
        when(userRepository.findByEmail(expectedUser.getEmail()))
                .thenReturn(Optional.empty());
        //WHEN
        User user = userService.createUser(expectedUser);

        //THEN
        assertEquals(expectedUser, user);
    }

    @Test
    public void shouldThrowExceptionWhenUserAlreadyExist() {
        //GIVEN
        User expectedUser = getUser();
        when(userRepository.findByEmail(expectedUser.getEmail()))
                .thenReturn(Optional.of(expectedUser));
        //WHEN
        assertThrows(UserAlreadyExistException.class, () -> {
            userService.createUser(expectedUser);
        });
    }

    @Test
    public void shouldReturnUserWhenFindByEmail() {
        //GIVEN
        User expectedUser = getUser();
        when(userRepository.findByEmail(expectedUser.getEmail()))
                .thenReturn(Optional.of(expectedUser));
        //WHEN
        Optional<User> user = userService.findByEmail(expectedUser.getEmail());

        //THEN
        assertEquals(Optional.of(expectedUser), user);
    }

    private User getUser() {
        User expectedUser = new User();
        expectedUser.setEmail("email");
        return expectedUser;
    }

}