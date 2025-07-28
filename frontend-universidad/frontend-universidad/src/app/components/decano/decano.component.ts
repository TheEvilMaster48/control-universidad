import { Component, type OnInit } from "@angular/core"
import { CommonModule } from "@angular/common"
import { FormsModule } from "@angular/forms"
import { Router } from "@angular/router"
import { DecanoService } from "../../services/decano.service"
import { Decano, DecanoRequest } from "../../models/decano.model"

@Component({
  selector: "app-decano",
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: "./decano.component.html",
  styleUrls: ["./decano.component.css"],
})
export class DecanoComponent implements OnInit {
  decanos: Decano[] = []
  loading = false
  showForm = false
  editMode = false
  currentDecano: DecanoRequest = this.getEmptyDecano()
  editId: number | null = null
  message = ""
  messageType = ""

  constructor(
    private decanoService: DecanoService,
    private router: Router,
  ) {}

  ngOnInit(): void {
    this.loadDecanos()
  }

  getEmptyDecano(): DecanoRequest {
    return {
      nombre: "",
      apellido: "",
      correo: "",
      codigoDecano: "",
      facultad: "",
      titulo: "",
      telefono: "",
      oficina: "",
      fechaInicio: "",
      experienciaAcademica: "",
    }
  }

  loadDecanos(): void {
    this.loading = true
    this.decanoService.getAll().subscribe({
      next: (data) => {
        this.decanos = data
        this.loading = false
      },
      error: (error) => {
        console.error("Error al cargar decanos:", error)
        this.showMessage("Error al cargar decanos", "error")
        this.loading = false
      },
    })
  }

  showCreateForm(): void {
    this.showForm = true
    this.editMode = false
    this.currentDecano = this.getEmptyDecano()
    this.editId = null
  }

  showEditForm(decano: Decano): void {
    this.showForm = true
    this.editMode = true
    this.editId = decano.id!
    this.currentDecano = {
      nombre: decano.nombre,
      apellido: decano.apellido,
      correo: decano.correo,
      codigoDecano: decano.codigoDecano,
      facultad: decano.facultad,
      titulo: decano.titulo,
      telefono: decano.telefono,
      oficina: decano.oficina,
      fechaInicio: decano.fechaInicio,
      experienciaAcademica: decano.experienciaAcademica,
    }
  }

  hideForm(): void {
    this.showForm = false
    this.editMode = false
    this.currentDecano = this.getEmptyDecano()
    this.editId = null
  }

  onSubmit(): void {
    if (!this.isFormValid()) {
      this.showMessage("Por favor complete todos los campos obligatorios", "error")
      return
    }

    this.loading = true

    if (this.editMode && this.editId) {
      this.decanoService.update(this.editId, this.currentDecano).subscribe({
        next: (response) => {
          this.showMessage("Decano actualizado exitosamente", "success")
          this.loadDecanos()
          this.hideForm()
          this.loading = false
        },
        error: (error) => {
          console.error("Error al actualizar decano:", error)
          this.showMessage("Error al actualizar decano", "error")
          this.loading = false
        },
      })
    } else {
      this.decanoService.create(this.currentDecano).subscribe({
        next: (response) => {
          this.showMessage("Decano creado exitosamente", "success")
          this.loadDecanos()
          this.hideForm()
          this.loading = false
        },
        error: (error) => {
          console.error("Error al crear decano:", error)
          this.showMessage("Error al crear decano", "error")
          this.loading = false
        },
      })
    }
  }

  deleteDecano(id: number): void {
    if (confirm("¿Está seguro de que desea eliminar este decano?")) {
      this.loading = true
      this.decanoService.delete(id).subscribe({
        next: (response) => {
          this.showMessage("Decano eliminado exitosamente", "success")
          this.loadDecanos()
          this.loading = false
        },
        error: (error) => {
          console.error("Error al eliminar decano:", error)
          this.showMessage("Error al eliminar decano", "error")
          this.loading = false
        },
      })
    }
  }

  isFormValid(): boolean {
    return !!(
      this.currentDecano.nombre &&
      this.currentDecano.apellido &&
      this.currentDecano.correo &&
      this.currentDecano.codigoDecano &&
      this.currentDecano.facultad
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
