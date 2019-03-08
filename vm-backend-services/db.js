require('dotenv').config();

var mysql = require('mysql');

var con = mysql.createConnection({
    host: process.env.DB_HOST,
    port: process.env.DB_PORT,
    user: process.env.DB_USER,
    password: process.env.DB_PASS,
    database: process.env.DB_NAME
});

// con.connect(function(err){
//     if(err) throw err;
//     console.log('connected')
//     con.query('select * from vms_users', function(err, result) {
//         if (err) throw err;
//         console.log(result);
//         con.end();
//     })
// })


exports.login = function(req, res, email, password, requestedFrom) {
    con.connect(function(err){
        if(err) throw err;
        const query = `select * from vms_users where email="${email}" and password="${password}"`
        console.log(query)
        con.query(query, function(err, result) {
            if (err) throw err;
            console.log(result);
            con.end();
            res.send({status:1})
            // return result;
        });
    });
}