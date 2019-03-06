var express = require('express');
var path = require('path');
var logger = require('morgan');
var cookieParser = require('cookie-parser');
var bodyParser = require('body-parser');
var cors=require('cors');
var routes = require('./routes/index');
var users = require('./routes/users');
var Tasks=require('./routes/Tasks');
var app = express();
var mysql = require('mysql');
require('dotenv').config()

var con = mysql.createConnection({
    host: process.env.DB_HOST,
    port: process.env.DB_PORT,
    user: process.env.DB_USER,
    password: process.env.DB_PASS
});

app.use(cors());
app.use(logger('dev'));
app.use(bodyParser.json({limit: '50mb'}));
app.use(bodyParser.urlencoded({limit: '50mb', extended: false }));

app.use(cookieParser());
app.use(express.static(path.join(__dirname, 'public')));
//app.use(express.static('/uploads/'));

/*app.use('/resources',express.static(__dirname + '/images'));
So now, you can use http://localhost:5000/resources/myImage.jpg to serve all the images instead of http://localhost:5000/images/myImage.jpg. */
app.use('/', routes);
app.use('/users', users);
app.use('/Tasks',Tasks);
// catch 404 and forward to error handler
app.use(function(req, res, next) {
  var err = new Error('Not Found');
  err.status = 404;
  next(err);
});

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

app.listen(3000, () => {
  console.log('App listening on port 3000');
});

module.exports = app;
