import { Injectable } from "@angular/core"
import { HttpClient, HttpHeaders } from "@angular/common/http"
import { Observable } from "rxjs"
import { Decano, DecanoRequest } from "../models/decano.model"
import { AuthService } from "./auth.service"

@Injectable({
  providedIn: "root",
})
export class DecanoService {
  private apiUrl = "http://localhost:3002/api/decanos"

  constructor(
    private http: HttpClient,
    private authService: AuthService,
  ) {}

  private getHeaders(): HttpHeaders {
    return new HttpHeaders(this.authService.getAuthHeaders())
  }

  getAll(): Observable<Decano[]> {
    return this.http.get<Decano[]>(this.apiUrl, { headers: this.getHeaders() })
  }

  getById(id: number): Observable<Decano> {
    return this.http.get<Decano>(`${this.apiUrl}/${id}`, { headers: this.getHeaders() })
  }

  create(decano: DecanoRequest): Observable<Decano> {
    return this.http.post<Decano>(this.apiUrl, decano, { headers: this.getHeaders() })
  }

  update(id: number, decano: DecanoRequest): Observable<Decano> {
    return this.http.put<Decano>(`${this.apiUrl}/${id}`, decano, { headers: this.getHeaders() })
  }

  delete(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${id}`, { headers: this.getHeaders() })
  }
}
