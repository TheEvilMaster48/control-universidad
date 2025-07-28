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

import com.universidad.dto.SubdecanoRequest;
import com.universidad.entities.Subdecano;
import com.universidad.services.SubdecanoService;

import jakarta.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/subdecanos")
public class SubdecanoController {
    
    @Autowired
    private SubdecanoService subdecanoService;
    
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Subdecano>> getAllSubdecanos() {
        System.out.println("=== GET /api/subdecanos ===");
        try {
            List<Subdecano> subdecanos = subdecanoService.findAll();
            System.out.println("✅ Subdecanos encontrados: " + subdecanos.size());
            return ResponseEntity.ok(subdecanos);
        } catch (Exception e) {
            System.err.println("❌ Error al obtener subdecanos: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
    
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Subdecano> getSubdecanoById(@PathVariable Long id) {
        System.out.println("=== GET /api/subdecanos/" + id + " ===");
        try {
            Subdecano subdecano = subdecanoService.findById(id)
                .orElseThrow(() -> new RuntimeException("Subdecano no encontrado con id: " + id));
            return ResponseEntity.ok(subdecano);
        } catch (Exception e) {
            System.err.println("❌ Error al obtener subdecano: " + e.getMessage());
            throw e;
        }
    }
    
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createSubdecano(@Valid @RequestBody SubdecanoRequest subdecanoRequest, Authentication authentication) {
        System.out.println("=== POST /api/subdecanos ===");
        System.out.println("📝 Datos recibidos:");
        System.out.println("   Nombre: " + subdecanoRequest.getNombre());
        System.out.println("   Apellido: " + subdecanoRequest.getApellido());
        System.out.println("   Correo: " + subdecanoRequest.getCorreo());
        System.out.println("   Código: " + subdecanoRequest.getCodigoSubdecano());
        System.out.println("   Usuario actual: " + authentication.getName());
        
        try {
            Subdecano savedSubdecano = subdecanoService.save(subdecanoRequest);
            System.out.println("✅ Subdecano creado exitosamente con ID: " + savedSubdecano.getId());
            
            Map<String, String> response = new HashMap<>();
            response.put("message", "Subdecano creado exitosamente!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.err.println("❌ Error al crear subdecano: " + e.getMessage());
            e.printStackTrace();
            
            Map<String, String> response = new HashMap<>();
            response.put("message", "Error al crear subdecano: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateSubdecano(@PathVariable Long id, @Valid @RequestBody SubdecanoRequest subdecanoRequest) {
        System.out.println("=== PUT /api/subdecanos/" + id + " ===");
        try {
            Subdecano updatedSubdecano = subdecanoService.update(id, subdecanoRequest);
            System.out.println("✅ Subdecano actualizado exitosamente");
            return ResponseEntity.ok(updatedSubdecano);
        } catch (Exception e) {
            System.err.println("❌ Error al actualizar subdecano: " + e.getMessage());
            
            Map<String, String> response = new HashMap<>();
            response.put("message", "Error al actualizar subdecano: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteSubdecano(@PathVariable Long id) {
        System.out.println("=== DELETE /api/subdecanos/" + id + " ===");
        try {
            subdecanoService.deleteById(id);
            System.out.println("✅ Subdecano eliminado exitosamente");
            
            Map<String, String> response = new HashMap<>();
            response.put("message", "Subdecano eliminado exitosamente");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.err.println("❌ Error al eliminar subdecano: " + e.getMessage());
            
            Map<String, String> response = new HashMap<>();
            response.put("message", "Error al eliminar subdecano: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}
