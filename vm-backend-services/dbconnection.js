var mysql=require('mysql');
var connection=mysql.createPool({
    host: "35.207.12.149",
    port: 8306,
    user: "user",
    password: "Infy123+"
});
module.exports=connection;