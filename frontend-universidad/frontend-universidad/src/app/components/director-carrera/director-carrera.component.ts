import { Component, type OnInit } from "@angular/core"
import { CommonModule } from "@angular/common"
import { FormsModule } from "@angular/forms"
import { Router } from "@angular/router"
import { DirectorCarreraService } from "../../services/director-carrera.service"
import { DirectorCarrera, DirectorCarreraRequest } from "../../models/director-carrera.model"

@Component({
  selector: "app-director-carrera",
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: "./director-carrera.component.html",
  styleUrls: ["./director-carrera.component.css"],
})
export class DirectorCarreraComponent implements OnInit {
  directores: DirectorCarrera[] = []
  loading = false
  showForm = false
  editMode = false
  currentDirector: DirectorCarreraRequest = this.getEmptyDirector()
  editId: number | null = null
  message = ""
  messageType = ""

  constructor(
    private directorService: DirectorCarreraService,
    private router: Router,
  ) {}

  ngOnInit(): void {
    this.loadDirectores()
  }

  getEmptyDirector(): DirectorCarreraRequest {
    return {
      nombre: "",
      apellido: "",
      correo: "",
      codigoDirector: "",
      carrera: "",
      facultad: "",
      titulo: "",
      telefono: "",
      oficina: "",
      fechaInicio: "",
    }
  }

  loadDirectores(): void {
    this.loading = true
    this.directorService.getAll().subscribe({
      next: (data) => {
        this.directores = data
        this.loading = false
      },
      error: (error) => {
        console.error("Error al cargar directores:", error)
        this.showMessage("Error al cargar directores de carrera", "error")
        this.loading = false
      },
    })
  }

  showCreateForm(): void {
    this.showForm = true
    this.editMode = false
    this.currentDirector = this.getEmptyDirector()
    this.editId = null
  }

  showEditForm(director: DirectorCarrera): void {
    this.showForm = true
    this.editMode = true
    this.editId = director.id!
    this.currentDirector = {
      nombre: director.nombre,
      apellido: director.apellido,
      correo: director.correo,
      codigoDirector: director.codigoDirector,
      carrera: director.carrera,
      facultad: director.facultad,
      titulo: director.titulo,
      telefono: director.telefono,
      oficina: director.oficina,
      fechaInicio: director.fechaInicio,
    }
  }

  hideForm(): void {
    this.showForm = false
    this.editMode = false
    this.currentDirector = this.getEmptyDirector()
    this.editId = null
  }

  onSubmit(): void {
    if (!this.isFormValid()) {
      this.showMessage("Por favor complete todos los campos obligatorios", "error")
      return
    }

    this.loading = true

    if (this.editMode && this.editId) {
      this.directorService.update(this.editId, this.currentDirector).subscribe({
        next: (response) => {
          this.showMessage("Director de carrera actualizado exitosamente", "success")
          this.loadDirectores()
          this.hideForm()
          this.loading = false
        },
        error: (error) => {
          console.error("Error al actualizar director:", error)
          this.showMessage("Error al actualizar director de carrera", "error")
          this.loading = false
        },
      })
    } else {
      this.directorService.create(this.currentDirector).subscribe({
        next: (response) => {
          this.showMessage("Director de carrera creado exitosamente", "success")
          this.loadDirectores()
          this.hideForm()
          this.loading = false
        },
        error: (error) => {
          console.error("Error al crear director:", error)
          this.showMessage("Error al crear director de carrera", "error")
          this.loading = false
        },
      })
    }
  }

  deleteDirector(id: number): void {
    if (confirm("¿Está seguro de que desea eliminar este director de carrera?")) {
      this.loading = true
      this.directorService.delete(id).subscribe({
        next: (response) => {
          this.showMessage("Director de carrera eliminado exitosamente", "success")
          this.loadDirectores()
          this.loading = false
        },
        error: (error) => {
          console.error("Error al eliminar director:", error)
          this.showMessage("Error al eliminar director de carrera", "error")
          this.loading = false
        },
      })
    }
  }

  isFormValid(): boolean {
    return !!(
      this.currentDirector.nombre &&
      this.currentDirector.apellido &&
      this.currentDirector.correo &&
      this.currentDirector.codigoDirector &&
      this.currentDirector.carrera &&
      this.currentDirector.facultad
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
