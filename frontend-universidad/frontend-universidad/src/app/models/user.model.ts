export interface LoginRequest {
  usuario: string
  password: string
}

export interface LoginResponse {
  token: string
  usuario: string
  email: string
  role: string
}

export interface AuthResponse {
  token: string
  usuario: string
  email: string
  role: string
}

export interface RegisterRequest {
  usuario: string
  correo: string
  password: string
  verificarPassword: string
  nombre: string
  apellido: string
  role?: string
}

export interface User {
  id?: number
  usuario: string
  email: string
  role: string
  nombre?: string
  apellido?: string
  createdAt?: string
  updatedAt?: string
}
