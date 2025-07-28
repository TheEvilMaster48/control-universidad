package com.universidad.dto;

public class AuthResponse {
    private String token;
    private String type = "Bearer";
    private String usuario;
    private String email;
    private String role;

    public AuthResponse(String token, String usuario, String email, String role) {
        this.token = token;
        this.usuario = usuario;
        this.email = email;
        this.role = role;
    }

    // Getters y Setters
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}
