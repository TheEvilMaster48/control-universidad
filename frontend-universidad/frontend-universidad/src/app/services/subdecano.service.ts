import { Injectable } from "@angular/core"
import { HttpClient, HttpHeaders } from "@angular/common/http"
import { Observable } from "rxjs"
import { Subdecano, SubdecanoRequest } from "../models/subdecano.model"
import { AuthService } from "./auth.service"

@Injectable({
  providedIn: "root",
})
export class SubdecanoService {
  private apiUrl = "http://localhost:3002/api/subdecanos"

  constructor(
    private http: HttpClient,
    private authService: AuthService,
  ) {}

  private getHeaders(): HttpHeaders {
    return new HttpHeaders(this.authService.getAuthHeaders())
  }

  getAll(): Observable<Subdecano[]> {
    return this.http.get<Subdecano[]>(this.apiUrl, { headers: this.getHeaders() })
  }

  getById(id: number): Observable<Subdecano> {
    return this.http.get<Subdecano>(`${this.apiUrl}/${id}`, { headers: this.getHeaders() })
  }

  create(subdecano: SubdecanoRequest): Observable<Subdecano> {
    return this.http.post<Subdecano>(this.apiUrl, subdecano, { headers: this.getHeaders() })
  }

  update(id: number, subdecano: SubdecanoRequest): Observable<Subdecano> {
    return this.http.put<Subdecano>(`${this.apiUrl}/${id}`, subdecano, { headers: this.getHeaders() })
  }

  delete(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${id}`, { headers: this.getHeaders() })
  }
}
