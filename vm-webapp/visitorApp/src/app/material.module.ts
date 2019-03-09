import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatButtonModule, MatCardModule,MatSelectModule, MatTabsModule,MatDialogModule, 
  MatInputModule, MatTableModule,MatToolbarModule, MatMenuModule,MatIconModule,
  MatAutocompleteModule, MatProgressSpinnerModule} from '@angular/material';

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
    MatProgressSpinnerModule,
    MatAutocompleteModule
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
    MatProgressSpinnerModule,
    MatAutocompleteModule
  ],
  declarations: []
})
export class MaterialModule { }
