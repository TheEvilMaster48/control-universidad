package com.universidad.repositories;

import com.universidad.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsuario(String usuario);
    Optional<User> findByEmail(String email);
    Boolean existsByUsuario(String usuario);
    Boolean existsByEmail(String email);
}
