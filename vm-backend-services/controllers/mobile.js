var express = require('express');
var router = express.Router();
const db = require('../db');

/* GET users listing. */
router.get('/', function(req, res, next) {
  res.send('respond with a resource');
});

router.get('/getVisitors', (req,res) => {
  db.getVisitors(req,res);
})



module.exports = router;
