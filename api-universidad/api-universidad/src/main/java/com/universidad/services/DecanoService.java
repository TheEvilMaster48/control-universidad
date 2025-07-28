package com.universidad.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.universidad.dto.DecanoRequest;
import com.universidad.entities.Decano;
import com.universidad.entities.User;
import com.universidad.repositories.DecanoRepository;
import com.universidad.repositories.UserRepository;

@Service
public class DecanoService {
    
    @Autowired
    private DecanoRepository decanoRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Transactional(readOnly = true)
    public List<Decano> findAll() {
        return decanoRepository.findAll();
    }
    
    @Transactional(readOnly = true)
    public Optional<Decano> findById(Long id) {
        return decanoRepository.findById(id);
    }
    
    public Decano save(DecanoRequest decanoRequest) {
        // Validar que el correo no exista
        if (decanoRepository.existsByCorreo(decanoRequest.getCorreo())) {
            throw new RuntimeException("Ya existe un decano con este correo: " + decanoRequest.getCorreo());
        }
        
        // Validar que el código no exista
        if (decanoRepository.existsByCodigoDecano(decanoRequest.getCodigoDecano())) {
            throw new RuntimeException("Ya existe un decano con este código: " + decanoRequest.getCodigoDecano());
        }
        
        // Crear nueva entidad
        Decano decano = new Decano();
        decano.setNombre(decanoRequest.getNombre());
        decano.setApellido(decanoRequest.getApellido());
        decano.setCorreo(decanoRequest.getCorreo());
        decano.setCodigoDecano(decanoRequest.getCodigoDecano());
        decano.setFacultad(decanoRequest.getFacultad());
        decano.setTitulo(decanoRequest.getTitulo());
        decano.setTelefono(decanoRequest.getTelefono());
        decano.setOficina(decanoRequest.getOficina());
        decano.setFechaInicio(decanoRequest.getFechaInicio());
        decano.setExperienciaAcademica(decanoRequest.getExperienciaAcademica());
        
        // Obtener el usuario actual
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        User currentUser = userRepository.findByUsuario(currentUsername)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + currentUsername));
        
        decano.setUser(currentUser);
        
        return decanoRepository.save(decano);
    }
    
    public Decano update(Long id, DecanoRequest decanoRequest) {
        Decano decano = decanoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Decano no encontrado con id: " + id));
        
        // Validar correo único (excepto el actual)
        if (!decano.getCorreo().equals(decanoRequest.getCorreo()) && 
            decanoRepository.existsByCorreo(decanoRequest.getCorreo())) {
            throw new RuntimeException("Ya existe un decano con este correo: " + decanoRequest.getCorreo());
        }
        
        // Validar código único (excepto el actual)
        if (!decano.getCodigoDecano().equals(decanoRequest.getCodigoDecano()) && 
            decanoRepository.existsByCodigoDecano(decanoRequest.getCodigoDecano())) {
            throw new RuntimeException("Ya existe un decano con este código: " + decanoRequest.getCodigoDecano());
        }
        
        // Actualizar campos
        decano.setNombre(decanoRequest.getNombre());
        decano.setApellido(decanoRequest.getApellido());
        decano.setCorreo(decanoRequest.getCorreo());
        decano.setCodigoDecano(decanoRequest.getCodigoDecano());
        decano.setFacultad(decanoRequest.getFacultad());
        decano.setTitulo(decanoRequest.getTitulo());
        decano.setTelefono(decanoRequest.getTelefono());
        decano.setOficina(decanoRequest.getOficina());
        decano.setFechaInicio(decanoRequest.getFechaInicio());
        decano.setExperienciaAcademica(decanoRequest.getExperienciaAcademica());
        
        return decanoRepository.save(decano);
    }
    
    public void deleteById(Long id) {
        if (!decanoRepository.existsById(id)) {
            throw new RuntimeException("Decano no encontrado con id: " + id);
        }
        decanoRepository.deleteById(id);
    }
}
