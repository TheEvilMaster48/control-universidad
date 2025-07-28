package com.universidad.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.universidad.dto.EstudianteRequest;
import com.universidad.entities.Estudiante;
import com.universidad.entities.User;
import com.universidad.repositories.EstudianteRepository;
import com.universidad.repositories.UserRepository;

@Service
public class EstudianteService {
    
    @Autowired
    private EstudianteRepository estudianteRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Transactional(readOnly = true)
    public List<Estudiante> findAll() {
        return estudianteRepository.findAll();
    }
    
    @Transactional(readOnly = true)
    public Optional<Estudiante> findById(Long id) {
        return estudianteRepository.findById(id);
    }
    
    public Estudiante save(EstudianteRequest estudianteRequest) {
        // Validar que el correo no exista
        if (estudianteRepository.existsByCorreo(estudianteRequest.getCorreo())) {
            throw new RuntimeException("Ya existe un estudiante con este correo: " + estudianteRequest.getCorreo());
        }
        
        // Validar que el código no exista
        if (estudianteRepository.existsByCodigoEstudiante(estudianteRequest.getCodigoEstudiante())) {
            throw new RuntimeException("Ya existe un estudiante con este código: " + estudianteRequest.getCodigoEstudiante());
        }
        
        // Crear nueva entidad
        Estudiante estudiante = new Estudiante();
        estudiante.setNombre(estudianteRequest.getNombre());
        estudiante.setApellido(estudianteRequest.getApellido());
        estudiante.setCorreo(estudianteRequest.getCorreo());
        estudiante.setCodigoEstudiante(estudianteRequest.getCodigoEstudiante());
        estudiante.setCarrera(estudianteRequest.getCarrera());
        estudiante.setSemestre(estudianteRequest.getSemestre());
        estudiante.setPromedio(estudianteRequest.getPromedio());
        estudiante.setTelefono(estudianteRequest.getTelefono());
        estudiante.setDireccion(estudianteRequest.getDireccion());
        
        // Obtener el usuario actual
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        User currentUser = userRepository.findByUsuario(currentUsername)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + currentUsername));
        
        estudiante.setUser(currentUser);
        
        return estudianteRepository.save(estudiante);
    }
    
    public Estudiante update(Long id, EstudianteRequest estudianteRequest) {
        Estudiante estudiante = estudianteRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Estudiante no encontrado con id: " + id));
        
        // Validar correo único (excepto el actual)
        if (!estudiante.getCorreo().equals(estudianteRequest.getCorreo()) && 
            estudianteRepository.existsByCorreo(estudianteRequest.getCorreo())) {
            throw new RuntimeException("Ya existe un estudiante con este correo: " + estudianteRequest.getCorreo());
        }
        
        // Validar código único (excepto el actual)
        if (!estudiante.getCodigoEstudiante().equals(estudianteRequest.getCodigoEstudiante()) && 
            estudianteRepository.existsByCodigoEstudiante(estudianteRequest.getCodigoEstudiante())) {
            throw new RuntimeException("Ya existe un estudiante con este código: " + estudianteRequest.getCodigoEstudiante());
        }
        
        // Actualizar campos
        estudiante.setNombre(estudianteRequest.getNombre());
        estudiante.setApellido(estudianteRequest.getApellido());
        estudiante.setCorreo(estudianteRequest.getCorreo());
        estudiante.setCodigoEstudiante(estudianteRequest.getCodigoEstudiante());
        estudiante.setCarrera(estudianteRequest.getCarrera());
        estudiante.setSemestre(estudianteRequest.getSemestre());
        estudiante.setPromedio(estudianteRequest.getPromedio());
        estudiante.setTelefono(estudianteRequest.getTelefono());
        estudiante.setDireccion(estudianteRequest.getDireccion());
        
        return estudianteRepository.save(estudiante);
    }
    
    public void deleteById(Long id) {
        if (!estudianteRepository.existsById(id)) {
            throw new RuntimeException("Estudiante no encontrado con id: " + id);
        }
        estudianteRepository.deleteById(id);
    }
}
