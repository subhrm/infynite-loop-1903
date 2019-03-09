import { Injectable } from '@angular/core';
import { HttpHeaders } from '@angular/common/http';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class SecurityService {

  constructor(private http:HttpClient) { }

  authToken:string = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJjaGVjayI6dHJ1ZSwiaWF0IjoxNTUyMDQ5NTk5LCJleHAiOjE1NTIwNzgzOTl9.qoso1U3IPN39hFfJUH7fIoD6LYpUA5I8bSwjoo3xGp0";

  fetchEmployeeDetails(){
    const httpOptions = {
      headers: new HttpHeaders().set('Content-Type','application/json')
                                .set('access-token',this.authToken)
    }

    return this.http.get(environment.apiURL.employeeDetails,httpOptions);
  }

  requestGuestAccess(visitorPayload){
    const httpOptions = {
      headers: new HttpHeaders().set('Content-Type','application/json')
                                .set('access-token',this.authToken)
    }

    return this.http.post(environment.apiURL.addVisitorSecurity,visitorPayload,httpOptions);
  }
}
