package com.universidad.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.universidad.entities.Persona;
import com.universidad.enums.TipoPersona;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, Long> {
    List<Persona> findByUserId(Long userId);
    List<Persona> findByTipo(TipoPersona tipo);
    Optional<Persona> findByCorreo(String correo);
    Optional<Persona> findByCodigo(String codigo);
    Boolean existsByCorreo(String correo);
    Boolean existsByCodigo(String codigo);
}
