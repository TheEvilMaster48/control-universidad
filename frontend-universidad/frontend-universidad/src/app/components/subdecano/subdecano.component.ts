import { Component, type OnInit } from "@angular/core"
import { CommonModule } from "@angular/common"
import { FormsModule } from "@angular/forms"
import { Router } from "@angular/router"
import { SubdecanoService } from "../../services/subdecano.service"
import { Subdecano, SubdecanoRequest } from "../../models/subdecano.model"

@Component({
  selector: "app-subdecano",
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: "./subdecano.component.html",
  styleUrls: ["./subdecano.component.css"],
})
export class SubdecanoComponent implements OnInit {
  subdecanos: Subdecano[] = []
  loading = false
  showForm = false
  editMode = false
  currentSubdecano: SubdecanoRequest = this.getEmptySubdecano()
  editId: number | null = null
  message = ""
  messageType = ""

  constructor(
    private subdecanoService: SubdecanoService,
    private router: Router,
  ) {}

  ngOnInit(): void {
    this.loadSubdecanos()
  }

  getEmptySubdecano(): SubdecanoRequest {
    return {
      nombre: "",
      apellido: "",
      correo: "",
      codigoSubdecano: "",
      facultad: "",
      areaResponsabilidad: "",
      titulo: "",
      telefono: "",
      oficina: "",
      fechaInicio: "",
    }
  }

  loadSubdecanos(): void {
    this.loading = true
    this.subdecanoService.getAll().subscribe({
      next: (data) => {
        this.subdecanos = data
        this.loading = false
      },
      error: (error) => {
        console.error("Error al cargar subdecanos:", error)
        this.showMessage("Error al cargar subdecanos", "error")
        this.loading = false
      },
    })
  }

  showCreateForm(): void {
    this.showForm = true
    this.editMode = false
    this.currentSubdecano = this.getEmptySubdecano()
    this.editId = null
  }

  showEditForm(subdecano: Subdecano): void {
    this.showForm = true
    this.editMode = true
    this.editId = subdecano.id!
    this.currentSubdecano = {
      nombre: subdecano.nombre,
      apellido: subdecano.apellido,
      correo: subdecano.correo,
      codigoSubdecano: subdecano.codigoSubdecano,
      facultad: subdecano.facultad,
      areaResponsabilidad: subdecano.areaResponsabilidad,
      titulo: subdecano.titulo,
      telefono: subdecano.telefono,
      oficina: subdecano.oficina,
      fechaInicio: subdecano.fechaInicio,
    }
  }

  hideForm(): void {
    this.showForm = false
    this.editMode = false
    this.currentSubdecano = this.getEmptySubdecano()
    this.editId = null
  }

  onSubmit(): void {
    if (!this.isFormValid()) {
      this.showMessage("Por favor complete todos los campos obligatorios", "error")
      return
    }

    this.loading = true

    if (this.editMode && this.editId) {
      this.subdecanoService.update(this.editId, this.currentSubdecano).subscribe({
        next: (response) => {
          this.showMessage("Subdecano actualizado exitosamente", "success")
          this.loadSubdecanos()
          this.hideForm()
          this.loading = false
        },
        error: (error) => {
          console.error("Error al actualizar subdecano:", error)
          this.showMessage("Error al actualizar subdecano", "error")
          this.loading = false
        },
      })
    } else {
      this.subdecanoService.create(this.currentSubdecano).subscribe({
        next: (response) => {
          this.showMessage("Subdecano creado exitosamente", "success")
          this.loadSubdecanos()
          this.hideForm()
          this.loading = false
        },
        error: (error) => {
          console.error("Error al crear subdecano:", error)
          this.showMessage("Error al crear subdecano", "error")
          this.loading = false
        },
      })
    }
  }

  deleteSubdecano(id: number): void {
    if (confirm("¿Está seguro de que desea eliminar este subdecano?")) {
      this.loading = true
      this.subdecanoService.delete(id).subscribe({
        next: (response) => {
          this.showMessage("Subdecano eliminado exitosamente", "success")
          this.loadSubdecanos()
          this.loading = false
        },
        error: (error) => {
          console.error("Error al eliminar subdecano:", error)
          this.showMessage("Error al eliminar subdecano", "error")
          this.loading = false
        },
      })
    }
  }

  isFormValid(): boolean {
    return !!(
      this.currentSubdecano.nombre &&
      this.currentSubdecano.apellido &&
      this.currentSubdecano.correo &&
      this.currentSubdecano.codigoSubdecano &&
      this.currentSubdecano.facultad &&
      this.currentSubdecano.areaResponsabilidad
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
