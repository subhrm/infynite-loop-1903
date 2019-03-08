var express = require('express');
var router = express.Router();
jwt = require('jsonwebtoken');
const db = require('../db');
const crypto = require('crypto');

// const config = require('../configurations/config');
// app.set('Secret', config.secret);

/* GET home page. */
router.get('/', function (req, res, next) {
  // res.render('index', { title: 'Express' });
  res.send("working")
});


router.post('/login', (req,res,next) => {
  let email = req.body.email;
  let pass = crypto.createHash('sha256').update(req.body.password).digest('hex');
  let requestedFrom = req.body.requestedFrom;
  db.login(req, res, email, pass, requestedFrom);
})


router.use(function (req, res, next) {
  // check header for the token
  var token = req.headers['access-token'];
  // decode token
  if (token) {
    // verifies secret and checks if the token is expired
    jwt.verify(token, req.app.get('Secret'), (err, decoded) =>{      
      if (err) {
        res.statusCode = 401;
        return res.send({
          status: req.app.get('status-code').unauthorized, 
          message: 'invalid token' 
        });    
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
        status: req.app.get('status-code').unauthorized,
        message: 'No token provided.' 
    });
  }
});

router.use("/mobile", require("./mobile"));
router.use("/web", require("./web"));


module.exports = router;