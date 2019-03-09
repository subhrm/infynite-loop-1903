require('dotenv').config();

var mysql = require('mysql');
var request = require('request');

var con = mysql.createConnection({
    host: process.env.DB_HOST,
    port: process.env.DB_PORT,
    user: process.env.DB_USER,
    password: process.env.DB_PASS,
    database: process.env.DB_NAME
});
con.connect(function(err){
    if(err) throw err;

});
// fetches visitor's details like name, email, photo etc
exports.fetchVisitorProfile = function(req, res, id, role) {
    let query = '';
       if(role =='SEC_ADM'){
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
        v.status status,
        t.visitor_type_desc type
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
    WHERE v.id= ${id}`;
       }
       else{

       }
       try{
            // console.log(query);
            con.query(query, function(err, result) {
                console.log(result);
                if (err) throw err;
                let data = result[0];
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
                        "visitorStatus": data.status,
                        "visitorType": data.type
                    }
                }
                res.send(response);
            });
       }
       catch (e) {
            res.send({
                "status": -1,
                "message": "Failed to get data from DB",
                "error": e
            });
       }
       
}