import { NgModule } from '@angular/core';
import { RouterModule , Routes } from '@angular/router';
import { LoginComponentComponent} from './login-component/login-component.component';
import { AdminMenuComponent } from './admin-menu/admin-menu.component';
import { SecurityMenuComponent } from './security-menu/security-menu.component';
import { EmployeeMenuComponent } from './employee-menu/employee-menu.component';

const appRoutes: Routes = [
  { path:'admin', component: AdminMenuComponent},
  { path:'security', component: SecurityMenuComponent},
  { path:'', component: LoginComponentComponent},
  { path:'employee', component: EmployeeMenuComponent}
];

@NgModule({
  imports:[
    RouterModule.forRoot(appRoutes)
  ],
  exports:[
    RouterModule
  ],
  declarations: []
})
export class AppRoutingModule { }
