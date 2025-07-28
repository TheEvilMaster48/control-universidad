package com.universidad.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.universidad.entities.Estudiante;

@Repository
public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {
    
    List<Estudiante> findByUserId(Long userId);
    List<Estudiante> findByCarrera(String carrera);
    List<Estudiante> findBySemestre(Integer semestre);
    Optional<Estudiante> findByCorreo(String correo);
    Optional<Estudiante> findByCodigoEstudiante(String codigoEstudiante);
    Boolean existsByCorreo(String correo);
    Boolean existsByCodigoEstudiante(String codigoEstudiante);
}
