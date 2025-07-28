package com.universidad.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.universidad.dto.PersonaRequest;
import com.universidad.entities.Persona;
import com.universidad.entities.User;
import com.universidad.enums.Role;
import com.universidad.enums.TipoPersona;
import com.universidad.repositories.PersonaRepository;
import com.universidad.repositories.UserRepository;
import com.universidad.security.UserPrincipal;

@Service
public class PersonaService {

    @Autowired
    private PersonaRepository personaRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Persona> getAllPersonas() {
        return personaRepository.findAll();
    }

    public List<Persona> getPersonasByTipo(TipoPersona tipoPersona) {
        return personaRepository.findByTipo(tipoPersona);
    }

    public Optional<Persona> getPersonaById(Long id) {
        return personaRepository.findById(id);
    }

    public List<Persona> getPersonasByUser(Long userId) {
        return personaRepository.findByUserId(userId);
    }

    public ResponseEntity<?> createPersona(PersonaRequest personaRequest, Authentication authentication) {
        Map<String, String> response = new HashMap<>();

        if (personaRepository.existsByCorreo(personaRequest.getCorreo())) {
            response.put("message", "Error: El correo ya está en uso!");
            return ResponseEntity.badRequest().body(response);
        }

        if (personaRequest.getCodigo() != null && personaRepository.existsByCodigo(personaRequest.getCodigo())) {
            response.put("message", "Error: El código ya está en uso!");
            return ResponseEntity.badRequest().body(response);
        }

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        User user = userRepository.findById(userPrincipal.getId()).orElse(null);

        Persona persona = new Persona(
                personaRequest.getNombre(),
                personaRequest.getApellido(),
                personaRequest.getCorreo(),
                personaRequest.getTipo(),
                personaRequest.getEspecialidad(),
                personaRequest.getCodigo(),
                user
        );

        personaRepository.save(persona);
        
        String tipoPersonaStr = getTipoPersonaString(personaRequest.getTipo());
        response.put("message", tipoPersonaStr + " creado exitosamente!");
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<?> updatePersona(Long id, PersonaRequest personaRequest, Authentication authentication) {
        Map<String, String> response = new HashMap<>();
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        User currentUser = userRepository.findById(userPrincipal.getId()).orElse(null);

        Optional<Persona> personaOpt = personaRepository.findById(id);
        if (!personaOpt.isPresent()) {
            response.put("message", "Persona no encontrada!");
            return ResponseEntity.notFound().build();
        }

        Persona persona = personaOpt.get();

        // Verificar permisos
        if (currentUser.getRole() != Role.ADMIN && !persona.getUser().getId().equals(currentUser.getId())) {
            response.put("message", "No tienes permisos para actualizar esta persona!");
            return ResponseEntity.status(403).body(response);
        }

        // Verificar correo único
        if (!persona.getCorreo().equals(personaRequest.getCorreo()) && 
            personaRepository.existsByCorreo(personaRequest.getCorreo())) {
            response.put("message", "Error: El correo ya está en uso!");
            return ResponseEntity.badRequest().body(response);
        }

        // Verificar código único
        if (personaRequest.getCodigo() != null && 
            !personaRequest.getCodigo().equals(persona.getCodigo()) &&
            personaRepository.existsByCodigo(personaRequest.getCodigo())) {
            response.put("message", "Error: El código ya está en uso!");
            return ResponseEntity.badRequest().body(response);
        }

        // Actualizar campos básicos
        persona.setNombre(personaRequest.getNombre());
        persona.setApellido(personaRequest.getApellido());
        persona.setCorreo(personaRequest.getCorreo());
        persona.setTipo(personaRequest.getTipo());
        persona.setEspecialidad(personaRequest.getEspecialidad());
        persona.setCodigo(personaRequest.getCodigo());

        personaRepository.save(persona);
        
        String tipoPersonaStr = getTipoPersonaString(persona.getTipo());
        response.put("message", tipoPersonaStr + " actualizado exitosamente!");
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<?> deletePersona(Long id) {
        Map<String, String> response = new HashMap<>();
        
        Optional<Persona> personaOpt = personaRepository.findById(id);
        if (!personaOpt.isPresent()) {
            response.put("message", "Persona no encontrada!");
            return ResponseEntity.notFound().build();
        }

        Persona persona = personaOpt.get();
        String tipoPersonaStr = getTipoPersonaString(persona.getTipo());
        
        personaRepository.deleteById(id);
        response.put("message", tipoPersonaStr + " eliminado exitosamente!");
        return ResponseEntity.ok(response);
    }

    private String getTipoPersonaString(TipoPersona tipoPersona) {
        return switch (tipoPersona) {
            case ESTUDIANTE -> "Estudiante";
            case PROFESOR -> "Profesor";
            case DIRECTOR_CARRERA -> "Director de carrera";
            case DECANO -> "Decano";
            case SUBDECANO -> "Subdecano";
        };
    }
}
