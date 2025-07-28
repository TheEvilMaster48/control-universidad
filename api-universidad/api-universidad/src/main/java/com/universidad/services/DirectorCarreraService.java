package com.universidad.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.universidad.dto.DirectorCarreraRequest;
import com.universidad.entities.DirectorCarrera;
import com.universidad.entities.User;
import com.universidad.repositories.DirectorCarreraRepository;
import com.universidad.repositories.UserRepository;

@Service
public class DirectorCarreraService {
    
    @Autowired
    private DirectorCarreraRepository directorCarreraRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Transactional(readOnly = true)
    public List<DirectorCarrera> findAll() {
        return directorCarreraRepository.findAll();
    }
    
    @Transactional(readOnly = true)
    public Optional<DirectorCarrera> findById(Long id) {
        return directorCarreraRepository.findById(id);
    }
    
    public DirectorCarrera save(DirectorCarreraRequest directorRequest) {
        // Validar que el correo no exista
        if (directorCarreraRepository.existsByCorreo(directorRequest.getCorreo())) {
            throw new RuntimeException("Ya existe un director con este correo: " + directorRequest.getCorreo());
        }
        
        // Validar que el código no exista
        if (directorCarreraRepository.existsByCodigoDirector(directorRequest.getCodigoDirector())) {
            throw new RuntimeException("Ya existe un director con este código: " + directorRequest.getCodigoDirector());
        }
        
        // Crear nueva entidad
        DirectorCarrera director = new DirectorCarrera();
        director.setNombre(directorRequest.getNombre());
        director.setApellido(directorRequest.getApellido());
        director.setCorreo(directorRequest.getCorreo());
        director.setCodigoDirector(directorRequest.getCodigoDirector());
        director.setCarrera(directorRequest.getCarrera());
        director.setFacultad(directorRequest.getFacultad());
        director.setTitulo(directorRequest.getTitulo());
        director.setTelefono(directorRequest.getTelefono());
        director.setOficina(directorRequest.getOficina());
        director.setFechaInicio(directorRequest.getFechaInicio());
        
        // Obtener el usuario actual
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        User currentUser = userRepository.findByUsuario(currentUsername)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + currentUsername));
        
        director.setUser(currentUser);
        
        return directorCarreraRepository.save(director);
    }
    
    public DirectorCarrera update(Long id, DirectorCarreraRequest directorRequest) {
        DirectorCarrera director = directorCarreraRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Director de carrera no encontrado con id: " + id));
        
        // Validar correo único (excepto el actual)
        if (!director.getCorreo().equals(directorRequest.getCorreo()) && 
            directorCarreraRepository.existsByCorreo(directorRequest.getCorreo())) {
            throw new RuntimeException("Ya existe un director con este correo: " + directorRequest.getCorreo());
        }
        
        // Validar código único (excepto el actual)
        if (!director.getCodigoDirector().equals(directorRequest.getCodigoDirector()) && 
            directorCarreraRepository.existsByCodigoDirector(directorRequest.getCodigoDirector())) {
            throw new RuntimeException("Ya existe un director con este código: " + directorRequest.getCodigoDirector());
        }
        
        // Actualizar campos
        director.setNombre(directorRequest.getNombre());
        director.setApellido(directorRequest.getApellido());
        director.setCorreo(directorRequest.getCorreo());
        director.setCodigoDirector(directorRequest.getCodigoDirector());
        director.setCarrera(directorRequest.getCarrera());
        director.setFacultad(directorRequest.getFacultad());
        director.setTitulo(directorRequest.getTitulo());
        director.setTelefono(directorRequest.getTelefono());
        director.setOficina(directorRequest.getOficina());
        director.setFechaInicio(directorRequest.getFechaInicio());
        
        return directorCarreraRepository.save(director);
    }
    
    public void deleteById(Long id) {
        if (!directorCarreraRepository.existsById(id)) {
            throw new RuntimeException("Director de carrera no encontrado con id: " + id);
        }
        directorCarreraRepository.deleteById(id);
    }
}
