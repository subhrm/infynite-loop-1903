const app = require('express')();
const routes = require('./routes');
var mysql = require('mysql');
var con = mysql.createConnection({
    host: "35.207.12.149",
    port: 8306,
    user: "user",
    password: "Infy123+"
});

con.connect(function(err) {
    if (err) throw err;
    console.log("Connected!");
});

app.use('/', routes);

// const routes = require('express').Router();


app.listen(3000, () => {
    console.log('App listening on port 3000');
});