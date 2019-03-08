var express = require('express');
var path = require('path');
var logger = require('morgan');
var cookieParser = require('cookie-parser');
var bodyParser = require('body-parser');
var cors=require('cors');
// var routes = require('./routes/index');
// var users = require('./routes/users');
// var Tasks=require('./routes/Tasks');
require('dotenv').config();
var app = express();
var helmet = require('helmet');
const morgan = require('morgan');
const jwt = require('jsonwebtoken');
const config = require('./configurations/config');
var router = express.Router();

const  ProtectedRoutes = express.Router(); 




app.set('Secret', config.secret);
app.set('status-code', {
  "success": 1,
  "unauthorized": 0,
  "error": -1
});

app.use(helmet());
app.use(cors());
app.use(logger('dev'));
app.use(bodyParser.json({limit: '50mb'}));
app.use(bodyParser.urlencoded({limit: '50mb', extended: false }));
app.use(cookieParser());
app.use(express.static(path.join(__dirname, 'public')));

app.use(require('./controllers'));

// app.use('/api', ProtectedRoutes);


// app.use(function(req, res, next) {
//   var err = new Error('Not Found');
//   err.status = 404;
//   next(err);
// });

// error handlers

// development error handler
// will print stacktrace
// if (app.get('env') === 'development') {
//   app.use(function(err, req, res, next) {
//     res.status(err.status || 500);
//     res.render('error', {
//       message: err.message,
//       error: err
//     });
//   });
// }

// // production error handler
// // no stacktraces leaked to user
// app.use(function(err, req, res, next) {
//   res.status(err.status || 500);
//   // res.render('error', {
//   //   message: err.message,
//   //   error: {}
//   // });
// });


// app.get('/test', function (req, res, next) {
//   // res.render('index', { title: 'Express' });
//   res.send("working")
// });

// app.post('/authenticate', (req, res) => {
//   if (req.body.username === "aymen") {

//     if (req.body.password === 123) {
//       //if eveything is okey let's create our token 

//       const payload = {
//         check: true
//       };

//       var token = jwt.sign(payload, app.get('Secret'), {
//         expiresIn: 28800 // expires in 8 hours
//       });
//       res.json({
//         message: 'authentication done ',
//         token: token
//       });
//     } else {
//       res.json({
//         message: "please check your password !"
//       })
//     }
//   } else {
//     res.json({
//       message: "user not found !"
//     })
//   }
// })


// ProtectedRoutes.use((req, res, next) =>{

//   // check header for the token
//   var token = req.headers['access-token'];
//   // decode token
//   if (token) {
//     // verifies secret and checks if the token is expired
//     jwt.verify(token, app.get('Secret'), (err, decoded) =>{      
//       if (err) {
//         res.statusCode = 401;
//         return res.send({ status: 0, message: 'invalid token' });    
//       } else {
//         // if everything is good, save to request for use in other routes
//         req.decoded = decoded;    
//         next();
//       }
//     });

//   } else {
//     // if there is no token  
//     res.statusCode = 401
//     res.send({ 
//         status: 0,
//         message: 'No token provided.' 
//     });
//   }
// });

// ProtectedRoutes.get('/getAllProducts',(req,res)=>{
//   res.statusCode = 200;
//   let products = [
//       {
//           id: 1,
//           name:"cheese"
//       },
//       {
//          id: 2,
//          name:"carottes"
//      }
//   ]
//   res.json(products);
// });

// /* Bar code verification */
// ProtectedRoutes.get('/verify', (req,res) => {
//   // const encryptedVisitorId = req.body.visitorId;
//   // const securityId = req.body.securityId;
//   res.send({
//     status: 1,
//     message: "test",
//     data: [{test: "test2"}]
//   })
// })



app.listen(process.env.PORT || 3000, () => {
  console.log('App listening on port 3000');
});

module.exports = app;
