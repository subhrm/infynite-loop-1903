var nodemailer = require('nodemailer');
require('dotenv').config();
var user_email = 'anubhab.mondal11@gmail.com';
console.log(process.env.email_id)
// var transporter = nodemailer.createTransport({
//     service: 'gmail',
//     host: 'smtp.gmail.com',
//     auth: {
//         user: process.env.email_id,
//         pass: process.env.email_pass
//     }
// });

// const mailOptions = {
//     from: "anubhabm07@gmail.com",
//     to: user_email,
//     subject: 'Gate pass for your visit in Infosys',
//     text: 'Test Mail'
// };

// transporter.sendMail(mailOptions, function (error, info) {
//     if (error) {
//         console.log(error);
//     } else {
//         console.log('Email sent: ' + info.response);
//     }
// });

var transporter = nodemailer.createTransport({
    service: 'gmail',
    auth: {
           user: process.env.email_id,
           pass: process.env.email_pass
       }
   });

const mailOptions = {
    from: 'anubhabm07@gmail.com', // sender address
    to: 'anubhab.mondal11@gmail.com', // list of receivers
    subject: 'Subject of your email', // Subject line
    html: '<p>Your html here</p>'// plain text body
  };

transporter.sendMail(mailOptions, function (err, info) {
    if(err)
      console.log(err)
    else
      console.log(info);
 });