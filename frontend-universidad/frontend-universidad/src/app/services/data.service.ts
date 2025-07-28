import { Injectable } from "@angular/core"
import { BehaviorSubject, type Observable } from "rxjs"
import { Estudiante } from "../models/estudiante.model"
import { Profesor } from "../models/profesor.model"
import { DirectorCarrera } from "../models/director-carrera.model"
import { Decano } from "../models/decano.model"
import { Subdecano } from "../models/subdecano.model"

@Injectable({
  providedIn: "root",
})
export class DataService {
  private estudiantesSubject = new BehaviorSubject<Estudiante[]>([])
  private profesoresSubject = new BehaviorSubject<Profesor[]>([])
  private directoresSubject = new BehaviorSubject<DirectorCarrera[]>([])
  private decanosSubject = new BehaviorSubject<Decano[]>([])
  private subdecanosSubject = new BehaviorSubject<Subdecano[]>([])

  public estudiantes$ = this.estudiantesSubject.asObservable()
  public profesores$ = this.profesoresSubject.asObservable()
  public directores$ = this.directoresSubject.asObservable()
  public decanos$ = this.decanosSubject.asObservable()
  public subdecanos$ = this.subdecanosSubject.asObservable()

  private nextId = 1

  constructor() {
    this.initializeData()
  }

  private initializeData(): void {
    // Datos de ejemplo para estudiantes
    const estudiantesEjemplo: Estudiante[] = [
      {
        id: this.nextId++,
        nombre: "Juan",
        apellido: "Pérez",
        correo: "juan.perez@universidad.com",
        codigoEstudiante: "EST001",
        carrera: "Ingeniería de Sistemas",
        semestre: 5,
        promedio: 4.2,
        telefono: "123456789",
        direccion: "Calle 123 #45-67",
      },
      {
        id: this.nextId++,
        nombre: "María",
        apellido: "García",
        correo: "maria.garcia@universidad.com",
        codigoEstudiante: "EST002",
        carrera: "Administración de Empresas",
        semestre: 3,
        promedio: 4.5,
        telefono: "987654321",
        direccion: "Carrera 45 #12-34",
      },
    ]

    // Datos de ejemplo para profesores
    const profesoresEjemplo: Profesor[] = [
      {
        id: this.nextId++,
        nombre: "Carlos",
        apellido: "López",
        correo: "carlos.lopez@universidad.com",
        codigoProfesor: "PROF001",
        especialidad: "Matemáticas",
        departamento: "Ciencias Exactas",
        titulo: "PhD en Matemáticas",
        telefono: "555123456",
        oficina: "Edificio A - 201",
        horarioAtencion: "Lunes a Viernes 2-4 PM",
      },
    ]

    // Datos de ejemplo para directores
    const directoresEjemplo: DirectorCarrera[] = [
      {
        id: this.nextId++,
        nombre: "Ana",
        apellido: "Rodríguez",
        correo: "ana.rodriguez@universidad.com",
        codigoDirector: "DIR001",
        carrera: "Ingeniería de Sistemas",
        facultad: "Ingeniería",
        titulo: "MSc en Ingeniería de Software",
        telefono: "555987654",
        oficina: "Edificio B - 301",
        fechaInicio: "2023-01-15",
      },
    ]

    this.estudiantesSubject.next(estudiantesEjemplo)
    this.profesoresSubject.next(profesoresEjemplo)
    this.directoresSubject.next(directoresEjemplo)
    this.decanosSubject.next([])
    this.subdecanosSubject.next([])
  }

  // Métodos para Estudiantes
  getEstudiantes(): Observable<Estudiante[]> {
    return this.estudiantes$
  }

  addEstudiante(estudiante: Estudiante): void {
    const estudiantes = this.estudiantesSubject.value
    estudiante.id = this.nextId++
    estudiante.createdAt = new Date().toISOString()
    estudiantes.push(estudiante)
    this.estudiantesSubject.next([...estudiantes])
  }

