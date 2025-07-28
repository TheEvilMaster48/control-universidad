import { Component, type OnInit } from "@angular/core"
import { CommonModule } from "@angular/common"
import { FormsModule } from "@angular/forms"
import { Router } from "@angular/router"
import { ProfesorService } from "../../services/profesor.service"
import { Profesor, ProfesorRequest } from "../../models/profesor.model"

@Component({
  selector: "app-profesor",
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: "./profesor.component.html",
  styleUrls: ["./profesor.component.css"],
})
export class ProfesorComponent implements OnInit {
  profesores: Profesor[] = []
  loading = false
  showForm = false
  editMode = false
  currentProfesor: ProfesorRequest = this.getEmptyProfesor()
  editId: number | null = null
  message = ""
  messageType = ""

  constructor(
    private profesorService: ProfesorService,
    private router: Router,
  ) {}

  ngOnInit(): void {
    this.loadProfesores()
  }

  getEmptyProfesor(): ProfesorRequest {
    return {
      nombre: "",
      apellido: "",
      correo: "",
      codigoProfesor: "",
      especialidad: "",
      departamento: "",
      titulo: "",
      telefono: "",
      oficina: "",
      horarioAtencion: "",
    }
  }

  loadProfesores(): void {
    this.loading = true
    this.profesorService.getAll().subscribe({
      next: (data) => {
        this.profesores = data
        this.loading = false
      },
      error: (error) => {
        console.error("Error al cargar profesores:", error)
        this.showMessage("Error al cargar profesores", "error")
        this.loading = false
      },
    })
  }

  showCreateForm(): void {
    this.showForm = true
    this.editMode = false
    this.currentProfesor = this.getEmptyProfesor()
    this.editId = null
  }

  showEditForm(profesor: Profesor): void {
    this.showForm = true
    this.editMode = true
    this.editId = profesor.id!
    this.currentProfesor = {
      nombre: profesor.nombre,
      apellido: profesor.apellido,
      correo: profesor.correo,
      codigoProfesor: profesor.codigoProfesor,
      especialidad: profesor.especialidad,
      departamento: profesor.departamento,
      titulo: profesor.titulo,
      telefono: profesor.telefono,
      oficina: profesor.oficina,
      horarioAtencion: profesor.horarioAtencion,
    }
  }

  hideForm(): void {
    this.showForm = false
    this.editMode = false
    this.currentProfesor = this.getEmptyProfesor()
    this.editId = null
  }

  onSubmit(): void {
    if (!this.isFormValid()) {
      this.showMessage("Por favor complete todos los campos obligatorios", "error")
      return
    }

    this.loading = true

    if (this.editMode && this.editId) {
      this.profesorService.update(this.editId, this.currentProfesor).subscribe({
        next: (response) => {
          this.showMessage("Profesor actualizado exitosamente", "success")
          this.loadProfesores()
          this.hideForm()
          this.loading = false
        },
        error: (error) => {
          console.error("Error al actualizar profesor:", error)
          this.showMessage("Error al actualizar profesor", "error")
          this.loading = false
        },
      })
    } else {
      this.profesorService.create(this.currentProfesor).subscribe({
        next: (response) => {
          this.showMessage("Profesor creado exitosamente", "success")
          this.loadProfesores()
          this.hideForm()
          this.loading = false
        },
        error: (error) => {
          console.error("Error al crear profesor:", error)
          this.showMessage("Error al crear profesor", "error")
          this.loading = false
        },
      })
    }
  }

  deleteProfesor(id: number): void {
    if (confirm("¿Está seguro de que desea eliminar este profesor?")) {
      this.loading = true
      this.profesorService.delete(id).subscribe({
        next: (response) => {
          this.showMessage("Profesor eliminado exitosamente", "success")
          this.loadProfesores()
          this.loading = false
        },
        error: (error) => {
          console.error("Error al eliminar profesor:", error)
          this.showMessage("Error al eliminar profesor", "error")
          this.loading = false
        },
      })
    }
  }

  isFormValid(): boolean {
    return !!(
      this.currentProfesor.nombre &&
      this.currentProfesor.apellido &&
      this.currentProfesor.correo &&
      this.currentProfesor.codigoProfesor &&
      this.currentProfesor.especialidad &&
      this.currentProfesor.departamento
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
