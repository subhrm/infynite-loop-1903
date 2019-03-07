var express = require('express');
var path = require('path');
var logger = require('morgan');
var cookieParser = require('cookie-parser');
var bodyParser = require('body-parser');
var cors=require('cors');
// var routes = require('./routes/index');
// var users = require('./routes/users');
// var Tasks=require('./routes/Tasks');
var app = express();
var mysql = require('mysql');
var helmet = require('helmet');
const morgan = require('morgan');
const jwt = require('jsonwebtoken');
const config = require('./configurations/config');
require('dotenv').config();
var router = express.Router();

const  ProtectedRoutes = express.Router(); 

app.use('/api', ProtectedRoutes);


// var con = mysql.createConnection({
//     host: process.env.DB_HOST,
//     port: process.env.DB_PORT,
//     user: process.env.DB_USER,
//     password: process.env.DB_PASS
// });
app.set('Secret', config.secret);

app.use(helmet());
app.use(cors());
app.use(logger('dev'));
app.use(bodyParser.json({limit: '50mb'}));
app.use(bodyParser.urlencoded({limit: '50mb', extended: false }));
app.use(cookieParser());
app.use(express.static(path.join(__dirname, 'public')));

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


app.get('/test', function (req, res, next) {
  // res.render('index', { title: 'Express' });
  res.send("working")
});

app.post('/authenticate', (req, res) => {
  if (req.body.username === "aymen") {

    if (req.body.password === 123) {
      //if eveything is okey let's create our token 

      const payload = {
        check: true
      };

      var token = jwt.sign(payload, app.get('Secret'), {
        expiresIn: 28800 // expires in 8 hours
      });
      res.json({
        message: 'authentication done ',
        token: token
      });
    } else {
      res.json({
        message: "please check your password !"
      })
    }
  } else {
    res.json({
      message: "user not found !"
    })
  }
})


ProtectedRoutes.use((req, res, next) =>{


  // check header for the token
  var token = req.headers['access-token'];

  // decode token
  if (token) {

    // verifies secret and checks if the token is expired
    jwt.verify(token, app.get('Secret'), (err, decoded) =>{      
      if (err) {
        res.statusCode = 401;
        return res.json({ message: 'invalid token' });    
      } else {
        // if everything is good, save to request for use in other routes
        req.decoded = decoded;    
        next();
      }
    });

  } else {

    // if there is no token  
    res.statusCode = 401
    res.send({ 

        message: 'No token provided.' 
    });

  }
});

ProtectedRoutes.get('/getAllProducts',(req,res)=>{
  res.statusCode = 200;
  let products = [
      {
          id: 1,
          name:"cheese"
      },
      {
         id: 2,
         name:"carottes"
     }
  ]
  res.json(products)
});




app.listen(process.env.PORT || 3000, () => {
  console.log('App listening on port 3000');
});

module.exports = app;
