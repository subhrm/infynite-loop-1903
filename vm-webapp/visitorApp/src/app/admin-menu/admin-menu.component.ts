import { Component, OnInit } from '@angular/core';
import { HttpService } from '../service/http.service';

@Component({
  selector: 'app-admin-menu',
  templateUrl: './admin-menu.component.html',
  styleUrls: ['./admin-menu.component.css']
})
export class AdminMenuComponent implements OnInit {
  statusMessage = '';
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
      code: "c1",
      value: "val 1"
    },{
      code: "c2",
      value: "val 2"
    },{
      code: "c3",
      value: "val 3"
    },{
      code: "c4",
      value: "val 4"
    }
  ]
  constructor(private _http: HttpService) { }

  ngOnInit() {
  }

  links = ['Security Management', 'Visitor Management', 'Location Management'];
  activeLink = this.links[0];
  background = '';

  toggleBackground() {
    this.background = this.background ? '' : 'primary';
  }
  submitVisitorData() {
    const body = {

    };
    // this._http.getRequest('submitNewUser', body).subscribe(data=>{

    // });
    this.statusMessage = 'msg';
    console.log("Visitor data: ",this.visitor);
    setTimeout(() => {
      this.statusMessage = '';
    }, 3000);
  }

}
