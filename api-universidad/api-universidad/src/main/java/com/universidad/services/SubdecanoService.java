package com.universidad.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.universidad.dto.SubdecanoRequest;
import com.universidad.entities.Subdecano;
import com.universidad.entities.User;
import com.universidad.repositories.SubdecanoRepository;
import com.universidad.repositories.UserRepository;

@Service
public class SubdecanoService {
    
    @Autowired
    private SubdecanoRepository subdecanoRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Transactional(readOnly = true)
    public List<Subdecano> findAll() {
        return subdecanoRepository.findAll();
    }
    
    @Transactional(readOnly = true)
    public Optional<Subdecano> findById(Long id) {
        return subdecanoRepository.findById(id);
    }
    
    public Subdecano save(SubdecanoRequest subdecanoRequest) {
        // Validar que el correo no exista
        if (subdecanoRepository.existsByCorreo(subdecanoRequest.getCorreo())) {
            throw new RuntimeException("Ya existe un subdecano con este correo: " + subdecanoRequest.getCorreo());
        }
        
        // Validar que el código no exista
        if (subdecanoRepository.existsByCodigoSubdecano(subdecanoRequest.getCodigoSubdecano())) {
            throw new RuntimeException("Ya existe un subdecano con este código: " + subdecanoRequest.getCodigoSubdecano());
        }
        
        // Crear nueva entidad
        Subdecano subdecano = new Subdecano();
        subdecano.setNombre(subdecanoRequest.getNombre());
        subdecano.setApellido(subdecanoRequest.getApellido());
        subdecano.setCorreo(subdecanoRequest.getCorreo());
        subdecano.setCodigoSubdecano(subdecanoRequest.getCodigoSubdecano());
        subdecano.setFacultad(subdecanoRequest.getFacultad());
        subdecano.setAreaResponsabilidad(subdecanoRequest.getAreaResponsabilidad());
        subdecano.setTitulo(subdecanoRequest.getTitulo());
        subdecano.setTelefono(subdecanoRequest.getTelefono());
        subdecano.setOficina(subdecanoRequest.getOficina());
        subdecano.setFechaInicio(subdecanoRequest.getFechaInicio());
        
        // Obtener el usuario actual
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        User currentUser = userRepository.findByUsuario(currentUsername)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + currentUsername));
        
        subdecano.setUser(currentUser);
        
        return subdecanoRepository.save(subdecano);
    }
    
    public Subdecano update(Long id, SubdecanoRequest subdecanoRequest) {
        Subdecano subdecano = subdecanoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Subdecano no encontrado con id: " + id));
        
        // Validar correo único (excepto el actual)
        if (!subdecano.getCorreo().equals(subdecanoRequest.getCorreo()) && 
            subdecanoRepository.existsByCorreo(subdecanoRequest.getCorreo())) {
            throw new RuntimeException("Ya existe un subdecano con este correo: " + subdecanoRequest.getCorreo());
        }
        
        // Validar código único (excepto el actual)
        if (!subdecano.getCodigoSubdecano().equals(subdecanoRequest.getCodigoSubdecano()) && 
            subdecanoRepository.existsByCodigoSubdecano(subdecanoRequest.getCodigoSubdecano())) {
            throw new RuntimeException("Ya existe un subdecano con este código: " + subdecanoRequest.getCodigoSubdecano());
        }
        
        // Actualizar campos
        subdecano.setNombre(subdecanoRequest.getNombre());
        subdecano.setApellido(subdecanoRequest.getApellido());
        subdecano.setCorreo(subdecanoRequest.getCorreo());
        subdecano.setCodigoSubdecano(subdecanoRequest.getCodigoSubdecano());
        subdecano.setFacultad(subdecanoRequest.getFacultad());
        subdecano.setAreaResponsabilidad(subdecanoRequest.getAreaResponsabilidad());
        subdecano.setTitulo(subdecanoRequest.getTitulo());
        subdecano.setTelefono(subdecanoRequest.getTelefono());
        subdecano.setOficina(subdecanoRequest.getOficina());
        subdecano.setFechaInicio(subdecanoRequest.getFechaInicio());
        
        return subdecanoRepository.save(subdecano);
    }
    
    public void deleteById(Long id) {
        if (!subdecanoRepository.existsById(id)) {
            throw new RuntimeException("Subdecano no encontrado con id: " + id);
        }
        subdecanoRepository.deleteById(id);
    }
}
