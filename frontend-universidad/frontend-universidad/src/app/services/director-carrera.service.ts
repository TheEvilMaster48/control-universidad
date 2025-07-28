import { Injectable } from "@angular/core"
import { HttpClient, HttpHeaders } from "@angular/common/http"
import { Observable } from "rxjs"
import { DirectorCarrera, DirectorCarreraRequest } from "../models/director-carrera.model"
import { AuthService } from "./auth.service"

@Injectable({
  providedIn: "root",
})
export class DirectorCarreraService {
  private apiUrl = "http://localhost:3002/api/directores-carrera"

  constructor(
    private http: HttpClient,
    private authService: AuthService,
  ) {}

  private getHeaders(): HttpHeaders {
    return new HttpHeaders(this.authService.getAuthHeaders())
  }

  getAll(): Observable<DirectorCarrera[]> {
    return this.http.get<DirectorCarrera[]>(this.apiUrl, { headers: this.getHeaders() })
  }

  getById(id: number): Observable<DirectorCarrera> {
    return this.http.get<DirectorCarrera>(`${this.apiUrl}/${id}`, { headers: this.getHeaders() })
  }

  create(director: DirectorCarreraRequest): Observable<DirectorCarrera> {
    return this.http.post<DirectorCarrera>(this.apiUrl, director, { headers: this.getHeaders() })
  }

  update(id: number, director: DirectorCarreraRequest): Observable<DirectorCarrera> {
    return this.http.put<DirectorCarrera>(`${this.apiUrl}/${id}`, director, { headers: this.getHeaders() })
  }

  delete(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${id}`, { headers: this.getHeaders() })
  }
}
