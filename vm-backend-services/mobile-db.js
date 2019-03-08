require('dotenv').config();

var mysql = require('mysql');

var con = mysql.createConnection({
    host: process.env.DB_HOST,
    port: process.env.DB_PORT,
    user: process.env.DB_USER,
    password: process.env.DB_PASS,
    database: process.env.DB_NAME
});

exports.fetchVisitorProfile = function(req, res, id, role) {
    let query = '';
       if(role =='securityadmin'){
        query= `SELECT
        v.id visitor_id,
        t.visitor_type_desc visitor_type,
        v.name,
        v.email,
        v.mobile,
        i.image_data photo,
        e.name referred_by,
        DATE_FORMAT(
            expected_in_time,
            "%Y-%m-%d %H:%i"
        ) expected_in_time,
        DATE_FORMAT(actual_in_time, "%Y-%m-%d %H:%i") actual_in_time,
        DATE_FORMAT(
            expected_out_time,
            "%Y-%m-%d %H:%i"
        ) expected_out_time,
        v.status status
    FROM
        vms.visitor v
    JOIN vms.images i
    ON
        v.uploaded_photo = i.image_id
    JOIN vms.visitor_type t
    ON
        v.visitor_type_cd = t.visitor_type_cd
    LEFT OUTER JOIN vms.employee e
    ON
        v.refered_by = e.id
    WHERE id="${id}"`;
       }
       else{

       }
       try{
        con.connect(function(err){
            if(err) throw err;
            console.log(query);
            con.query(query, function(err, result) {
                if (err) throw err;
                console.log(result);
                let data = result[0];
                const payload = {
                    check: true
                };
                var token = jwt.sign(payload, req.app.get('Secret'), {
                    expiresIn: 28800 // expires in 8 hours
                });
                let response = {
                    "status": 1,
                    "message": "",
                    "data": {
                        "visitorId": data.visitor_id,
                        "name": data.name,
                        "email": data.email,
                        "mobile": data.mobile,
                        "photo": data.photo,
                        "referredBy": data.referred_by,
                        "expectedEntry": data.expected_in_time,
                        "actualEntry": data.actual_in_time,
                        "expectedExit": data.expected_out_time,
                        "visitorStatus": data.status
                    }
                }
                res.send(response);
                // return result;
            });
        });
       }
       catch (e) {
            response.send({
                "status": 0,
                "message": "Failed to get data from DB"
            });
       }
       finally{
           con.end();
       }
}