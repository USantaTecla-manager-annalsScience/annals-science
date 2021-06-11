package es.upm.annalsscience.domain.services;

import es.upm.annalsscience.domain.UserRepository;
import es.upm.annalsscience.domain.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        userRepository.findByEmail(user.getEmail())
                .ifPresent(s -> { throw new RuntimeException("found");});
        userRepository.save(user);
        return user;
    }

    public Optional<User> findByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }
}
