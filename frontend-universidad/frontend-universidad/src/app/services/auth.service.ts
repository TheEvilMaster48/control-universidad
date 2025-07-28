import { Injectable } from "@angular/core"
import { HttpClient } from "@angular/common/http"
import { type Observable, BehaviorSubject } from "rxjs"
import { tap } from "rxjs/operators"
import { LoginRequest, LoginResponse, RegisterRequest } from "../models/user.model"

@Injectable({
  providedIn: "root",
})
export class AuthService {
  private apiUrl = "http://localhost:3002/api/auth"
  private currentUserSubject = new BehaviorSubject<any>(null)
  public currentUser$ = this.currentUserSubject.asObservable()

  constructor(private http: HttpClient) {
    this.loadCurrentUser()
  }

  login(credentials: LoginRequest): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.apiUrl}/signin`, credentials).pipe(
      tap((response) => {
        localStorage.setItem("token", response.token)
        localStorage.setItem(
          "user",
          JSON.stringify({
            usuario: response.usuario,
            email: response.email,
            role: response.role,
          }),
        )
        this.currentUserSubject.next({
          usuario: response.usuario,
          email: response.email,
          role: response.role,
        })
      }),
    )
  }

  register(userData: RegisterRequest): Observable<any> {
    return this.http.post(`${this.apiUrl}/signup`, userData)
  }

  logout(): void {
    localStorage.removeItem("token")
    localStorage.removeItem("user")
    this.currentUserSubject.next(null)
  }

  getToken(): string | null {
    return localStorage.getItem("token")
  }

  isAuthenticated(): boolean {
    return !!this.getToken()
  }

  isAdmin(): boolean {
    const user = this.getCurrentUser()
    return user && user.role === "ADMIN"
  }

  getCurrentUser(): any {
    return this.currentUserSubject.value
  }

  private loadCurrentUser(): void {
    const userStr = localStorage.getItem("user")
    if (userStr) {
      this.currentUserSubject.next(JSON.parse(userStr))
    }
  }

  getAuthHeaders(): any {
    const token = this.getToken()
    return token ? { Authorization: `Bearer ${token}` } : {}
  }
}
