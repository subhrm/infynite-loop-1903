import { Component, OnInit } from '@angular/core';
import { SecurityService } from '../security.service';
import { DomSanitizer, SafeResourceUrl} from '@angular/platform-browser';

@Component({
  selector: 'app-employee-pass',
  templateUrl: './employee-pass.component.html',
  styleUrls: ['./employee-pass.component.css']
})
export class EmployeePassComponent implements OnInit {

  constructor(private secService:SecurityService,private _sanitizier:DomSanitizer) { }

  employeeData:boolean = false;
  errorDiv:boolean = false;
  id:string;
  name:string;
  email:string;
  mobile:string;
  image_data:SafeResourceUrl;

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
        if (response["data"].length>0){
          self.employeeData = true;
          self.name = response["data"][0].name;
          self.mobile = response["data"][0].mobile;
          self.email = response["data"][0].email;
          self.image_data = self._sanitizier.bypassSecurityTrustResourceUrl('data:image/jpg;base64,'+ response["data"][0].image_data);
        }else{
          self.employeeData = false;
          self.errorDiv = true;
        }
    });
  }

}
