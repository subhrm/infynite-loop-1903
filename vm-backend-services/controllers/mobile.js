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
router.post('/locationAccess',(req,res)=> {
  let visitorId = req.body.visitorId;
  let securityId = req.body.securityId;
  db.locationAccess(req,res,visitorId,securityId);
})

router.get('/locationAccess',(req,res)=> {
  let visitorId = req.body.visitorId;
  let securityId = req.body.securityId;
  db.locationAccess(req,res,visitorId,securityId);
});
router.post('/updateGatePass',(req,res)=> {
  let visitorId = req.body.visitorId;
  let depositType = req.body.depositType;
  db.updateGatePass(req,res,visitorId,depositType);
})

router.post('/generateGatePass', (req,res) => {
  const visitorId = req.body.visitorId;
  generatePdf.generateGatePass(req,res, visitorId);
})

router.post('/fetchEmployeeDetails', (req,res) => {
  const Id = req.body.Id;
  db.fetchEmployeeDetails(req,res,Id);
})
module.exports = router;


