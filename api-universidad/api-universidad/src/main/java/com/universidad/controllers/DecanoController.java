package com.universidad.controllers;

import com.universidad.dto.DecanoRequest;
import com.universidad.entities.Decano;
import com.universidad.services.DecanoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/decanos")
public class DecanoController {
    
    @Autowired
    private DecanoService decanoService;
    
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Decano>> getAllDecanos() {
        System.out.println("=== GET /api/decanos ===");
        try {
            List<Decano> decanos = decanoService.findAll();
            System.out.println("✅ Decanos encontrados: " + decanos.size());
            return ResponseEntity.ok(decanos);
        } catch (Exception e) {
            System.err.println("❌ Error al obtener decanos: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
    
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Decano> getDecanoById(@PathVariable Long id) {
        System.out.println("=== GET /api/decanos/" + id + " ===");
        try {
            Decano decano = decanoService.findById(id)
                .orElseThrow(() -> new RuntimeException("Decano no encontrado con id: " + id));
            return ResponseEntity.ok(decano);
        } catch (Exception e) {
            System.err.println("❌ Error al obtener decano: " + e.getMessage());
            throw e;
        }
    }
    
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createDecano(@Valid @RequestBody DecanoRequest decanoRequest, Authentication authentication) {
        System.out.println("=== POST /api/decanos ===");
        System.out.println("📝 Datos recibidos:");
        System.out.println("   Nombre: " + decanoRequest.getNombre());
        System.out.println("   Apellido: " + decanoRequest.getApellido());
        System.out.println("   Correo: " + decanoRequest.getCorreo());
        System.out.println("   Código: " + decanoRequest.getCodigoDecano());
        System.out.println("   Usuario actual: " + authentication.getName());
        
        try {
            Decano savedDecano = decanoService.save(decanoRequest);
            System.out.println("✅ Decano creado exitosamente con ID: " + savedDecano.getId());
            
            Map<String, String> response = new HashMap<>();
            response.put("message", "Decano creado exitosamente!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.err.println("❌ Error al crear decano: " + e.getMessage());
            e.printStackTrace();
            
            Map<String, String> response = new HashMap<>();
            response.put("message", "Error al crear decano: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateDecano(@PathVariable Long id, @Valid @RequestBody DecanoRequest decanoRequest) {
        System.out.println("=== PUT /api/decanos/" + id + " ===");
        try {
            Decano updatedDecano = decanoService.update(id, decanoRequest);
            System.out.println("✅ Decano actualizado exitosamente");
            return ResponseEntity.ok(updatedDecano);
        } catch (Exception e) {
            System.err.println("❌ Error al actualizar decano: " + e.getMessage());
            
            Map<String, String> response = new HashMap<>();
            response.put("message", "Error al actualizar decano: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteDecano(@PathVariable Long id) {
        System.out.println("=== DELETE /api/decanos/" + id + " ===");
        try {
            decanoService.deleteById(id);
            System.out.println("✅ Decano eliminado exitosamente");
            
            Map<String, String> response = new HashMap<>();
            response.put("message", "Decano eliminado exitosamente");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.err.println("❌ Error al eliminar decano: " + e.getMessage());
            
            Map<String, String> response = new HashMap<>();
            response.put("message", "Error al eliminar decano: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}
