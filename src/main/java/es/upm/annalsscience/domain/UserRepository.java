package es.upm.annalsscience.domain;

import es.upm.annalsscience.domain.model.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findByEmail(String email);
    void save(User user);
}
