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
    });
}


exports.getVisitors = function(req,res) {
    con.connect(function(err){
        if(err) throw err;
        let todayDate = new Date();
        const todayStartDate = todayDate.setHours(0,0,0,0);
        const todayEndDate = todayDate.setHours(24,0,0,0);
        const query = `select count(*) total,  from visitor where expected_in_time>"${todayStartDate}" and expected_out_time<"${todayEndDate}"`;
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
    });
}


exports.getVisitorType = function(req,res) {
    con.connect(function(err){
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
    });
}