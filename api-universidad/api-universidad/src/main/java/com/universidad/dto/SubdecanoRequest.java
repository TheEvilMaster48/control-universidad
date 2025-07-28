package com.universidad.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class SubdecanoRequest {
    
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede exceder 100 caracteres")
    private String nombre;
    
    @NotBlank(message = "El apellido es obligatorio")
    @Size(max = 100, message = "El apellido no puede exceder 100 caracteres")
    private String apellido;
    
    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "Debe ser un correo válido")
    private String correo;
    
    @NotBlank(message = "El código de subdecano es obligatorio")
    private String codigoSubdecano;
    
    @NotBlank(message = "La facultad es obligatoria")
    private String facultad;
    
    @NotBlank(message = "El área de responsabilidad es obligatoria")
    private String areaResponsabilidad;
    
    private String titulo;
    private String telefono;
    private String oficina;
    private String fechaInicio;
    
    // Constructores
    public SubdecanoRequest() {}
    
    // Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }
    
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    
    public String getCodigoSubdecano() { return codigoSubdecano; }
    public void setCodigoSubdecano(String codigoSubdecano) { this.codigoSubdecano = codigoSubdecano; }
    
    public String getFacultad() { return facultad; }
    public void setFacultad(String facultad) { this.facultad = facultad; }
    
    public String getAreaResponsabilidad() { return areaResponsabilidad; }
    public void setAreaResponsabilidad(String areaResponsabilidad) { this.areaResponsabilidad = areaResponsabilidad; }
    
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    
    public String getOficina() { return oficina; }
    public void setOficina(String oficina) { this.oficina = oficina; }
    
    public String getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(String fechaInicio) { this.fechaInicio = fechaInicio; }
}
