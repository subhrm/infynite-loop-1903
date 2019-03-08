var express = require('express');
var router = express.Router();
const db = require('../db');

/* GET users listing. */
router.get('/', function(req, res, next) {
  res.send('respond with a resource');
});

router.get('/getVisitorType', (req,res) => {
  db.getVisitorType(req,res);
});

router.post('/addVisitorSecurity',(req,res)=> {
  let Name = req.body.Name;
  let Email = req.body.Email;
  let Photo = req.body.Photo;
  let Mobile = req.body.Mobile;
  let VisitorType = req.body.VisitorType;
  let IN = req.body.IN;
  let OUT = req.body.OUT;
  db.addVisitorSecurity(req,res,Name,Email,Photo,Mobile,VisitorType,IN,OUT);
})

module.exports = router;
