package com.universidad.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.universidad.entities.Subdecano;

@Repository
public interface SubdecanoRepository extends JpaRepository<Subdecano, Long> {
    
    boolean existsByCorreo(String correo);
    boolean existsByCodigoSubdecano(String codigoSubdecano);
    Optional<Subdecano> findByCorreo(String correo);
    Optional<Subdecano> findByCodigoSubdecano(String codigoSubdecano);
}
