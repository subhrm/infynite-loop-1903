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

con.connect(function(err){
    if(err) throw err;
});
exports.login = function(req, res, email, password, requestedFrom) {

    const query = `select * from vms_users where email="${email}" and password="${password}" LIMIT 0,1`
    // console.log(query)
    try {
        con.query(query, function(err, result) {
            if (err) throw err;
            // console.log(result);
            if(result.length > 0) { 
                let data = result[0];
                const payload = {
                    check: true
                };
                var token = jwt.sign(payload, req.app.get('Secret'), {
                    expiresIn: 28800 // expires in 8 hours
                });

                res.send({
                    status:req.app.get('status-code').success,
                    message: "Authentication Successful",
                    data: {
                        "userId": data.id,
                        "name": data.name,
                        "email": data.email,
                        "userRole": data.role_code,
                        "token": token
                    }
                });
            } else {
                res.send({
                    status:req.app.get('status-code').unauthorized,
                    message: "Invalid Credentials",
                    data: {}
                });
            }
        });
    } catch (error) {
        console.log(error)
        res.send({
            status: req.app.get('status-code').error,
            message: "Sorry some error occured"
        })
    }
}

exports.locationAccess = function(req,res,visitorId,securityId){
    con.connect(function(err){
        if(err) throw err;
        const query = `select * from visitor_access where "visitor_type_cd" =(select visitor_type_cd from visitor where "id"= "visitorId")and 
        "location_code"=(select location_id from security where id="sercurityId")`
        const query1= 'select location_code from visitor_access where "visitor_type_cd" = (select visitor_type_cd from visitor where "id"= "visitorId") and "location_code"=(select location_id from security where id="sercurityId")'
        try {
            con.query(query, function(err, result) {
                if (err) throw err;
                con.query(query, function(err1, result1){
    const query = `select * from visitor_access where visitor_type_cd =(select visitor_type_cd from visitor where id=${visitorId})and 
    location_code=(select location_id from security where id=${securityId})`
    const query1= `select location_code from visitor_access where visitor_type_cd = (select visitor_type_cd from visitor where id= ${visitorId}) and location_code=(select location_id from security where id=${sercurityId})`
    try {
        con.query(query, function(err, result) {
            if (err) throw err;
            con.query(query, function(err1, result1){

                 console.log(result);
                    res.send({
                        status:req.app.get('status-code').success,
                        message: query1,
                    
                    });
                });
            });
        
    }  catch (error) {
        console.log(error)
        res.send({
            status: req.app.get('status-code').error,
            message: "Sorry some error occured"
        });
    }
}

exports.getVisitors = function(req,res) {
    if(err) throw err;
    const query = `SELECT COUNT(1) total, SUM(IF(status = 2, 1, 0)) inside, SUM(IF(status <> 2, 1, 0)) remaining FROM visitor WHERE DATE(expected_in_time) = CURDATE();"`;
    console.log(query);
    try {
        con.query(query, function (err, result) {
            if(err) throw err;
            console.log(result);

            res.send(result);
        })
    } catch (error) {
        console.log(error)
        res.send({
            status: req.app.get('status-code').error,
            message: "Sorry some error occured"
        })
    }
}


exports.getVisitorType = function(req,res) {
    if(err) throw err;
    const query = "select * from visitor_type";
    try {
        con.query(query, function (err, result) {
            if(err) throw err;
            res.send({
                status:req.app.get('status-code').success,
                message: "Fetch Successful",
                data: result
            });
        })
    } catch (error) {
        console.log(error)
        res.send({
            status: req.app.get('status-code').error,
            message: "Sorry some error occured"
        })
    }
}