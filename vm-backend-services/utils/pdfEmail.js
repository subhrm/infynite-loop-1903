const PDFDocument = require('pdfkit');
const fs = require('fs');
const qr_generation = require('./qr_generation');
var nodemailer = require('nodemailer');
var dateFormat = require('dateformat');
// require('dotenv').config();
var request = require('request');
console.log(process.env.email_id)

var user_email = 'anubhab.mondal11@gmail.com';
const infyLogo = fs.readFileSync('./assets/images/infosys-logo.png');
var todayDate = dateFormat(new Date(), "dd-mmm-yyyy");
var date1 = "22 June 2019";
var textMarginLeft = 30;
var name = 'Anubhab';
const qrEncodeUrl = 'http://35.207.12.149:8000/api'
// let cipher_response = request.post({
//     "headers": {
//         "content-type": "application/json"
//     },
//     "url": qrEncodeUrl + '/generate-code',
//     "body": JSON.stringify({
//         "plain_text": "12345678"
//     })
// }, function (error, response, body) {
//     let cipher_id = body
//     console.log(cipher_id)
// });


var transporter = nodemailer.createTransport({
    host: 'smtp.gmail.com',
    port: 465,
    secure: true,
    auth: {
        user: process.env.email_id,
        pass: process.env.email_pass
    }
});

exports.sendEmail = function (req, res, userData) {
    // console.log("Inside send Mail");
    // console.log(userData);
    userData.expected_in_time = dateFormat(userData.expected_in_time,"dd-mmm-yyyy HH:MM");
    generatePDF(req, res, userData);
}

function generatePDF(req, res, userData) {
    const params = {
        "plain_text": userData.id.toString()
    };
    // let cipher_response = request.post(qrEncodeUrl+'/encrypt-code', {json: params});
    // let cipher_id = cipher_response.body()
    request.post({
        "headers": {
            "content-type": "application/json"
        },
        "url": qrEncodeUrl + '/generate-code',
        "body": JSON.stringify({
            "plain_text": userData.id.toString()
        })
    }, function (error, response, body) {
        let cipher_id =  JSON.parse(body).cipher_text;
        console.log(cipher_id);
        const op = qr_generation.getQrSvg(cipher_id);
        op.then(qrData => {

            /* To create PDF Attachment */
            const doc = new PDFDocument();
            // doc.pipe(fs.createWriteStream('output.pdf'));
            doc.image(infyLogo, 460, 20, {
                scale: 0.045,
                align: 'center',
                valign: 'right'
            });
            doc.moveDown();
            doc.fontSize(8).text(`Date: ${todayDate}`, 460, 120);
            doc.moveDown();
            doc.fontSize(10);
            doc.text(`Dear ${userData.name},`, textMarginLeft, 150, {
                width: 100,
                align: 'left'
            });
            doc.text(`  Your visit has been scheduled on ${userData.expected_in_time}. Please carry this document along with any government approved ID proof with you. Please adhere to the rules inside the campus and co-operate with the security personnel for a smooth visit.`, textMarginLeft, 165, {
                align: 'justify'
            });
            doc.moveDown();
            // doc.text('Thank You.');
            doc.moveDown();
            doc.moveDown();
            doc.image(qrData, {
                fit: [120, 120],
                align: 'left',
                valign: 'left'
            }).text(userData.id, textMarginLeft + 35, 350);
            doc.end();


            /* To write mail contents */
            const mailOptions = {
                from: process.env.email_id,
                to: userData.email,
                subject: 'Gate pass for your visit in Infosys',
                attachments: [{
                        filename: "infy-logo.png",
                        content: infyLogo,
                        cid: "infy-logo"
                    },
                    {
                        filename: "Gatepass.pdf",
                        content: doc
                    }
                ],
                html: `
                <div><img src="cid:infy-logo" width="150" height="60" style="float:right"></div>
                <br />
                <p>Dear ${userData.name},</p>
                <p>  Your  Your visit has been scheduled on ${userData.expected_in_time}.</p> 
                <p>  Please carry the attached document along with any government approved photo ID proof with you. Please adhere to the rules inside the campus and co-operate with the security personnel for a smooth visit.</p>
                <p>Infosys Ltd.</p>
                `
            };
            // console.log(mailOptions);
            // console.log(process.env.email_pass)
            // sendMail(mailOptions);
            transporter.sendMail(mailOptions, function (error, info) {
                if (error) {
                    console.log(error);
                    res.send({
                        status:req.app.get('status-code').error,
                        message: "Some Error Occured",
                        error: error
                    })
                } else {
                    console.log('Email sent: ' + info.response);
                    res.send({
                        status:req.app.get('status-code').success,
                        message: "Data Saved successfully",
                    })
                }
            });

            
        }).catch(err => {
            console.log(err);
        });
    });


}
// const op = qr_generation.getQrSvg();
// op.then(data => {
//     const doc = new PDFDocument();
//     doc.pipe(fs.createWriteStream('output.pdf'));
//     doc.image(infyLogo, 460, 20, {
//         scale: 0.045,
//         align: 'center',
//         valign: 'right'
//     });
//     doc.fontSize(10);
//     doc.moveDown();
//     doc.text('Dear Visitor,', textMarginLeft, 100, {
//         width:100,
//         align: 'left'
//     });
//     doc.text(`  Your visit has been scheduled on ${date1}. Please carry this document along with any government approved ID proof with you. Please adhere to the rules inside the campus and co-operate with the security personnel for a smooth visit.`, textMarginLeft, 115, {
//         align: 'justify'
//     });
//     doc.moveDown();
//     // doc.text('Thank You.');
//     doc.moveDown();
//     doc.moveDown();
//     doc.image(data,  {
//         fit: [150,150],
//         align: 'left',
//         valign: 'left'
//     }).text("12345678", textMarginLeft + 40, 310);
//     doc.end();
// }).catch(err => {
//     console.log(err);
// });

exports.isgApprovalMail = function(req,res, userObj, respData) {
    // let cipher_response = request.post({
    //     "headers": {
    //         "content-type": "application/json"
    //     },
    //     "url": qrEncodeUrl + '/generate-code',
    //     "body": JSON.stringify({
    //         "plain_text": userObj.userId
    //     })
    // }, function (error, response, body) {
    //     let cipher_id =  JSON.parse(body).cipher_text;
    //     console.log(cipher_id);
        const approve_link = `http://35.207.12.149:8000/api/approve-request?code=${userObj.cipher_id}`;
        const mailOptions = {
            from: process.env.email_id,
            to: userObj.admin_email,
            subject: `Visitor Approval Request for ${userObj.name}`,
            attachments: [{
                    filename: "infy-logo.png",
                    content: infyLogo,
                    cid: "infy-logo"
                },
            ],
            html: `
            <div><img src="cid:infy-logo" width="150" height="60" style="float:right"></div>
            <br />
            <p>Dear Admin,</p>
            <p>  A visitor is waiting for you in the gate. Please approve by clicking the link. To reject ignore this mail.</p> 
            <a href="${approve_link}">Approve</a><br />
            <p>Security Team</p>
            `
        };

        transporter.sendMail(mailOptions, function (error, info) {
            if (error) {
                console.log(error);
                res.send({
                    status:req.app.get('status-code').error,
                    message: "Some Error Occured",
                    error: error
                })
            } else {
                console.log('Email sent: ' + info.response);
                res.send(respData);
            }
        });
    // });
   
}


function sendMail(mailOptions) {
    transporter.sendMail(mailOptions, function (error, info) {
        if (error) {
            console.log(error);
        } else {
            console.log('Email sent: ' + info.response);
        }
    });
}