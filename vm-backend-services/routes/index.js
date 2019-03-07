var express = require('express');
var router = express.Router();
jwt = require('jsonwebtoken');

/* GET home page. */
router.get('/', function (req, res, next) {
  // res.render('index', { title: 'Express' });
  res.send("working")
});

router.post('/authenticate', (req, res) => {
  if (req.body.username === "aymen") {

    if (req.body.password === 123) {
      //if eveything is okey let's create our token 

      const payload = {
        check: true
      };

      var token = jwt.sign(payload, app.get('Secret'), {
        expiresIn: 1440 // expires in 24 hours
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

module.exports = router;