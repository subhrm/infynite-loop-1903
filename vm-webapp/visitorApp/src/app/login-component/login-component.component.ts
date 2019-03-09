import { Component, OnInit } from '@angular/core';
import { Router} from '@angular/router';
import { LoginService } from './login.service';
import { environment } from '../../environments/environment';

@Component({
  selector: 'app-login-component',
  templateUrl: './login-component.component.html',
  styleUrls: ['./login-component.component.css']
})

export class LoginComponentComponent implements OnInit {

  role:string;
  email:string;
  password:string;

  constructor(private routerObj:Router,private loginService:LoginService) { 
  }

  ngOnInit() {
  }

  login(){
    let self = this;
    
    let loginArgs = {
      "email":this.email,
      "password":this.password
    };

    this.loginService.loginToApp(loginArgs)
    .subscribe((responseData) =>{
        console.log(responseData["data"]);
        let loginData = responseData["data"];
        
        if (loginData.status === 1){
            //Updating the auth token.
            environment.authToken = loginData.token;

            switch(loginData.userRole){
              case "SEC_ADM":{
                self.routerObj.navigateByUrl('security');
              }
              case "EMPLOYEE":{
                self.routerObj.navigateByUrl('employee');
              }
              case "HRD":{
                self.routerObj.navigateByUrl('admin');
              }
            }
        }else if(loginData.status === 0){
            self.routerObj.navigateByUrl('login');
        }
        
    });
  }

}
