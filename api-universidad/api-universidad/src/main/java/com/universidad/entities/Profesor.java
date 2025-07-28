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
@Table(name = "profesores")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Profesor {
    
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
    
    @NotBlank(message = "El código de profesor es obligatorio")
    @Column(nullable = false, unique = true, name = "codigo_profesor")
    private String codigoProfesor;
    
    @NotBlank(message = "La especialidad es obligatoria")
    @Column(nullable = false)
    private String especialidad;
    
    @NotBlank(message = "El departamento es obligatorio")
    @Column(nullable = false)
    private String departamento;
    
    @Column(length = 200)
    private String titulo;
    
    @Column(length = 20)
    private String telefono;
    
    @Column(length = 100)
    private String oficina;
    
    @Column(name = "horario_atencion", length = 200)
    private String horarioAtencion;
    
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
    public Profesor() {}

    public Profesor(String nombre, String apellido, String correo, String codigoProfesor,
                   String especialidad, String departamento, String titulo, String telefono,
                   String oficina, String horarioAtencion, User user) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.codigoProfesor = codigoProfesor;
        this.especialidad = especialidad;
        this.departamento = departamento;
        this.titulo = titulo;
        this.telefono = telefono;
        this.oficina = oficina;
        this.horarioAtencion = horarioAtencion;
        this.user = user;
    }
    
    public Profesor(String nombre, String apellido, String correo, String codigoProfesor, 
                   String especialidad, String departamento) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.codigoProfesor = codigoProfesor;
        this.especialidad = especialidad;
        this.departamento = departamento;
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
    
    public String getCodigoProfesor() { return codigoProfesor; }
    public void setCodigoProfesor(String codigoProfesor) { this.codigoProfesor = codigoProfesor; }
    
    public String getEspecialidad() { return especialidad; }
    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }
    
    public String getDepartamento() { return departamento; }
    public void setDepartamento(String departamento) { this.departamento = departamento; }
    
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    
    public String getOficina() { return oficina; }
    public void setOficina(String oficina) { this.oficina = oficina; }
    
    public String getHorarioAtencion() { return horarioAtencion; }
    public void setHorarioAtencion(String horarioAtencion) { this.horarioAtencion = horarioAtencion; }
    
    @JsonIgnore
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
