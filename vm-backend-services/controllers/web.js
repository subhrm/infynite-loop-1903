var express = require('express');
var router = express.Router();
const db = require('../db');
const pdfEmail = require('../utils/pdfEmail');


/* GET users listing. */
router.get('/', function(req, res, next) {
  res.send('respond with a resource');
});

router.get('/getVisitorType', (req,res) => {
  db.getVisitorType(req,res);
});

router.post('/addVisitorEmp', (req,res) => {
  pdfEmail.generateGatePass(req,res, userData);
});

module.exports = router;
