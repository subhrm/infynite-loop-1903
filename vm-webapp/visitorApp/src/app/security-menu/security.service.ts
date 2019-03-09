import { Injectable } from '@angular/core';
import { HttpHeaders } from '@angular/common/http';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class SecurityService {

  constructor(private http:HttpClient) { }

  authToken:string = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJjaGVjayI6dHJ1ZSwiaWF0IjoxNTUyMTMxODU4LCJleHAiOjE1NTIyMTgyNTh9.icvkokZZAt3ztyhelb5YWeC4KHP3t5ohadZd3nldYjc";

  fetchEmployeeDetails(empPayload){
    const httpOptions = {
      headers: new HttpHeaders().set('Content-Type','application/json')
                                .set('access-token',localStorage.getItem("token"))
    }

    return this.http.post(environment.apiURL.employeeDetails,empPayload,httpOptions);
  }

  requestGuestAccess(visitorPayload){
    const httpOptions = {
      headers: new HttpHeaders().set('Content-Type','application/json')
                                .set('access-token',localStorage.getItem("token"))
    }

    return this.http.post(environment.apiURL.addVisitorSecurity,visitorPayload,httpOptions);
  }

  fetchApprovedVisitors(){
    const httpOptions = {
      headers: new HttpHeaders().set('Content-Type','application/json')
                                .set('access-token',localStorage.getItem("token"))
    }

    return this.http.get(environment.apiURL.approvedVisitors,httpOptions);
  }


}
