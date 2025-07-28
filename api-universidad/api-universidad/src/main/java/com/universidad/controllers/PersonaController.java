package com.universidad.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.universidad.dto.PersonaRequest;
import com.universidad.entities.Persona;
import com.universidad.entities.User;
import com.universidad.enums.Role;
import com.universidad.enums.TipoPersona;
import com.universidad.repositories.UserRepository;
import com.universidad.security.UserPrincipal;
import com.universidad.services.PersonaService;

import jakarta.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/personas")
public class PersonaController {

    @Autowired
    private PersonaService personaService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<Persona> getAllPersonas() {
        return personaService.getAllPersonas();
    }

    @GetMapping("/tipo/{tipo}")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Persona> getPersonasByTipo(@PathVariable TipoPersona tipo) {
        return personaService.getPersonasByTipo(tipo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPersonaById(@PathVariable Long id, Authentication authentication) {
        Map<String, String> response = new HashMap<>();
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        User currentUser = userRepository.findById(userPrincipal.getId()).orElse(null);

        Optional<Persona> personaOpt = personaService.getPersonaById(id);
        if (!personaOpt.isPresent()) {
            response.put("message", "Persona no encontrada!");
            return ResponseEntity.notFound().build();
        }

        Persona persona = personaOpt.get();

        // Verificar permisos
        if (currentUser.getRole() != Role.ADMIN && !persona.getUser().getId().equals(currentUser.getId())) {
            response.put("message", "No tienes permisos para ver esta persona!");
            return ResponseEntity.status(403).body(response);
        }

        return ResponseEntity.ok(persona);
    }

    @GetMapping("/mis-personas")
    public List<Persona> getMisPersonas(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        return personaService.getPersonasByUser(userPrincipal.getId());
    }

    @PostMapping
    public ResponseEntity<?> createPersona(@Valid @RequestBody PersonaRequest personaRequest, Authentication authentication) {
        return personaService.createPersona(personaRequest, authentication);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePersona(@PathVariable Long id, @Valid @RequestBody PersonaRequest personaRequest, Authentication authentication) {
        return personaService.updatePersona(id, personaRequest, authentication);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deletePersona(@PathVariable Long id) {
        return personaService.deletePersona(id);
    }
}
