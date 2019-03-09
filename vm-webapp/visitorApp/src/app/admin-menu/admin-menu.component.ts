import { Component, OnInit } from '@angular/core';
import { HttpService } from '../service/http.service';
import { SecurityService } from '../security-menu/security.service';
import { isNullOrUndefined } from 'util';

@Component({
  selector: 'app-admin-menu',
  templateUrl: './admin-menu.component.html',
  styleUrls: ['./admin-menu.component.css']
})
export class AdminMenuComponent implements OnInit {
  statusResponse = undefined;
  showSpinnerFlag = false;
  visitor = {
    name: '',
    email: '',
    phone: '',
    type: '',
    photo: '',
    referredBy: '',
    inDate: new Date(),
    outDate: new Date()
  }
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

  constructor(private _http: SecurityService) { }

  ngOnInit() {
  }

  links = ['Security Management', 'Visitor Management', 'Location Management'];
  activeLink = this.links[0];
  background = '';

  toggleBackground() {
    this.background = this.background ? '' : 'primary';
  }
  submitVisitorData() {
    this.showSpinnerFlag = true;
    let inTime = new Date(this.visitor.inDate).toISOString();
    inTime = inTime.replace('Z','');
    inTime = inTime.replace('T',' ');

    let outTime = new Date(this.visitor.outDate).toISOString();
    outTime = outTime.replace('Z','');
    outTime = outTime.replace('T',' ');
    let photo = '1';
    if(this.visitor.photo!= ''){
      photo = this.visitor.photo;
    }
    const body = {
      "Name": this.visitor.name,
      "Email":this.visitor.email,
      "Photo": photo,
      "Mobile":this.visitor.phone,
      "VisitorType":this.visitor.type,
      "Reffered":this.visitor.referredBy,
      "IN": inTime,
      "OUT": outTime,
    };
    this._http.requestGuestAccess(body)
    .subscribe(response =>{
      this.showSpinnerFlag = false;
    this.statusResponse = response;
    setTimeout(() => {
      this.statusResponse = undefined;
    }, 4000);
    });
    console.log("Visitor data: ",this.visitor);


  }

}
