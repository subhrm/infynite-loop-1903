import { Component, OnInit } from '@angular/core';
import { EmployeeService } from './employee.service';

@Component({
  selector: 'app-employee-menu',
  templateUrl: './employee-menu.component.html',
  styleUrls: ['./employee-menu.component.css']
})
export class EmployeeMenuComponent implements OnInit {

  visitorType:any[];

  constructor(private empService:EmployeeService) { }

  ngOnInit() {
    let self= this;
    this.empService.getVisitorDetails()
    .subscribe((visitorData)=>{
      console.log(visitorData["data"]);
      self.visitorType = visitorData["data"];
    });
  }

}
