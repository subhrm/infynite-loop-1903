// This file can be replaced during build by using the `fileReplacements` array.
// `ng build --prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

export const environment = {
  production: false,
  apiURL:{
    login: 'http://localhost:3000/login',
    visitor: 'http://localhost:3000/web/getVisitorType',
    imageValidation:'http://35.207.12.149:8000/api/upload-photo-b64',
    addVisitorEmployee: 'http://localhost:3000/web/addVisitorEmployee',
    employeeDetails:'',
    addVisitorSecurity:'http://localhost:3000/web/addVisitorSecurity',
    approvedVisitors:''
  }
};

/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/dist/zone-error';  // Included with Angular CLI.
