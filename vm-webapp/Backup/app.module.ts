import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { MaterialModule } from './material.module';
import { HttpClientModule } from '@angular/common/http';

import { AppComponent } from './app.component';
import { LoginComponentComponent } from './login-component/login-component.component';
import { SecurityMenuComponent } from './security-menu/security-menu.component';
import { AdminMenuComponent } from './admin-menu/admin-menu.component';
import { GuestAccessComponent } from './security-menu/guest-access/guest-access.component';
import { GuestPassComponent} from './security-menu/guest-pass/guest-pass.component';
import { EmployeePassComponent } from './security-menu/employee-pass/employee-pass.component';
import { GuestPendingComponent} from './security-menu/guest-pending/guest-pending.component';
import { EmployeeMenuComponent } from './employee-menu/employee-menu.component';


@NgModule({
  declarations: [
    AppComponent,
    LoginComponentComponent,
    SecurityMenuComponent,
    AdminMenuComponent,
    GuestAccessComponent,
    GuestPassComponent,
    GuestPendingComponent,
    EmployeePassComponent,
    EmployeeMenuComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    FormsModule,
    MaterialModule,
    HttpClientModule
  ],
  providers: [ ],
  bootstrap: [AppComponent]
})
export class AppModule { }
