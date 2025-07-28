import { Component } from "@angular/core"
import { CommonModule } from "@angular/common"
import { FormsModule } from "@angular/forms"
import { Router } from "@angular/router"
import { AuthService } from "../../services/auth.service"
import { LoginRequest, RegisterRequest } from "../../models/user.model"

@Component({
  selector: "app-login",
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: "./login.component.html",
  styleUrls: ["./login.component.css"],
})
export class LoginComponent {
  showRegister = false

  credentials: LoginRequest = {
    usuario: "",
    password: "",
  }

  registerData: RegisterRequest = {
    usuario: "",
    correo: "",
    password: "",
    verificarPassword: "",
    nombre: "",
    apellido: "",
    role: "USUARIO",
  }

  loading = false
  error = ""
  success = ""

  constructor(
    private authService: AuthService,
    private router: Router,
  ) {
    if (this.authService.isAuthenticated()) {
      this.router.navigate(["/dashboard"])
    }
  }

  onSubmit(): void {
    if (!this.credentials.usuario || !this.credentials.password) {
      this.error = "Por favor complete todos los campos"
      return
    }

    this.loading = true
    this.error = ""
    this.success = ""

    console.log("Intentando login con:", this.credentials.usuario)

    this.authService.login(this.credentials).subscribe({
      next: (response) => {
        console.log("Login exitoso:", response)
        this.loading = false
        this.success = "Login exitoso, redirigiendo..."
        setTimeout(() => {
          this.router.navigate(["/dashboard"])
        }, 1000)
      },
      error: (error) => {
        console.error("Error de login:", error)
        this.loading = false
        if (error.status === 0) {
          this.error = "No se puede conectar al servidor. Verifique que el backend esté ejecutándose en puerto 3002."
        } else if (error.status === 401 || error.status === 400) {
          this.error = "Usuario o contraseña incorrectos"
        } else {
          this.error = "Error del servidor. Intente nuevamente."
        }
      },
    })
  }

  onRegister(): void {
    if (
      !this.registerData.usuario ||
      !this.registerData.correo ||
      !this.registerData.password ||
      !this.registerData.nombre ||
      !this.registerData.apellido
    ) {
      this.error = "Por favor complete todos los campos"
      return
    }

    if (this.registerData.password !== this.registerData.verificarPassword) {
      this.error = "Las contraseñas no coinciden"
      return
    }

    this.loading = true
    this.error = ""
    this.success = ""

    console.log("Intentando registro con:", this.registerData.usuario)

    this.authService.register(this.registerData).subscribe({
      next: (response) => {
        console.log("Registro exitoso:", response)
        this.loading = false
        this.success = "Registro exitoso. Ahora puede iniciar sesión."
        this.showRegister = false
        this.registerData = {
          usuario: "",
          correo: "",
          password: "",
          verificarPassword: "",
          nombre: "",
          apellido: "",
          role: "USUARIO",
        }
      },
      error: (error) => {
        console.error("Error de registro:", error)
        this.loading = false
        if (error.status === 0) {
          this.error = "No se puede conectar al servidor."
        } else if (error.error && error.error.message) {
          this.error = error.error.message
        } else {
          this.error = "Error al registrar usuario. Intente nuevamente."
        }
      },
    })
  }

  toggleMode(): void {
    this.showRegister = !this.showRegister
    this.error = ""
    this.success = ""
    this.credentials = { usuario: "", password: "" }
    this.registerData = {
      usuario: "",
      correo: "",
      password: "",
      verificarPassword: "",
      nombre: "",
      apellido: "",
      role: "USUARIO",
    }
  }

  testConnection(): void {
    console.log("Probando conexión al backend...")
    fetch("http://localhost:3002/api/auth/test")
      .then((response) => {
        if (response.ok) {
          this.success = "Conexión al backend exitosa"
          console.log("Backend conectado correctamente")
        } else {
          this.error = "Backend responde pero hay un error"
        }
      })
      .catch((error) => {
        this.error = "No se puede conectar al backend en puerto 3002"
        console.error("Error de conexión:", error)
      })
  }
}
