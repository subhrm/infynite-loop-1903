import { Component, OnInit } from '@angular/core';
import { DomSanitizer} from '@angular/platform-browser';
import { EmployeeService } from '../../employee-menu/employee.service';
import { SecurityService } from '../security.service';

@Component({
  selector: 'app-guest-access',
  templateUrl: './guest-access.component.html',
  styleUrls: ['./guest-access.component.css']
})
export class GuestAccessComponent implements OnInit {
  statusMessage: string;
  visitorType:any[];
  visitorTypes = [
    {
      code: 'B1GUEST',
      value: 'Can Visit Only Building 1'
    },{
      code: 'B2GUEST',
      value: 'Can Visist Only Building 2'
    },{
      code: 'B3GUEST',
      value: 'Can Visist Only Building 3'
    },{
      code: 'EMPLOYEE',
      value: 'Employee, Can visit all buildings'
    },{
      code: 'GEN',
      value: 'General Visitor.Restricted Access'
    }

  ]
  name:string;
  email:string;
  mobile:string;
  referredBy:string;
  inTime: Date;
  outTime: Date;
  photoID:string;
  selectedVisitorType:string;
  photo: any;
  imageSource: any;

  constructor(private empService:EmployeeService,private secService:SecurityService, private _sanitizer: DomSanitizer) { }

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
          self.photo = response['image_id'];
        },(httpError)=>{
          console.log(httpError.error);
          alert(httpError.error.error);
          
        });
    }
  }

  submitVisitorDetails(){
    let in_time = new Date(this.inTime).toISOString();
    in_time = in_time.replace('Z', '');
    in_time = in_time.replace('T', ' ');
    let out_time = new Date(this.outTime).toISOString();
    out_time = in_time.replace('Z', '');
    out_time = in_time.replace('T', ' ');
    let visitorPayload = {
      "Name":this.name,
      "Email":this.email,
      "Photo":this.photo,
      "Mobile":this.mobile,
      "VisitorType":this.selectedVisitorType,
      "Reffered":this.referredBy,
      "IN": in_time,
      "OUT":this.outTime
    };
    
    this.secService.requestGuestAccess(visitorPayload)
    .subscribe(response =>{
      this.imageSource = this._sanitizer.bypassSecurityTrustResourceUrl('data:image/jpg;base64,' 
      + response['data']['Photo']);
      console.log(response);
    });
  }

}
