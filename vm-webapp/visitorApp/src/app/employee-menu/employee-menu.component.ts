import { Component, OnInit } from '@angular/core';
import { EmployeeService } from './employee.service';
import { DomSanitizer} from '@angular/platform-browser';
import { SecurityService } from '../security-menu/security.service';

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
  inTime: Date;
  outTime: Date;
  photoID:string;
  selectedVisitorType:string;
  imageSource: any;
  showSpinner:boolean = false;


  constructor(private empService:EmployeeService,private secService:SecurityService,private _sanitizer: DomSanitizer) { }

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
    self.showSpinner = true;
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
          self.photoID = response["image_id"];
          self.showSpinner = false;
          alert("Image Validation successful");

        },(httpError)=>{
          console.log(httpError.error);
          self.showSpinner = false;
          alert(httpError.error.error);
        });
    }
  }

  submitRequest(){
    let in_time = new Date(this.inTime).toISOString();
    in_time = in_time.replace('Z', '');
    in_time = in_time.replace('T', ' ');
    let out_time = new Date(this.outTime).toISOString();
    out_time = in_time.replace('Z', '');
    out_time = in_time.replace('T', ' ');
    let visitorPayload = {
      "Name":this.name,
      "Email":this.email,
      "Photo":this.photoID,
      "Mobile":this.mobile,
      "VisitorType":this.selectedVisitorType,
      "Reffered":this.referredBy,
      "IN": in_time,
      "OUT":out_time
    };
    
    this.empService.requestVisitorAccess(visitorPayload)
    .subscribe(response =>{
      this.imageSource = this._sanitizer.bypassSecurityTrustResourceUrl('data:image/jpg;base64,' 
      + response['data']['Photo']);
      console.log(response);
    });
  }

}
