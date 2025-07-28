export interface Estudiante {
  id?: number
  nombre: string
  apellido: string
  correo: string
  codigoEstudiante: string
  carrera: string
  semestre?: number
  promedio?: number
  telefono?: string
  direccion?: string
  createdAt?: string
  updatedAt?: string
}

export interface EstudianteRequest {
  nombre: string
  apellido: string
  correo: string
  codigoEstudiante: string
  carrera: string
  semestre?: number
  promedio?: number
  telefono?: string
  direccion?: string
}
