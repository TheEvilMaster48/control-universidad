import { Injectable } from "@angular/core"
import { HttpClient, HttpHeaders } from "@angular/common/http"
import { Observable } from "rxjs"
import { Estudiante, EstudianteRequest } from "../models/estudiante.model"
import { AuthService } from "./auth.service"

@Injectable({
  providedIn: "root",
})
export class EstudianteService {
  private apiUrl = "http://localhost:3002/api/estudiantes"

  constructor(
    private http: HttpClient,
    private authService: AuthService,
  ) {}

  private getHeaders(): HttpHeaders {
    return new HttpHeaders(this.authService.getAuthHeaders())
  }

  getAll(): Observable<Estudiante[]> {
    return this.http.get<Estudiante[]>(this.apiUrl, { headers: this.getHeaders() })
  }

  getById(id: number): Observable<Estudiante> {
    return this.http.get<Estudiante>(`${this.apiUrl}/${id}`, { headers: this.getHeaders() })
  }

  create(estudiante: EstudianteRequest): Observable<Estudiante> {
    return this.http.post<Estudiante>(this.apiUrl, estudiante, { headers: this.getHeaders() })
  }

  update(id: number, estudiante: EstudianteRequest): Observable<Estudiante> {
    return this.http.put<Estudiante>(`${this.apiUrl}/${id}`, estudiante, { headers: this.getHeaders() })
  }

  delete(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${id}`, { headers: this.getHeaders() })
  }
}
