package com.universidad.controllers;

import com.universidad.dto.EstudianteRequest;
import com.universidad.entities.Estudiante;
import com.universidad.services.EstudianteService;
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
@RequestMapping("/api/estudiantes")
public class EstudianteController {
    
    @Autowired
    private EstudianteService estudianteService;
    
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Estudiante>> getAllEstudiantes() {
        System.out.println("=== GET /api/estudiantes ===");
        try {
            List<Estudiante> estudiantes = estudianteService.findAll();
            System.out.println("✅ Estudiantes encontrados: " + estudiantes.size());
            return ResponseEntity.ok(estudiantes);
        } catch (Exception e) {
            System.err.println("❌ Error al obtener estudiantes: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
    
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Estudiante> getEstudianteById(@PathVariable Long id) {
        System.out.println("=== GET /api/estudiantes/" + id + " ===");
        try {
            Estudiante estudiante = estudianteService.findById(id)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado con id: " + id));
            return ResponseEntity.ok(estudiante);
        } catch (Exception e) {
            System.err.println("❌ Error al obtener estudiante: " + e.getMessage());
            throw e;
        }
    }
    
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createEstudiante(@Valid @RequestBody EstudianteRequest estudianteRequest, Authentication authentication) {
        System.out.println("=== POST /api/estudiantes ===");
        System.out.println("📝 Datos recibidos:");
        System.out.println("   Nombre: " + estudianteRequest.getNombre());
        System.out.println("   Apellido: " + estudianteRequest.getApellido());
        System.out.println("   Correo: " + estudianteRequest.getCorreo());
        System.out.println("   Código: " + estudianteRequest.getCodigoEstudiante());
        System.out.println("   Usuario actual: " + authentication.getName());
        
        try {
            Estudiante savedEstudiante = estudianteService.save(estudianteRequest);
            System.out.println("✅ Estudiante creado exitosamente con ID: " + savedEstudiante.getId());
            
            Map<String, String> response = new HashMap<>();
            response.put("message", "Estudiante creado exitosamente!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.err.println("❌ Error al crear estudiante: " + e.getMessage());
            e.printStackTrace();
            
            Map<String, String> response = new HashMap<>();
            response.put("message", "Error al crear estudiante: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateEstudiante(@PathVariable Long id, @Valid @RequestBody EstudianteRequest estudianteRequest) {
        System.out.println("=== PUT /api/estudiantes/" + id + " ===");
        try {
            Estudiante updatedEstudiante = estudianteService.update(id, estudianteRequest);
            System.out.println("✅ Estudiante actualizado exitosamente");
            return ResponseEntity.ok(updatedEstudiante);
        } catch (Exception e) {
            System.err.println("❌ Error al actualizar estudiante: " + e.getMessage());
            
            Map<String, String> response = new HashMap<>();
            response.put("message", "Error al actualizar estudiante: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteEstudiante(@PathVariable Long id) {
        System.out.println("=== DELETE /api/estudiantes/" + id + " ===");
        try {
            estudianteService.deleteById(id);
            System.out.println("✅ Estudiante eliminado exitosamente");
            
            Map<String, String> response = new HashMap<>();
            response.put("message", "Estudiante eliminado exitosamente");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.err.println("❌ Error al eliminar estudiante: " + e.getMessage());
            
            Map<String, String> response = new HashMap<>();
            response.put("message", "Error al eliminar estudiante: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}
