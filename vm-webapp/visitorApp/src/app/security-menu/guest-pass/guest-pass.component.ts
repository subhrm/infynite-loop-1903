import { Component, OnInit } from '@angular/core';
import { SecurityService } from '../security.service';

export interface PeriodicElement {
  name: string;
  position: number;
  weight: number;
  symbol: string;
}

// const ELEMENT_DATA: PeriodicElement[] = [
//   {position: 1, name: 'Hydrogen', weight: 1.0079, symbol: 'H'},
//   {position: 2, name: 'Helium', weight: 4.0026, symbol: 'He'},
//   {position: 3, name: 'Lithium', weight: 6.941, symbol: 'Li'},
//   {position: 4, name: 'Beryllium', weight: 9.0122, symbol: 'Be'},
//   {position: 5, name: 'Boron', weight: 10.811, symbol: 'B'},
//   {position: 6, name: 'Carbon', weight: 12.0107, symbol: 'C'},
//   {position: 7, name: 'Nitrogen', weight: 14.0067, symbol: 'N'},
//   {position: 8, name: 'Oxygen', weight: 15.9994, symbol: 'O'},
//   {position: 9, name: 'Fluorine', weight: 18.9984, symbol: 'F'},
//   {position: 10, name: 'Neon', weight: 20.1797, symbol: 'Ne'},
// ];

@Component({
  selector: 'app-guest-pass',
  templateUrl: './guest-pass.component.html',
  styleUrls: ['./guest-pass.component.css']
})


export class GuestPassComponent implements OnInit {

  constructor(private secService:SecurityService) { }

  displayedColumns: string[]; //= ['position', 'name', 'weight', 'symbol'];
  dataSource; //= ELEMENT_DATA;
  
  ngOnInit() {
  }

  fetchApprovedGuestDetails(){
    console.log("In function");
    this.secService.fetchApprovedVisitors()
    .subscribe((response)=>{
        console.log(response);
        this.displayedColumns = Object.keys(response["data"][0]);
        this.dataSource = response["data"];

        console.log(this.displayedColumns);
        console.log(this.dataSource);
    });
  }

}
