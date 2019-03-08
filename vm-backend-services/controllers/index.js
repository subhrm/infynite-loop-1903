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
  // if (req.body.email === "aymen") {

  //     //if eveything is okey let's create our token 

  //     const payload = {
  //       check: true
  //     };

  //     var token = jwt.sign(payload, req.app.get('Secret'), {
  //       expiresIn: 28800 // expires in 8 hours
  //     });
  //     res.send({
  //       status: 1,
  //       message: 'authentication successful',
  //       token: token
  //     });
  // } else {
  //   res.send({
  //     status: 0,
  //     message: 'Invalid Credentials'
  //   }); 
  // }
})

// router.post('/authenticate', (req, res) => {
//   if (req.body.username === "aymen") {

//     if (req.body.password === 123) {
//       //if eveything is okey let's create our token 

//       const payload = {
//         check: true
//       };

//       var token = jwt.sign(payload, app.get('Secret'), {
//         expiresIn: 1440 // expires in 24 hours
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
          status: 0, 
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
        status: 0,
        message: 'No token provided.' 
    });
  }
});

router.use("/mobile", require("./mobile"));

module.exports = router;