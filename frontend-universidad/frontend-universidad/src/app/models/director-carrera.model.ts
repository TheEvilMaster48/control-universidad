export interface DirectorCarrera {
  id?: number
  nombre: string
  apellido: string
  correo: string
  codigoDirector: string
  carrera: string
  facultad: string
  titulo?: string
  telefono?: string
  oficina?: string
  fechaInicio?: string
  createdAt?: string
  updatedAt?: string
}

export interface DirectorCarreraRequest {
  nombre: string
  apellido: string
  correo: string
  codigoDirector: string
  carrera: string
  facultad: string
  titulo?: string
  telefono?: string
  oficina?: string
  fechaInicio?: string
}
