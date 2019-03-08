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
    const query = `select * from visitor_access where visitor_type_cd =(select visitor_type_cd from visitor where id=${visitorId})and 
                    location_code=(select location_id from security where id=${securityId})`
    const query1= `select location_code from visitor_access where visitor_type_cd = (select visitor_type_cd from visitor where id= ${visitorId}) and location_code=(select location_id from security where id=${sercurityId})`
    try {
        con.query(query, function(err, result) {
            if (err) throw err;
            con.query(query1, function(err1, result1){

                 console.log(result);
                    res.send({
                        status:req.app.get('status-code').success,
                        message: result1,
                    
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
    const query = `SELECT COUNT(1) total, SUM(IF(status = 2, 1, 0)) inside, SUM(IF(status <> 2, 1, 0)) remaining FROM visitor WHERE DATE(expected_in_time) = CURDATE();`;
    const query2 = `SELECT COUNT(1) as count, visitor_date as date FROM (
        SELECT id visitor_id, DATE_FORMAT(expected_in_time, '%Y-%m-%d') visitor_date FROM visitor WHERE DATE(expected_in_time) BETWEEN date_sub(CURDATE(), INTERVAL 10 DAY) AND CURDATE()) v GROUP BY visitor_date;`
    console.log(query);
    try {
        con.query(query, function (err, result) {
            if(err) throw err;
            console.log(result);
            con.query(query2, function(err2, result2) {
                if(err) throw err;
                console.log(result2);
                res.send({
                    status:req.app.get('status-code').success,
                    message: "Data fetched successfully",
                    data: {
                        "todaysVisitors": result[0],
                        "visitorLastDays": result2
                    }
                });
            })

            
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

exports.updateGatePass = function(req,res,visitorId,depositType){
    
    const query =`select status from visitor where id=${visitorId}`;
    try {
        if(depositType=="TEMP_DISABLE"){
            
            con.query(query, function (err, result) {
                console.log(result)
                if(err) throw err;
                if(result==-1){
                    console.log("your trip is complete");
                }
                else{
                    const query1 =`update visitor set status = 2 where status = ${result[0].status} `
                    con.query(query1, function (err, result1){
                        if(err) throw err;
                        res.send(result1)
                    });
                    
                }
            

            });
        }

        if(depositType=="FINAL_SUBMIT"){
            con.query(query, function (err, result) {
                console.log(result)
                if(err) throw err;
                const query1 =`update visitor set status = -1 where status = ${result[0].status}  `
                //res.send(result)
                con.query(query1, function (err, result1){
                    if(err) throw err;
                    res.send(result1)
                });
            });

        }

        if(depositType=="RE_ENABLE"){
            con.query(query, function (err, result) {
                if(err) throw err;
                    const query1 =`update visitor set status = 1 where status = ${result[0].status} `
                    con.query(query1, function (err, result1){
                        if(err) throw err;
                        res.send(result1)
                    });
                
            });

        }

        console.log(query)


    } catch (error) {console.log(error)
        res.send({
            status: req.app.get('status-code').error,
            message: "Sorry some error occured"
        });
        
    }
}