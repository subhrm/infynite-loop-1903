import { Injectable } from '@angular/core';
import { HttpHeaders } from '@angular/common/http';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class SecurityService {

  constructor(private http:HttpClient) { }

  authToken:string = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJjaGVjayI6dHJ1ZSwiaWF0IjoxNTUyMTAyODMzLCJleHAiOjE1NTIxMzE2MzN9.8SWENZClaXtWXDFsV5d2r05M1HNhboFSTLLkHC0wmR8";

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
