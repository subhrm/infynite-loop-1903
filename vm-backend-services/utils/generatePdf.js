const PDFDocument = require('pdfkit');
const fs = require('fs');
const qr_generation = require('./qr_generation');
var nodemailer = require('nodemailer');
var dateFormat = require('dateformat');
require('dotenv').config();
const db = require('../db');
const infyLogo = fs.readFileSync('assets/images/infosys-logo.png');

// console.log(infyLogo)

exports.generateGatePass = function(req, res, visitorId) {
    db.getVisitorDetail(req,res, visitorId).then(visitorData => {
        console.log(visitorData);
        const op = qr_generation.getQrSvg("visitor_id");
        op.then(qrRes => {
            const doc = new PDFDocument();
            doc.pipe(fs.createWriteStream('output.pdf'));
            // doc.image(visitorData.photo, 10, 10, {
            //     height: 50,
            //     width: 50
            // });
            doc.fontSize(8).text(visitorData.name)

            console.log(qrRes)
            doc.image(infyLogo, 80, 10, {
                height:20,
                width: 50,
                align: 'center',
                valign: 'right'
            });
            doc.image(qrRes, 80, 60, {
                height: 40,
                width: 40
            });
            doc.end();

        })
        res.send(visitorData);
    });

}