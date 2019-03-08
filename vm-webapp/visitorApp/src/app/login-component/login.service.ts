import { Injectable } from '@angular/core';
import { HttpHeaders } from '@angular/common/http';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';
@Injectable({
  providedIn: 'root'
})
export class LoginService {

  constructor(private http:HttpClient) { }

  loginToApp(args){ 
      const httpOptions = {
        headers: new HttpHeaders().set('Content-Type','application/json')
      }

    return this.http.post(environment.apiURL.login,args,httpOptions);
  }
}
