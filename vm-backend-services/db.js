require('dotenv').config();
const qr_generation = require('./utils/qr_generation');
const pdfEmail = require('./utils/pdfEmail');

var mysql = require('mysql');
var request = require('request');
var con = mysql.createConnection({
    host: process.env.DB_HOST,
    port: process.env.DB_PORT,
    user: process.env.DB_USER,
    password: process.env.DB_PASS,
    database: process.env.DB_NAME
});
const qrEncodeUrl = 'http://35.207.12.149:8000/api'

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
    const query = `select * from visitor_access where visitor_type_cd =(select visitor_type_cd from visitor where id=$      {visitorId})and location_code=(select location_id from security where id=${securityId})`
    const query1= `select location_code from visitor_access where visitor_type_cd = (select visitor_type_cd from visitor    where id= ${visitorId}) and location_code=(select location_id from security where id=${securityId})`
    try {
        con.query(query, function(err, result) {
            if (err) throw err;
            con.query(query1, function(err1, result1){
                 console.log(result1);
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
    const query = `SELECT COUNT(1) total, SUM(IF(status = 1, 1, 0)) inside, SUM(IF(status <> 1, 1, 0)) remaining FROM visitor WHERE DATE(expected_in_time) = CURDATE();`;
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


exports.getVisitorDetail = async function(req,res, visitorId) {
    const query = `SELECT
    v.id visitor_id,
    t.visitor_type_cd visitor_type,
    v.name,
    i.image_data photo,
    e.name referred_by
    FROM
        visitor v
    JOIN images i
    ON
        v.uploaded_photo = i.image_id
    JOIN visitor_type t
    ON
        v.visitor_type_cd = t.visitor_type_cd
    LEFT OUTER JOIN employee e
    ON
        v.refered_by = e.id
    WHERE v.id= ${visitorId}`;

    return new Promise(function(resolve, reject) {
        con.query(query, function(err, result){
            if (err) reject(err);
            console.log(result)
            // res.send(result);
            resolve(result);
        });
    })
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
                const query1 =`update visitor set status = -1,expected_out_time= CURRENT_TIMESTAMP where status = ${result[0].status}  `
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

exports.addVisitorSecurity = function(req,res,Name,Email,Photo,Mobile,VisitorType,IN,OUT) {
    const query = `insert into visitor (visitor_type_cd,name,email,actual_photo,mobile,expected_in_time,expected_out_time) values ('${VisitorType}','${Name}','${Email}',${Photo},${Mobile},'${IN}','${OUT}')`;
    console.log(query)
    try {
        con.query(query, function (err, result) {
            if(err) throw err;
            
            const query1 = `select image_data  from images where image_Id =${Photo}`;
            con.query(query1, function (err, result1){
                if(err) throw err;
                
            
                
                let cipher_response = request.post({
                    "headers": {
                        "content-type": "application/json"
                    },
                    "url": qrEncodeUrl + '/generate-code',
                    "body": JSON.stringify({
                        "plain_text": result.insertId.toString()
                    })
                }, function (error, response, body) {
                    let cipher_id = JSON.parse(body).cipher_text;
                    console.log(JSON.parse(body).cipher_text)

                    qr_generation.getQrSvg(cipher_id).then(qrData => {
                        res.send({
                            status:req.app.get('status-code').success,
                            message: "Success",
                            data: {
                                Name:Name,
                                Photo:result1[0].image_data,
                                QR_code: qrData
                            }
                        });
                    })
            });

                // res.send({
                //     status:req.app.get('status-code').success,
                //     message: "Success",
                //     data: {
                //         Name:Name,
                //         Photo:result1[0],
                //         QR_code=exports.getQrSvgO(cipher_id)

                //     }
                // });
            });
            
           
        })
    } catch (error) {
        console.log(error)
        res.send({
            status: req.app.get('status-code').error,
            message: "Failure"
        });
    }
}


exports.addVisitorEmployee = function(req,res,name,email,photo,mobile,visitorType,in_time,out_time) {
    const query = `insert into visitor (visitor_type_cd,name,email,actual_photo,mobile,expected_in_time,expected_out_time) values ('${visitorType}','${name}','${email}',${photo},${mobile},'${in_time}','${out_time}')`;
    console.log(query);
    try {
        con.query(query, function (err, result) {
            if(err) throw err;
            console.log(result);
            const userObj = {id: result.insertId, name: name, expected_in_time: in_time, email: email};
            pdfEmail.sendEmail(req, res, userObj);
            // res.send({
            //     status:req.app.get('status-code').success,
            //     message: "Data fetched successfully",
            // })
        });
    } catch (error) {
        console.log(error)
        res.send({
            status: req.app.get('status-code').error,
            message: "Failure"
        });
    }
}


exports.getApprovedVisitorsToday = function(req,res){
    const query = `SELECT
        v.id visitor_id,
        t.visitor_type_cd visitor_type,
        v.name,
        e.name referred_by
        FROM
            visitor v
        JOIN visitor_type t
        ON
            v.visitor_type_cd = t.visitor_type_cd
        LEFT OUTER JOIN employee e
        ON
            v.refered_by = e.id
        WHERE DATE(expected_in_time) = CURDATE();`
    try {
        con.query(query, function (err, result) {
            if(err) throw err;
            console.log(result);
            res.send({
                status:req.app.get('status-code').success,
                message: "Data fetched successfully",
                data: result
            })
        });
    } catch (error) {
        console.log(error)
        res.send({
            status: req.app.get('status-code').error,
            message: "Failure"
        });
    }
}

exports.fetchEmployeeDetails = function(req,res,id) {
    const query = `Select id,name,email,mobile,image_data from employee e join images i on i.image_id=e.photo where id=${id}`;
    console.log(query);
    try {
        con.query(query, function (err, result) {
            if(err) throw err;
            console.log(result);
            res.send({
                status:req.app.get('status-code').success,
                message: "Data fetched successfully",
                data: result
            })
        });
    } catch (error) {
        console.log(error)
        res.send({
            status: req.app.get('status-code').error,
            message: "Failure"
        });
    }
}

exports.getVisitorInsideCampus = function(req,res) {
    const query = `select v.name,v.expected_out_time,v.mobile visitorPhNo,e.mobile refererPhNo from visitor v LEFT OUTER JOIN employee e on v.refered_by = e.id where v.status=1 `;
    console.log(query);
    try {
        con.query(query, function (err, result) {
            if(err) throw err;
            console.log(result);
            res.send({
                status:req.app.get('status-code').success,
                message: "Data fetched successfully",
                data: result
            })
        });
    } catch (error) {
        console.log(error)
        res.send({
            status: req.app.get('status-code').error,
            message: "Failure"
        });
    }
}

exports.approveVisitor = function(req,res, visitorId, visitorPhoto) {
    const query = "select(max(image_id))+1 new_id from images";
    
    try {
        con.query(query, function (err, result) {
            if(err) throw err;
            console.log(result);
            const newId = result[0].new_id;
            const query1 = `INSERT into images VALUES(${newId}, "${visitorPhoto}")`
            con.query(query1, function(err1, result1) {
                if(err1) throw err1;
                console.log(result1);
                const query2 = `UPDATE visitor SET actual_photo=${newId},actual_in_time=CURRENT_TIMESTAMP, status=1 where id=${visitorId}`;
                con.query(query2, function(err2, result2) {
                    if(err2) throw err2;
                    console.log(result2);
                    res.send({
                        status:req.app.get('status-code').success,
                        message: "Data Saved successfully"
                    });

                })
            })
            
        });
    } catch (error) {
        console.log(error)
        res.send({
            status: req.app.get('status-code').error,
            message: "Failure"
        });
    }
}
