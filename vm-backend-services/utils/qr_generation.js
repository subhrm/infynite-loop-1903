// const qr = require('qr-image');
const qrcode = require('qrcode');

exports.getQrSvg = async function(data) {
    return new Promise(function(resolve, reject) {
        qrcode.toDataURL(data, {errorCorrectionLevel: 'M'}, function (err, url){
            if(err) {
                reject(err);
            } else {
                resolve(url);
            }
        });
    });
}