  updateEstudiante(id: number, estudiante: Estudiante): void {
    const estudiantes = this.estudiantesSubject.value
    const index = estudiantes.findIndex((e) => e.id === id)
    if (index !== -1) {
      estudiantes[index] = { ...estudiante, id, updatedAt: new Date().toISOString() }
      this.estudiantesSubject.next([...estudiantes])
    }
  }

  deleteEstudiante(id: number): void {
    const estudiantes = this.estudiantesSubject.value.filter((e) => e.id !== id)
    this.estudiantesSubject.next(estudiantes)
  }

  // Métodos para Profesores
  getProfesores(): Observable<Profesor[]> {
    return this.profesores$
  }

  addProfesor(profesor: Profesor): void {
    const profesores = this.profesoresSubject.value
    profesor.id = this.nextId++
    profesor.createdAt = new Date().toISOString()
    profesores.push(profesor)
    this.profesoresSubject.next([...profesores])
  }

  updateProfesor(id: number, profesor: Profesor): void {
    const profesores = this.profesoresSubject.value
    const index = profesores.findIndex((p) => p.id === id)
    if (index !== -1) {
      profesores[index] = { ...profesor, id, updatedAt: new Date().toISOString() }
      this.profesoresSubject.next([...profesores])
    }
  }

  deleteProfesor(id: number): void {
    const profesores = this.profesoresSubject.value.filter((p) => p.id !== id)
    this.profesoresSubject.next(profesores)
  }

  // Métodos para Directores de Carrera
  getDirectores(): Observable<DirectorCarrera[]> {
    return this.directores$
  }

  addDirector(director: DirectorCarrera): void {
    const directores = this.directoresSubject.value
    director.id = this.nextId++
    director.createdAt = new Date().toISOString()
    directores.push(director)
    this.directoresSubject.next([...directores])
  }

  updateDirector(id: number, director: DirectorCarrera): void {
    const directores = this.directoresSubject.value
    const index = directores.findIndex((d) => d.id === id)
    if (index !== -1) {
      directores[index] = { ...director, id, updatedAt: new Date().toISOString() }
      this.directoresSubject.next([...directores])
    }
  }

  deleteDirector(id: number): void {
    const directores = this.directoresSubject.value.filter((d) => d.id !== id)
    this.directoresSubject.next(directores)
  }

  // Métodos para Decanos
  getDecanos(): Observable<Decano[]> {
    return this.decanos$
  }

  addDecano(decano: Decano): void {
    const decanos = this.decanosSubject.value
    decano.id = this.nextId++
    decano.createdAt = new Date().toISOString()
    decanos.push(decano)
    this.decanosSubject.next([...decanos])
  }

  updateDecano(id: number, decano: Decano): void {
    const decanos = this.decanosSubject.value
    const index = decanos.findIndex((d) => d.id === id)
    if (index !== -1) {
      decanos[index] = { ...decano, id, updatedAt: new Date().toISOString() }
      this.decanosSubject.next([...decanos])
    }
  }

  deleteDecano(id: number): void {
    const decanos = this.decanosSubject.value.filter((d) => d.id !== id)
    this.decanosSubject.next(decanos)
  }

  // Métodos para Subdecanos
  getSubdecanos(): Observable<Subdecano[]> {
    return this.subdecanos$
  }

  addSubdecano(subdecano: Subdecano): void {
    const subdecanos = this.subdecanosSubject.value
    subdecano.id = this.nextId++
    subdecano.createdAt = new Date().toISOString()
    subdecanos.push(subdecano)
    this.subdecanosSubject.next([...subdecanos])
  }

  updateSubdecano(id: number, subdecano: Subdecano): void {
    const subdecanos = this.subdecanosSubject.value
    const index = subdecanos.findIndex((s) => s.id === id)
    if (index !== -1) {
      subdecanos[index] = { ...subdecano, id, updatedAt: new Date().toISOString() }
      this.subdecanosSubject.next([...subdecanos])
    }
  }

  deleteSubdecano(id: number): void {
    const subdecanos = this.subdecanosSubject.value.filter((s) => s.id !== id)
    this.subdecanosSubject.next(subdecanos)
  }
}
