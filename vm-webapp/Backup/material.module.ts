import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatButtonModule, MatCardModule,MatSelectModule,MatStepperModule, MatTabsModule,MatDialogModule, MatInputModule, MatTableModule,MatToolbarModule, MatMenuModule,MatIconModule, MatProgressSpinnerModule} from '@angular/material';
@NgModule({
  imports: [
    CommonModule,
    MatButtonModule, 
    MatCardModule, 
    MatDialogModule, 
    MatInputModule, 
    MatTableModule,
    MatTabsModule,
    MatToolbarModule,
    MatMenuModule,
    MatIconModule, 
    MatSelectModule,
    MatStepperModule,
    MatProgressSpinnerModule
  ],
  exports:[
    CommonModule,
    MatButtonModule, 
    MatCardModule, 
    MatDialogModule, 
    MatInputModule, 
    MatTableModule,
    MatTabsModule,
    MatToolbarModule,
    MatMenuModule,
    MatIconModule, 
    MatSelectModule,
    MatStepperModule,
    MatProgressSpinnerModule
  ],
  declarations: []
})
export class MaterialModule { }
