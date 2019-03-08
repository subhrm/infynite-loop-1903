var express = require('express');
var router = express.Router();
var mobileDB = require('../mobile-db');

/* GET users listing. */
router.get('/', function(req, res, next) {
  res.send('respond with a resource');
});
router.post('/getVisitorProfile', function(req, res, next){
let id = req.body.visitorId;
  let role = req.body.securityRole;
  mobileDB.fetchVisitorProfile(req, res, id, role);
});

module.exports = router;
