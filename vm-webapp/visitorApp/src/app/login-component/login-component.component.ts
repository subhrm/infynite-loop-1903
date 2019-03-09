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
  errorDiv:boolean;
  showSpinner:boolean = false;

  constructor(private routerObj:Router,private loginService:LoginService) { 
  }

  ngOnInit() {
  }

  login(){
    let self = this;
    self.showSpinner = true;
    let loginArgs = {
      "email":this.email,
      "password":this.password
    };

    this.loginService.loginToApp(loginArgs)
    .subscribe((responseData) =>{
        self.showSpinner = false;
        console.log(responseData["data"]);
        let loginData = responseData["data"];
        
        if (responseData["status"] === 1){
            //Updating the auth token.
            environment.authToken = loginData.token;

            console.log(environment);

            switch(loginData.userRole){
              case "SEC_ADM":{
                self.routerObj.navigateByUrl('security');
                break;
              }
              case "EMPLOYEE":{
                self.routerObj.navigateByUrl('employee');
                break;
              }
              case "HRD":{
                self.routerObj.navigateByUrl('admin');
                break;
              }
            }
            self.errorDiv = false;
        }else if(responseData["status"] === 0){
            self.routerObj.navigateByUrl('');
            self.errorDiv = true;
        }
        
    });
  }

}
