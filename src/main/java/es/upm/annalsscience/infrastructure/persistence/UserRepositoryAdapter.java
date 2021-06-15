package es.upm.annalsscience.infrastructure.persistence;

import es.upm.annalsscience.domain.repositories.UserRepository;
import es.upm.annalsscience.domain.model.User;
import es.upm.annalsscience.infrastructure.persistence.entities.UserEntity;
import es.upm.annalsscience.infrastructure.persistence.jpa.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserRepositoryAdapter implements UserRepository {

    private final UserDAO userDAO;

    @Autowired
    public UserRepositoryAdapter(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return this.userDAO.findByEmail(email)
                .map(this::map);
    }

    @Override
    public void save(User user) {
        this.userDAO.save(map(user));
    }

    private User map(UserEntity userEntity) {
        User user = new User();
        user.setId(userEntity.getId());
        user.setName(userEntity.getName());
        user.setSurname(userEntity.getSurname());
        user.setPassword(userEntity.getPassword());
        user.setEmail(userEntity.getEmail());
        return user;
    }

    private UserEntity map(User user) {
        UserEntity userEntity = new UserEntity();
        userEntity.setName(user.getName());
        userEntity.setSurname(user.getSurname());
        userEntity.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userEntity.setEmail(user.getEmail());
        return userEntity;
    }
}
