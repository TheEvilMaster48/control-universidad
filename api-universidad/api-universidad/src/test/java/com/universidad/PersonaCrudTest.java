package com.universidad;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.universidad.dto.LoginRequest;
import com.universidad.dto.PersonaRequest;
import com.universidad.enums.TipoPersona;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class PersonaCrudTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private String adminToken;

    @BeforeEach
    public void setup() throws Exception {
        // Obtener token de admin
        LoginRequest loginRequest = new LoginRequest("admin", "admin");
        
        MvcResult result = mockMvc.perform(post("/api/auth/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        // Extraer token de la respuesta JSON
        adminToken = objectMapper.readTree(response).get("token").asText();
    }

    @Test
    public void testCreatePersona() throws Exception {
        PersonaRequest personaRequest = new PersonaRequest();
        personaRequest.setNombre("María");
        personaRequest.setApellido("García");
        personaRequest.setCorreo("maria@universidad.com");
        personaRequest.setTipo(TipoPersona.PROFESOR);
        personaRequest.setEspecialidad("Matemáticas");
        personaRequest.setCodigo("PROF001");

        mockMvc.perform(post("/api/personas")
                .header("Authorization", "Bearer " + adminToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(personaRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Persona creada exitosamente!"));
    }

    @Test
    public void testGetAllPersonasAsAdmin() throws Exception {
        mockMvc.perform(get("/api/personas")
                .header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testGetAllPersonasWithoutAuth() throws Exception {
        mockMvc.perform(get("/api/personas"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testCreatePersonaWithDuplicateEmail() throws Exception {
        // Crear primera persona
        PersonaRequest personaRequest1 = new PersonaRequest();
        personaRequest1.setNombre("Ana");
        personaRequest1.setApellido("López");
        personaRequest1.setCorreo("ana@universidad.com");
        personaRequest1.setTipo(TipoPersona.ESTUDIANTE);

        mockMvc.perform(post("/api/personas")
                .header("Authorization", "Bearer " + adminToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(personaRequest1)))
                .andExpect(status().isOk());

        // Intentar crear segunda persona con mismo email
        PersonaRequest personaRequest2 = new PersonaRequest();
        personaRequest2.setNombre("Carlos");
        personaRequest2.setApellido("Ruiz");
        personaRequest2.setCorreo("ana@universidad.com"); // Email duplicado
        personaRequest2.setTipo(TipoPersona.PROFESOR);

        mockMvc.perform(post("/api/personas")
                .header("Authorization", "Bearer " + adminToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(personaRequest2)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Error: El correo ya está en uso!"));
    }
}
