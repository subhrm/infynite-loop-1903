import { Injectable } from '@angular/core';
import { HttpHeaders } from '@angular/common/http';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';
@Injectable({
  providedIn: 'root'
})
export class HttpService {
  URL = 'http://localhost:3000/web/'
  constructor(private http:HttpClient) { }

  getRequest(endPoint, body){ 
      const httpOptions = {
        headers: new HttpHeaders().set('Content-Type','application/json')
      }

    return this.http.post(this.URL+endPoint, body, httpOptions);
  }
}
