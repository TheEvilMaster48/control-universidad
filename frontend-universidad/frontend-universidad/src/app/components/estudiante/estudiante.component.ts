import { Component, type OnInit } from "@angular/core"
import { CommonModule } from "@angular/common"
import { FormsModule } from "@angular/forms"
import { Router } from "@angular/router"
import { EstudianteService } from "../../services/estudiante.service"
import { Estudiante, EstudianteRequest } from "../../models/estudiante.model"

@Component({
  selector: "app-estudiante",
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: "./estudiante.component.html",
  styleUrls: ["./estudiante.component.css"],
})
export class EstudianteComponent implements OnInit {
  estudiantes: Estudiante[] = []
  loading = false
  showForm = false
  editMode = false
  currentEstudiante: EstudianteRequest = this.getEmptyEstudiante()
  editId: number | null = null
  message = ""
  messageType = ""

  constructor(
    private estudianteService: EstudianteService,
    private router: Router,
  ) {}

  ngOnInit(): void {
    this.loadEstudiantes()
  }

  getEmptyEstudiante(): EstudianteRequest {
    return {
      nombre: "",
      apellido: "",
      correo: "",
      codigoEstudiante: "",
      carrera: "",
      semestre: undefined,
      promedio: undefined,
      telefono: "",
      direccion: "",
    }
  }

  loadEstudiantes(): void {
    this.loading = true
    this.estudianteService.getAll().subscribe({
      next: (data) => {
        this.estudiantes = data
        this.loading = false
      },
      error: (error) => {
        console.error("Error al cargar estudiantes:", error)
        this.showMessage("Error al cargar estudiantes", "error")
        this.loading = false
      },
    })
  }

  showCreateForm(): void {
    this.showForm = true
    this.editMode = false
    this.currentEstudiante = this.getEmptyEstudiante()
    this.editId = null
  }

  showEditForm(estudiante: Estudiante): void {
    this.showForm = true
    this.editMode = true
    this.editId = estudiante.id!
    this.currentEstudiante = {
      nombre: estudiante.nombre,
      apellido: estudiante.apellido,
      correo: estudiante.correo,
      codigoEstudiante: estudiante.codigoEstudiante,
      carrera: estudiante.carrera,
      semestre: estudiante.semestre,
      promedio: estudiante.promedio,
      telefono: estudiante.telefono,
      direccion: estudiante.direccion,
    }
  }

  hideForm(): void {
    this.showForm = false
    this.editMode = false
    this.currentEstudiante = this.getEmptyEstudiante()
    this.editId = null
  }

  onSubmit(): void {
    if (!this.isFormValid()) {
      this.showMessage("Por favor complete todos los campos obligatorios", "error")
      return
    }

    this.loading = true

    if (this.editMode && this.editId) {
      this.estudianteService.update(this.editId, this.currentEstudiante).subscribe({
        next: (response) => {
          this.showMessage("Estudiante actualizado exitosamente", "success")
          this.loadEstudiantes()
          this.hideForm()
          this.loading = false
        },
        error: (error) => {
          console.error("Error al actualizar estudiante:", error)
          this.showMessage("Error al actualizar estudiante", "error")
          this.loading = false
        },
      })
    } else {
      this.estudianteService.create(this.currentEstudiante).subscribe({
        next: (response) => {
          this.showMessage("Estudiante creado exitosamente", "success")
          this.loadEstudiantes()
          this.hideForm()
          this.loading = false
        },
        error: (error) => {
          console.error("Error al crear estudiante:", error)
          this.showMessage("Error al crear estudiante", "error")
          this.loading = false
        },
      })
    }
  }

  deleteEstudiante(id: number): void {
    if (confirm("¿Está seguro de que desea eliminar este estudiante?")) {
      this.loading = true
      this.estudianteService.delete(id).subscribe({
        next: (response) => {
          this.showMessage("Estudiante eliminado exitosamente", "success")
          this.loadEstudiantes()
          this.loading = false
        },
        error: (error) => {
          console.error("Error al eliminar estudiante:", error)
          this.showMessage("Error al eliminar estudiante", "error")
          this.loading = false
        },
      })
    }
  }

  isFormValid(): boolean {
    return !!(
      this.currentEstudiante.nombre &&
      this.currentEstudiante.apellido &&
      this.currentEstudiante.correo &&
      this.currentEstudiante.codigoEstudiante &&
      this.currentEstudiante.carrera
    )
  }

  showMessage(message: string, type: string): void {
    this.message = message
    this.messageType = type
    setTimeout(() => {
      this.message = ""
      this.messageType = ""
    }, 5000)
  }

  goBack(): void {
    this.router.navigate(["/dashboard"])
  }
}
