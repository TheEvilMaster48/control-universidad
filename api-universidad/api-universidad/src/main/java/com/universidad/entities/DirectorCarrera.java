package com.universidad.entities;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "directores_carrera")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class DirectorCarrera {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede exceder 100 caracteres")
    @Column(nullable = false, length = 100)
    private String nombre;
    
    @NotBlank(message = "El apellido es obligatorio")
    @Size(max = 100, message = "El apellido no puede exceder 100 caracteres")
    @Column(nullable = false, length = 100)
    private String apellido;
    
    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "Debe ser un correo válido")
    @Column(nullable = false, unique = true)
    private String correo;
    
    @NotBlank(message = "El código de director es obligatorio")
    @Column(nullable = false, unique = true, name = "codigo_director")
    private String codigoDirector;
    
    @NotBlank(message = "La carrera es obligatoria")
    @Column(nullable = false)
    private String carrera;
    
    @NotBlank(message = "La facultad es obligatoria")
    @Column(nullable = false)
    private String facultad;
    
    @Column(length = 200)
    private String titulo;
    
    @Column(length = 20)
    private String telefono;
    
    @Column(length = 100)
    private String oficina;
    
    @Column(name = "fecha_inicio", length = 10)
    private String fechaInicio;
    
    // ✅ RELACIÓN CON USER - COMPLETAMENTE IGNORADA EN JSON
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;
    
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    // Constructores
    public DirectorCarrera() {}

    public DirectorCarrera(String nombre, String apellido, String correo, String codigoDirector,
                          String carrera, String facultad, String titulo, String telefono,
                          String oficina, String fechaInicio, User user) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.codigoDirector = codigoDirector;
        this.carrera = carrera;
        this.facultad = facultad;
        this.titulo = titulo;
        this.telefono = telefono;
        this.oficina = oficina;
        this.fechaInicio = fechaInicio;
        this.user = user;
    }
    
    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }
    
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    
    public String getCodigoDirector() { return codigoDirector; }
    public void setCodigoDirector(String codigoDirector) { this.codigoDirector = codigoDirector; }
    
    public String getCarrera() { return carrera; }
    public void setCarrera(String carrera) { this.carrera = carrera; }
    
    public String getFacultad() { return facultad; }
    public void setFacultad(String facultad) { this.facultad = facultad; }
    
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    
    public String getOficina() { return oficina; }
    public void setOficina(String oficina) { this.oficina = oficina; }
    
    public String getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(String fechaInicio) { this.fechaInicio = fechaInicio; }
    
    @JsonIgnore
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
