import { Routes } from "@angular/router"
import { LoginComponent } from "./components/login/login.component"
import { DashboardComponent } from "./components/dashboard/dashboard.component"
import { EstudianteComponent } from "./components/estudiante/estudiante.component"
import { ProfesorComponent } from "./components/profesor/profesor.component"
import { DirectorCarreraComponent } from "./components/director-carrera/director-carrera.component"
import { DecanoComponent } from "./components/decano/decano.component"
import { SubdecanoComponent } from "./components/subdecano/subdecano.component"
import { AuthGuard } from "./guards/auth.guard"
import { AdminGuard } from "./guards/admin.guard"

export const routes: Routes = [
  { path: "", redirectTo: "/login", pathMatch: "full" },
  { path: "login", component: LoginComponent },
  {
    path: "dashboard",
    component: DashboardComponent,
    canActivate: [AuthGuard],
  },
  {
    path: "estudiantes",
    component: EstudianteComponent,
    canActivate: [AuthGuard, AdminGuard],
  },
  {
    path: "profesores",
    component: ProfesorComponent,
    canActivate: [AuthGuard, AdminGuard],
  },
  {
    path: "directores-carrera",
    component: DirectorCarreraComponent,
    canActivate: [AuthGuard, AdminGuard],
  },
  {
    path: "decanos",
    component: DecanoComponent,
    canActivate: [AuthGuard, AdminGuard],
  },
  {
    path: "subdecanos",
    component: SubdecanoComponent,
    canActivate: [AuthGuard, AdminGuard],
  },
  { path: "**", redirectTo: "/login" },
]
