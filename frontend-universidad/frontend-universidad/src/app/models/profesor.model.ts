export interface Profesor {
  id?: number
  nombre: string
  apellido: string
  correo: string
  codigoProfesor: string
  especialidad: string
  departamento: string
  titulo?: string
  telefono?: string
  oficina?: string
  horarioAtencion?: string
  createdAt?: string
  updatedAt?: string
}

export interface ProfesorRequest {
  nombre: string
  apellido: string
  correo: string
  codigoProfesor: string
  especialidad: string
  departamento: string
  titulo?: string
  telefono?: string
  oficina?: string
  horarioAtencion?: string
}
