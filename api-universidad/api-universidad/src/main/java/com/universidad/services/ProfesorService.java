package com.universidad.services;

import com.universidad.dto.ProfesorRequest;
import com.universidad.entities.Profesor;
import com.universidad.entities.User;
import com.universidad.enums.Role;
import com.universidad.repositories.ProfesorRepository;
import com.universidad.repositories.UserRepository;
import com.universidad.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProfesorService {

    @Autowired
    private ProfesorRepository profesorRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Profesor> getAllProfesores() {
        return profesorRepository.findAll();
    }

    public Optional<Profesor> getProfesorById(Long id) {
        return profesorRepository.findById(id);
    }

    public List<Profesor> getProfesoresByUser(Long userId) {
        return profesorRepository.findByUserId(userId);
    }

    public ResponseEntity<?> createProfesor(ProfesorRequest profesorRequest, Authentication authentication) {
        Map<String, String> response = new HashMap<>();

        if (profesorRepository.existsByCorreo(profesorRequest.getCorreo())) {
            response.put("message", "Error: El correo ya está en uso!");
            return ResponseEntity.badRequest().body(response);
        }

        if (profesorRepository.existsByCodigoProfesor(profesorRequest.getCodigoProfesor())) {
            response.put("message", "Error: El código de profesor ya está en uso!");
            return ResponseEntity.badRequest().body(response);
        }

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        User user = userRepository.findById(userPrincipal.getId()).orElse(null);

        Profesor profesor = new Profesor(
                profesorRequest.getNombre(),
                profesorRequest.getApellido(),
                profesorRequest.getCorreo(),
                profesorRequest.getCodigoProfesor(),
                profesorRequest.getEspecialidad(),
                profesorRequest.getDepartamento(),
                profesorRequest.getTitulo(),
                profesorRequest.getTelefono(),
                profesorRequest.getOficina(),
                profesorRequest.getHorarioAtencion(),
                user
        );

        profesorRepository.save(profesor);
        response.put("message", "Profesor creado exitosamente!");
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<?> updateProfesor(Long id, ProfesorRequest profesorRequest, Authentication authentication) {
        Map<String, String> response = new HashMap<>();
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        User currentUser = userRepository.findById(userPrincipal.getId()).orElse(null);

        Optional<Profesor> profesorOpt = profesorRepository.findById(id);
        if (!profesorOpt.isPresent()) {
            response.put("message", "Profesor no encontrado!");
            return ResponseEntity.notFound().build();
        }

        Profesor profesor = profesorOpt.get();

        // Verificar permisos
        if (currentUser.getRole() != Role.ADMIN && !profesor.getUser().getId().equals(currentUser.getId())) {
            response.put("message", "No tienes permisos para actualizar este profesor!");
            return ResponseEntity.status(403).body(response);
        }

        // Verificar correo único
        if (!profesor.getCorreo().equals(profesorRequest.getCorreo()) && 
            profesorRepository.existsByCorreo(profesorRequest.getCorreo())) {
            response.put("message", "Error: El correo ya está en uso!");
            return ResponseEntity.badRequest().body(response);
        }

        // Verificar código único
        if (!profesor.getCodigoProfesor().equals(profesorRequest.getCodigoProfesor()) &&
            profesorRepository.existsByCodigoProfesor(profesorRequest.getCodigoProfesor())) {
            response.put("message", "Error: El código de profesor ya está en uso!");
            return ResponseEntity.badRequest().body(response);
        }

        profesor.setNombre(profesorRequest.getNombre());
        profesor.setApellido(profesorRequest.getApellido());
        profesor.setCorreo(profesorRequest.getCorreo());
        profesor.setCodigoProfesor(profesorRequest.getCodigoProfesor());
        profesor.setEspecialidad(profesorRequest.getEspecialidad());
        profesor.setDepartamento(profesorRequest.getDepartamento());
        profesor.setTitulo(profesorRequest.getTitulo());
        profesor.setTelefono(profesorRequest.getTelefono());
        profesor.setOficina(profesorRequest.getOficina());
        profesor.setHorarioAtencion(profesorRequest.getHorarioAtencion());

        profesorRepository.save(profesor);
        response.put("message", "Profesor actualizado exitosamente!");
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<?> deleteProfesor(Long id) {
        Map<String, String> response = new HashMap<>();
        
        if (!profesorRepository.existsById(id)) {
            response.put("message", "Profesor no encontrado!");
            return ResponseEntity.notFound().build();
        }

        profesorRepository.deleteById(id);
        response.put("message", "Profesor eliminado exitosamente!");
        return ResponseEntity.ok(response);
    }
}
