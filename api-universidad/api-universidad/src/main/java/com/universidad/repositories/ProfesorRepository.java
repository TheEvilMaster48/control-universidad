package com.universidad.repositories;

import com.universidad.entities.Profesor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfesorRepository extends JpaRepository<Profesor, Long> {
    List<Profesor> findByUserId(Long userId);
    List<Profesor> findByEspecialidad(String especialidad);
    List<Profesor> findByDepartamento(String departamento);
    Optional<Profesor> findByCorreo(String correo);
    Optional<Profesor> findByCodigoProfesor(String codigoProfesor);
    Boolean existsByCorreo(String correo);
    Boolean existsByCodigoProfesor(String codigoProfesor);
}
