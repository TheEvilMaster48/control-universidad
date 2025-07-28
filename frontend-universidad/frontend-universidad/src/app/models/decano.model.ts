export interface Decano {
  id?: number
  nombre: string
  apellido: string
  correo: string
  codigoDecano: string
  facultad: string
  titulo?: string
  telefono?: string
  oficina?: string
  fechaInicio?: string
  experienciaAcademica?: string
  createdAt?: string
  updatedAt?: string
}

export interface DecanoRequest {
  nombre: string
  apellido: string
  correo: string
  codigoDecano: string
  facultad: string
  titulo?: string
  telefono?: string
  oficina?: string
  fechaInicio?: string
  experienciaAcademica?: string
}
