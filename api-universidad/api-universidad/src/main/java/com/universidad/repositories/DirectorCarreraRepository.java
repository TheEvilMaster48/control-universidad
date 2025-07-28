package com.universidad.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.universidad.entities.DirectorCarrera;

@Repository
public interface DirectorCarreraRepository extends JpaRepository<DirectorCarrera, Long> {
    
    boolean existsByCorreo(String correo);
    boolean existsByCodigoDirector(String codigoDirector);
    Optional<DirectorCarrera> findByCorreo(String correo);
    Optional<DirectorCarrera> findByCodigoDirector(String codigoDirector);
}
