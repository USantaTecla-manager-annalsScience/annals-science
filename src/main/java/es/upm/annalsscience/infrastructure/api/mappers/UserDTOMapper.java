package es.upm.annalsscience.infrastructure.api.mappers;

import es.upm.annalsscience.domain.model.User;
import es.upm.annalsscience.infrastructure.api.dto.CreateUserDTO;
import org.springframework.stereotype.Component;

@Component
public class UserDTOMapper {

    public User map(CreateUserDTO createUserDTO) {
        User user = new User();
        user.setName(createUserDTO.getName());
        user.setEmail(createUserDTO.getEmail());
        user.setPassword(createUserDTO.getPassword());
        user.setSurname(createUserDTO.getSurname());
        return user;
    }

    public CreateUserDTO map(User user) {
        CreateUserDTO createUserDTO = new CreateUserDTO();
        createUserDTO.setName(user.getName());
        createUserDTO.setEmail(user.getEmail());
        createUserDTO.setPassword("***");
        createUserDTO.setSurname(user.getSurname());
        return createUserDTO;
    }
}
