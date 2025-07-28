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

import com.universidad.dto.ProfesorRequest;
import com.universidad.entities.Profesor;
import com.universidad.entities.User;
import com.universidad.enums.Role;
import com.universidad.repositories.UserRepository;
import com.universidad.security.UserPrincipal;
import com.universidad.services.ProfesorService;

import jakarta.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/profesores")
public class ProfesorController {

    @Autowired
    private ProfesorService profesorService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<Profesor> getAllProfesores() {
        return profesorService.getAllProfesores();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProfesorById(@PathVariable Long id, Authentication authentication) {
        Map<String, String> response = new HashMap<>();
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        User currentUser = userRepository.findById(userPrincipal.getId()).orElse(null);

        Optional<Profesor> profesorOpt = profesorService.getProfesorById(id);
        if (!profesorOpt.isPresent()) {
            response.put("message", "Profesor no encontrado!");
            return ResponseEntity.notFound().build();
        }

        Profesor profesor = profesorOpt.get();

        // Verificar permisos
        if (currentUser.getRole() != Role.ADMIN && !profesor.getUser().getId().equals(currentUser.getId())) {
            response.put("message", "No tienes permisos para ver este profesor!");
            return ResponseEntity.status(403).body(response);
        }

        return ResponseEntity.ok(profesor);
    }

    @GetMapping("/mis-profesores")
    public List<Profesor> getMisProfesores(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        return profesorService.getProfesoresByUser(userPrincipal.getId());
    }

    @PostMapping
    public ResponseEntity<?> createProfesor(@Valid @RequestBody ProfesorRequest profesorRequest, Authentication authentication) {
        return profesorService.createProfesor(profesorRequest, authentication);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProfesor(@PathVariable Long id, @Valid @RequestBody ProfesorRequest profesorRequest, Authentication authentication) {
        return profesorService.updateProfesor(id, profesorRequest, authentication);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteProfesor(@PathVariable Long id) {
        return profesorService.deleteProfesor(id);
    }
}
