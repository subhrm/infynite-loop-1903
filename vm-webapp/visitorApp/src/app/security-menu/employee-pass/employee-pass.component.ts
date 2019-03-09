import { Component, OnInit } from '@angular/core';
import { SecurityService } from '../security.service';

@Component({
  selector: 'app-employee-pass',
  templateUrl: './employee-pass.component.html',
  styleUrls: ['./employee-pass.component.css']
})
export class EmployeePassComponent implements OnInit {

  constructor(private secService:SecurityService) { }

  employeeData:boolean = false;
  id:string;
  name:string;
  email:string;
  mobile:string;

  ngOnInit() {
  }

  getEmployeeDetails(){
    let self= this;
    let employeePayload = {
      "Id":this.id
    };

    this.secService.fetchEmployeeDetails(employeePayload)
    .subscribe((response)=>{
        console.log(response);
        if (response.data.length>0){
          self.employeeData = true;
          self.name = response.data[0].name;
          self.mobile = response.data[0].mobile;
          self.email = response.data[0].email;
        }else{
          self.employeeData = false;
        }
    });
  }

}
