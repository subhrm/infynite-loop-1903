var express = require('express');
var router = express.Router();
var mobileDB = require('../mobile-db');
const db = require('../db');
const generatePdf = require('../utils/generatePdf');

/* GET users listing. */
router.get('/', function(req, res, next) {
  res.send('respond with a resource');
});

router.post('/getVisitorProfile', function(req, res, next){
  let id = req.body.visitorId;
  let role = req.body.securityRole;
  console.log("req received:", typeof(id), typeof(role));
  mobileDB.fetchVisitorProfile(req, res, id, role);
});

router.get('/getVisitors', (req,res) => {
  db.getVisitors(req,res);
})

router.get('/locationAccess',(req,res)=> {
  let visitorId = req.body.visitorId;
  let securityId = req.body.securityId;
  db.locationAccess(req,res,visitorId,securityId);
});

router.post('/generateGatePass', (req,res) => {
  const visitorId = req.body.visitorId;
  generatePdf.generateGatePass(req,res, visitorId);
})

module.exports = router;


