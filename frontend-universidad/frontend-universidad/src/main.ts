import { bootstrapApplication } from "@angular/platform-browser"
import { provideRouter } from "@angular/router"
import { provideHttpClient, withInterceptorsFromDi } from "@angular/common/http"
import { importProvidersFrom } from "@angular/core"
import { FormsModule, ReactiveFormsModule } from "@angular/forms"

import { AppComponent } from "./app/app.component"
import { routes } from "./app/app.routes"

bootstrapApplication(AppComponent, {
  providers: [
    provideRouter(routes),
    provideHttpClient(withInterceptorsFromDi()),
    importProvidersFrom(FormsModule, ReactiveFormsModule),
  ],
}).catch((err) => console.error(err))

// cd "C:\Users\Estku\Downloads\frontend-universidad\frontend-universidad"
// ng serve