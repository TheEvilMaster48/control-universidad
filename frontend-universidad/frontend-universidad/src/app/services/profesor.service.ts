import { Injectable } from "@angular/core"
import { HttpClient, HttpHeaders } from "@angular/common/http"
import { Observable } from "rxjs"
import { Profesor, ProfesorRequest } from "../models/profesor.model"
import { AuthService } from "./auth.service"

@Injectable({
  providedIn: "root",
})
export class ProfesorService {
  private apiUrl = "http://localhost:3002/api/profesores"

  constructor(
    private http: HttpClient,
    private authService: AuthService,
  ) {}

  private getHeaders(): HttpHeaders {
    return new HttpHeaders(this.authService.getAuthHeaders())
  }

  getAll(): Observable<Profesor[]> {
    return this.http.get<Profesor[]>(this.apiUrl, { headers: this.getHeaders() })
  }

  getById(id: number): Observable<Profesor> {
    return this.http.get<Profesor>(`${this.apiUrl}/${id}`, { headers: this.getHeaders() })
  }

  create(profesor: ProfesorRequest): Observable<Profesor> {
    return this.http.post<Profesor>(this.apiUrl, profesor, { headers: this.getHeaders() })
  }

  update(id: number, profesor: ProfesorRequest): Observable<Profesor> {
    return this.http.put<Profesor>(`${this.apiUrl}/${id}`, profesor, { headers: this.getHeaders() })
  }

  delete(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${id}`, { headers: this.getHeaders() })
  }
}
