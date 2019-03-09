import { Component, OnInit } from '@angular/core';
import { EmployeeService } from './employee.service';

@Component({
  selector: 'app-employee-menu',
  templateUrl: './employee-menu.component.html',
  styleUrls: ['./employee-menu.component.css']
})
export class EmployeeMenuComponent implements OnInit {

  visitorType:any[];
  name:string;
  email:string;
  mobile:string;
  referredBy:string;
  inTime:string;
  outTime:string;
  photoID:string;

  constructor(private empService:EmployeeService) { }

  ngOnInit() {
    let self= this;
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
          self.photoID = response["image_id"]
        },(httpError)=>{
          console.log(httpError.error);
          alert(httpError.error.error);
          
        });
    }
  }

  submitRequest(){
    let visitorPayload = {
      "Name":this.name,
      "Email":this.email,
      "Photo":this.photoID,
      "Mobile":this.mobile,
      "Visitor Type":"",
      "Reffered":this.referredBy,
      "IN":this.inTime,
      "OUT":this.outTime
    };

    this.empService.requestVisitorAccess(visitorPayload)
    .subscribe((response) =>{
        console.log(response);
    });
  }

}
