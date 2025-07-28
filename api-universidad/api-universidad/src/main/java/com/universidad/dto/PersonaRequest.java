package com.universidad.dto;

import com.universidad.enums.TipoPersona;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class PersonaRequest {
  @NotBlank(message = "El nombre es obligatorio")
  private String nombre;

  @NotBlank(message = "El apellido es obligatorio")
  private String apellido;

  @NotBlank(message = "El correo es obligatorio")
  @Email(message = "Debe ser un correo válido")
  private String correo;

  @NotNull(message = "El tipo de persona es obligatorio")
  private TipoPersona tipo;

  private String especialidad;
  private String codigo;

  // Constructores
  public PersonaRequest() {}

  // Getters y Setters
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
}
