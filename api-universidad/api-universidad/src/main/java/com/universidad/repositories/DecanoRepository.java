package com.universidad.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.universidad.entities.Decano;

@Repository
public interface DecanoRepository extends JpaRepository<Decano, Long> {
    
    boolean existsByCorreo(String correo);
    boolean existsByCodigoDecano(String codigoDecano);
    Optional<Decano> findByCorreo(String correo);
    Optional<Decano> findByCodigoDecano(String codigoDecano);
}
