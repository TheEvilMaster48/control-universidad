package com.universidad;

import com.universidad.dto.PersonaRequest;
import com.universidad.enums.TipoPersona;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class ValidationTest {

    @Autowired
    private Validator validator;

    @Test
    public void testPersonaRequestValidation() {
        PersonaRequest request = new PersonaRequest();
        // Dejar campos vacíos para probar validación

        Set<ConstraintViolation<PersonaRequest>> violations = validator.validate(request);
        
        assertFalse(violations.isEmpty());
        assertTrue(violations.size() >= 3); // nombre, apellido, correo son obligatorios
    }

    @Test
    public void testPersonaRequestValidEmail() {
        PersonaRequest request = new PersonaRequest();
        request.setNombre("Juan");
        request.setApellido("Pérez");
        request.setCorreo("email-invalido");
        request.setTipo(TipoPersona.ESTUDIANTE);

        Set<ConstraintViolation<PersonaRequest>> violations = validator.validate(request);
        
        boolean hasEmailViolation = violations.stream()
                .anyMatch(v -> v.getMessage().contains("correo válido"));
        
        assertTrue(hasEmailViolation);
    }

    @Test
    public void testPersonaRequestValid() {
        PersonaRequest request = new PersonaRequest();
        request.setNombre("Juan");
        request.setApellido("Pérez");
        request.setCorreo("juan@universidad.com");
        request.setTipo(TipoPersona.ESTUDIANTE);
        request.setEspecialidad("Ingeniería");
        request.setCodigo("EST001");

        Set<ConstraintViolation<PersonaRequest>> violations = validator.validate(request);
        
        assertTrue(violations.isEmpty());
    }
}
