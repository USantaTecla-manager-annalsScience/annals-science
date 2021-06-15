package es.upm.annalsscience.infrastructure.persistence.jpa;

import es.upm.annalsscience.infrastructure.persistence.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDAO extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String name);
}
