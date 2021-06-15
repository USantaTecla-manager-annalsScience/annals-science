package es.upm.annalsscience.infrastructure.persistence.mappers;

import es.upm.annalsscience.domain.model.User;
import es.upm.annalsscience.infrastructure.persistence.entities.UserEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User map(UserEntity userEntity) {
        User user = new User();
        user.setId(userEntity.getId());
        user.setName(userEntity.getName());
        user.setSurname(userEntity.getSurname());
        user.setPassword(userEntity.getPassword());
        user.setEmail(userEntity.getEmail());
        return user;
    }

    public UserEntity map(User user) {
        UserEntity userEntity = new UserEntity();
        userEntity.setName(user.getName());
        userEntity.setSurname(user.getSurname());
        userEntity.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userEntity.setEmail(user.getEmail());
        return userEntity;
    }
}
