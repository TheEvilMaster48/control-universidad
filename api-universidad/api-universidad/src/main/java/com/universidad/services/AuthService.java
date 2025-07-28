package com.universidad.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.universidad.dto.AuthResponse;
import com.universidad.dto.LoginRequest;
import com.universidad.dto.RegisterRequest;
import com.universidad.entities.User;
import com.universidad.enums.Role;
import com.universidad.repositories.UserRepository;
import com.universidad.security.JwtUtils;

@Service
public class AuthService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    public ResponseEntity<?> authenticateUser(LoginRequest loginRequest) {
        try {
            System.out.println("🔍 Buscando usuario: " + loginRequest.getUsuario());
            
            // Verificar si el usuario existe
            User user = userRepository.findByUsuario(loginRequest.getUsuario())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + loginRequest.getUsuario()));

            System.out.println("✅ Usuario encontrado: " + user.getUsuario() + " - Rol: " + user.getRole());

            // Autenticar
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsuario(), 
                    loginRequest.getPassword()
                )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            System.out.println("🔐 Autenticación exitosa, generando JWT...");

            // Generar JWT
            String jwt = jwtUtils.generateJwtToken(authentication);
            System.out.println("🎫 JWT generado exitosamente");

            AuthResponse authResponse = new AuthResponse(
                jwt, 
                user.getUsuario(), 
                user.getEmail(), 
                user.getRole().name()
            );

            return ResponseEntity.ok(authResponse);

        } catch (BadCredentialsException e) {
            System.err.println("❌ Credenciales incorrectas para usuario: " + loginRequest.getUsuario());
            Map<String, String> response = new HashMap<>();
            response.put("message", "Usuario o contraseña incorrectos");
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            System.err.println("💥 Error en autenticación: " + e.getMessage());
            e.printStackTrace();
            Map<String, String> response = new HashMap<>();
            response.put("message", "Error interno del servidor: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    public ResponseEntity<?> registerUser(RegisterRequest signUpRequest) {
        Map<String, String> response = new HashMap<>();
        
        try {
            // Verificar si el usuario ya existe
            if (userRepository.existsByUsuario(signUpRequest.getUsuario())) {
                response.put("message", "Error: El usuario ya está en uso!");
                return ResponseEntity.badRequest().body(response);
            }

            if (userRepository.existsByEmail(signUpRequest.getCorreo())) {
                response.put("message", "Error: El email ya está registrado!");
                return ResponseEntity.badRequest().body(response);
            }

            // Crear nueva cuenta de usuario
            User user = new User();
            user.setUsuario(signUpRequest.getUsuario());
            user.setEmail(signUpRequest.getCorreo());
            user.setNombre(signUpRequest.getNombre());
            user.setApellido(signUpRequest.getApellido());
            user.setPassword(encoder.encode(signUpRequest.getPassword()));
            user.setRole(Role.USUARIO); // Por defecto todos los nuevos usuarios son USUARIO

            User savedUser = userRepository.save(user);
            System.out.println("✅ Usuario registrado exitosamente: " + savedUser.getUsuario());

            response.put("message", "Usuario registrado exitosamente!");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            System.err.println("💥 Error en registro: " + e.getMessage());
            e.printStackTrace();
            response.put("message", "Error interno del servidor: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}
