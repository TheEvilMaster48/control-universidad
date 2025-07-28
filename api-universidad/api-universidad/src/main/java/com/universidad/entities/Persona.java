package com.universidad.entities;

import com.universidad.enums.TipoPersona;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "personas")
public class Persona {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "El nombre es obligatorio")
  private String nombre;

  @NotBlank(message = "El apellido es obligatorio")
  private String apellido;

  @NotBlank(message = "El correo es obligatorio")
  @Email(message = "Debe ser un correo válido")
  @Column(unique = true)
  private String correo;

  @NotNull(message = "El tipo de persona es obligatorio")
  @Enumerated(EnumType.STRING)
  private TipoPersona tipo;

  private String especialidad;

  @Column(unique = true)
  private String codigo;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  @CreationTimestamp
  private LocalDateTime createdAt;

  @UpdateTimestamp
  private LocalDateTime updatedAt;

  // Constructores
  public Persona() {}

  public Persona(String nombre, String apellido, String correo, TipoPersona tipo, String especialidad, String codigo, User user) {
      this.nombre = nombre;
      this.apellido = apellido;
      this.correo = correo;
      this.tipo = tipo;
      this.especialidad = especialidad;
      this.codigo = codigo;
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

  public TipoPersona getTipo() { return tipo; }
  public void setTipo(TipoPersona tipo) { this.tipo = tipo; }

  public String getEspecialidad() { return especialidad; }
  public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }

  public String getCodigo() { return codigo; }
  public void setCodigo(String codigo) { this.codigo = codigo; }

  public User getUser() { return user; }
  public void setUser(User user) { this.user = user; }

  public LocalDateTime getCreatedAt() { return createdAt; }
  public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

  public LocalDateTime getUpdatedAt() { return updatedAt; }
  public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
