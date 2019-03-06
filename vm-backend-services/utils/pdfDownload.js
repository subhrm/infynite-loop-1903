const PDFDocument = require('pdfkit');
const fs = require('fs');
const qr_generation = require('./qr_generation');
var nodemailer = require('nodemailer');


const infyLogo = fs.readFileSync('../assets/images/infosys-logo.png');
var date1 = "22 June 2019";
var textMarginLeft = 30;

const op = qr_generation.getQrSvg();
op.then(data => {
    const doc = new PDFDocument();
    doc.pipe(fs.createWriteStream('output.pdf'));
    doc.image(infyLogo, 460, 20, {
        scale: 0.045,
        align: 'center',
        valign: 'right'
    });
    doc.fontSize(10);
    doc.moveDown();
    doc.text('Dear Visitor,', textMarginLeft, 100, {
        width:100,
        align: 'left'
    });
    doc.text(`  Your visit has been scheduled on ${date1}. Please carry this document along with any government approved ID proof with you. Please adhere to the rules inside the campus and co-operate with the security for a smooth visit.`, textMarginLeft, 115, {
        align: 'justify'
    });
    doc.moveDown();
    // doc.text('Thank You.');
    doc.moveDown();
    doc.moveDown();
    doc.image(data,  {
        fit: [150,150],
        align: 'left',
        valign: 'left'
    }).text("12345678", textMarginLeft + 40, 310);
    doc.end();
}).catch(err => {
    console.log(err);
});