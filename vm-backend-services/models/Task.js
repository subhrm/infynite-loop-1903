// const db=require('../dbconnection');
// const con = db.connection;
var mysql= require('mysql');
var connection=mysql.createConnection({
    host: "35.207.12.149",
    port: 8306,
    user: "user",
    password: "Infy123+",
    database: 'vms'
});
connection.connect(function(err, res) {
    if (err) throw err;
    console.log("Connected!", res);
});
var Task={

getAllUsers: function(req,res){
    connection.query('show tables', function(err, rows, fields) {
    connection.end();
      if (!err){
        console.log('The solution is: ', rows);
    }
      else
        console.log('Error while performing Query.', err);
      });
    },
// getTaskById:function(id,callback){

//     return db.query("select * from task where Id=?",[id],callback);
// },
}
module.exports=Task;