package com.universidad.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.universidad.entities.User;
import com.universidad.enums.Role;
import com.universidad.repositories.UserRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // ✅ CREAR USUARIOS USANDO SETTERS EN LUGAR DE CONSTRUCTOR
        if (!userRepository.existsByUsuario("admin")) {
            User admin = new User();
            admin.setUsuario("admin");
            admin.setEmail("admin@universidad.com");
            admin.setNombre("Administrador");
            admin.setApellido("Sistema");
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.setRole(Role.ADMIN);
            userRepository.save(admin);
            System.out.println("Usuario administrador creado: admin/admin");
        }

        // ✅ CREAR USUARIO REGULAR
        if (!userRepository.existsByUsuario("user")) {
            User user = new User();
            user.setUsuario("user");
            user.setEmail("user@universidad.com");
            user.setNombre("Usuario");
            user.setApellido("Regular");
            user.setPassword(passwordEncoder.encode("user"));
            user.setRole(Role.USUARIO);
            userRepository.save(user);
            System.out.println("Usuario regular creado: user/user");
        }

        System.out.println("=== USUARIOS DISPONIBLES ===");
        System.out.println("Admin: admin / admin (Rol: ADMIN)");
        System.out.println("Usuario: user / user (Rol: USUARIO)");
        System.out.println("============================");
        System.out.println("Backend ejecutándose en puerto: 3002");
        System.out.println("Frontend debe conectarse a: http://localhost:3002");
    }
}
