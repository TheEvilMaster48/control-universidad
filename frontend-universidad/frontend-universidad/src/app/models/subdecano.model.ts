export interface Subdecano {
  id?: number
  nombre: string
  apellido: string
  correo: string
  codigoSubdecano: string
  facultad: string
  areaResponsabilidad: string
  titulo?: string
  telefono?: string
  oficina?: string
  fechaInicio?: string
  createdAt?: string
  updatedAt?: string
}

export interface SubdecanoRequest {
  nombre: string
  apellido: string
  correo: string
  codigoSubdecano: string
  facultad: string
  areaResponsabilidad: string
  titulo?: string
  telefono?: string
  oficina?: string
  fechaInicio?: string
}
