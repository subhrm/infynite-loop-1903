var express = require('express');
var router = express.Router();
var mobileDB = require('../mobile-db');
const db = require('../db');
const generatePdf = require('../utils/generatePdf');
var request = require('request');
const qrEncodeUrl = 'http://35.207.12.149:8000/api'

/* GET users listing. */
router.get('/', function(req, res, next) {
  res.send('respond with a resource');
});

//to fetch visitor details by passing visitor id and user type
router.post('/getVisitorProfile', function(req, res, next){
  let id = req.body.visitorId;
  let role = req.body.securityRole;
  let encrypted = req.body.encrypted;
  console.log(id, role, encrypted);
  if(encrypted == 1){
    let cipher_response = request.post({
      "headers": {
          "content-type": "application/json"
      },
      "url": qrEncodeUrl + '/decrypt-code',
      "body": JSON.stringify({
          "cipher_text": id
      })
    }, function (error, response, body) {
        let cipher_id = JSON.parse(body).plain_text;
        console.log(JSON.parse(body).plain_text);
        mobileDB.fetchVisitorProfile(req, res, cipher_id, role);
    });
  } else{
    mobileDB.fetchVisitorProfile(req, res, id, role);
  }
});

router.get('/getVisitors', (req,res) => {
  db.getVisitors(req,res);
});

router.post('/locationAccess',(req,res)=> {
  let visitorId = req.body.visitorId;
  let securityId = req.body.securityId;
  let encrypted = req.body.encrypted;
  if(encrypted == 1){
    let cipher_response = request.post({
      "headers": {
          "content-type": "application/json"
      },
      "url": qrEncodeUrl + '/decrypt-code',
      "body": JSON.stringify({
          "cipher_text": visitorId
      })
    }, function (error, response, body) {
        let cipher_id = JSON.parse(body).plain_text;
        db.locationAccess(req,res,cipher_id,securityId);
    });
  } else{
    db.locationAccess(req,res,visitorId,securityId);
  }
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

router.post('/getVisitorInsideCampus', (req,res) => {
  db.getVisitorInsideCampus(req,res);
})
router.post('/approveVisitor', (req,res) => {
  const visitorId = req.body.visitorId;
  const visitorPhoto = req.body.visitorPhoto;
  db.approveVisitor(req,res, visitorId, visitorPhoto);
})

module.exports = router;


