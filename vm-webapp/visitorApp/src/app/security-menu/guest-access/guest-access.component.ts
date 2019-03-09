import { Component, OnInit } from '@angular/core';
import { EmployeeService } from '../../employee-menu/employee.service';
import { SecurityService } from '../security.service';

@Component({
  selector: 'app-guest-access',
  templateUrl: './guest-access.component.html',
  styleUrls: ['./guest-access.component.css']
})
export class GuestAccessComponent implements OnInit {

  visitorType:any[];
  name:string;
  email:string;
  mobile:string;
  referredBy:string;
  inTime:string;
  outTime:string;
  photoID:string;
  selectedVisitorType:string;

  constructor(private empService:EmployeeService,private secService:SecurityService) { }

  ngOnInit() {
    let self = this;
    this.empService.getVisitorDetails()
    .subscribe((visitorData)=>{
      console.log(visitorData["data"]);
      self.visitorType = visitorData["data"];
    });
  }

  validateFile(event){
    let self = this;
    let file = (<HTMLInputElement>document.getElementById('visitorImage')).files[0];
    console.log(file);

    let fileReader = new FileReader();
    fileReader.readAsDataURL(file);

    fileReader.onload= function(e){
      let fileText = fileReader.result;
      let fileBase64 = fileText.split(",")[1];
      console.log(fileBase64);

      let filePayload = {
        "image":fileBase64
      }

      self.empService.validateImageBase64(filePayload)
      .subscribe((response)=>{
          console.log(response);
        },(httpError)=>{
          console.log(httpError.error);
          alert(httpError.error.error);
          
        });
    }
  }

  submitVisitorDetails(){

    let visitorPayload = {
      "Name":this.name,
      "Email":this.email,
      "Photo":this.photoID,
      "Mobile":this.mobile,
      "VisitorType":this.selectedVisitorType,
      "Reffered":this.referredBy,
      "IN":this.inTime,
      "OUT":this.outTime
    };
    
    this.secService.requestGuestAccess(visitorPayload)
    .subscribe(response =>{
      console.log(response);
    });
  }

}
