package es.upm.annalsscience.infrastructure.persistence.jpa;

import es.upm.annalsscience.infrastructure.persistence.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepositoryJPA extends JpaRepository<UserEntity, String> {
    Optional<UserEntity> findByEmail(String name);
}
