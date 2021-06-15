package es.upm.annalsscience.infrastructure.persistence;

import es.upm.annalsscience.domain.model.User;
import es.upm.annalsscience.domain.repositories.UserRepository;
import es.upm.annalsscience.infrastructure.persistence.jpa.UserDAO;
import es.upm.annalsscience.infrastructure.persistence.mappers.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserRepositoryAdapter implements UserRepository {

    private final UserDAO userDAO;
    private final UserMapper userMapper;

    @Autowired
    public UserRepositoryAdapter(UserDAO userDAO, UserMapper userMapper) {
        this.userDAO = userDAO;
        this.userMapper = userMapper;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return this.userDAO.findByEmail(email)
                .map(userMapper::map);
    }

    @Override
    public void save(User user) {
        this.userDAO.save(userMapper.map(user));
    }
}
