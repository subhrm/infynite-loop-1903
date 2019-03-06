const app = require('express')();
const routes = require('./routes');
var mysql = require('mysql');
require('dotenv').config()
var con = mysql.createConnection({
    host: process.env.DB_HOST,
    port: process.env.DB_PORT,
    user: process.env.DB_USER,
    password: process.env.DB_PASS
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