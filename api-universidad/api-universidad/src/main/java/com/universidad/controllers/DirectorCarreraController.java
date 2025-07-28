package com.universidad.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.universidad.dto.DirectorCarreraRequest;
import com.universidad.entities.DirectorCarrera;
import com.universidad.services.DirectorCarreraService;

import jakarta.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/directores-carrera")
public class DirectorCarreraController {
    
    @Autowired
    private DirectorCarreraService directorCarreraService;
    
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<DirectorCarrera>> getAllDirectoresCarrera() {
        System.out.println("=== GET /api/directores-carrera ===");
        try {
            List<DirectorCarrera> directores = directorCarreraService.findAll();
            System.out.println("✅ Directores encontrados: " + directores.size());
            return ResponseEntity.ok(directores);
        } catch (Exception e) {
            System.err.println("❌ Error al obtener directores: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
    
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DirectorCarrera> getDirectorCarreraById(@PathVariable Long id) {
        System.out.println("=== GET /api/directores-carrera/" + id + " ===");
        try {
            DirectorCarrera director = directorCarreraService.findById(id)
                .orElseThrow(() -> new RuntimeException("Director de carrera no encontrado con id: " + id));
            return ResponseEntity.ok(director);
        } catch (Exception e) {
            System.err.println("❌ Error al obtener director: " + e.getMessage());
            throw e;
        }
    }
    
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createDirectorCarrera(@Valid @RequestBody DirectorCarreraRequest directorRequest, Authentication authentication) {
        System.out.println("=== POST /api/directores-carrera ===");
        System.out.println("📝 Datos recibidos:");
        System.out.println("   Nombre: " + directorRequest.getNombre());
        System.out.println("   Apellido: " + directorRequest.getApellido());
        System.out.println("   Correo: " + directorRequest.getCorreo());
        System.out.println("   Código: " + directorRequest.getCodigoDirector());
        System.out.println("   Usuario actual: " + authentication.getName());
        
        try {
            DirectorCarrera savedDirector = directorCarreraService.save(directorRequest);
            System.out.println("✅ Director creado exitosamente con ID: " + savedDirector.getId());
            
            Map<String, String> response = new HashMap<>();
            response.put("message", "Director de carrera creado exitosamente!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.err.println("❌ Error al crear director: " + e.getMessage());
            e.printStackTrace();
            
            Map<String, String> response = new HashMap<>();
            response.put("message", "Error al crear director: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateDirectorCarrera(@PathVariable Long id, @Valid @RequestBody DirectorCarreraRequest directorRequest) {
        System.out.println("=== PUT /api/directores-carrera/" + id + " ===");
        try {
            DirectorCarrera updatedDirector = directorCarreraService.update(id, directorRequest);
            System.out.println("✅ Director actualizado exitosamente");
            return ResponseEntity.ok(updatedDirector);
        } catch (Exception e) {
            System.err.println("❌ Error al actualizar director: " + e.getMessage());
            
            Map<String, String> response = new HashMap<>();
            response.put("message", "Error al actualizar director: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteDirectorCarrera(@PathVariable Long id) {
        System.out.println("=== DELETE /api/directores-carrera/" + id + " ===");
        try {
            directorCarreraService.deleteById(id);
            System.out.println("✅ Director eliminado exitosamente");
            
            Map<String, String> response = new HashMap<>();
            response.put("message", "Director de carrera eliminado exitosamente");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.err.println("❌ Error al eliminar director: " + e.getMessage());
            
            Map<String, String> response = new HashMap<>();
            response.put("message", "Error al eliminar director: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}
