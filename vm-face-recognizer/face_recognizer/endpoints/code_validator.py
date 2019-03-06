
from flask import request, make_response, json
from face_recognizer import app, logger, config
from face_recognizer.models import EM

@app.route('/api/generate-code', methods=['post'])
def generate_code():
    logger.info("Processing request for gerete code")
    status = config.HTTP_STATUS_OK
    try:
        if(request.headers['Content-Type'] != 'application/json'):
                return make_response('{"error":"unsupported content type"}', config.HTTP_STATUS_ERROR)

        # Get POST parameters
        input_json = request.json
        plain_text = input_json["plain_text"]
        cipher_text = EM.encrypt(plain_text)
        result = {"cipher_text":cipher_text}
    except Exception as ex:
        logger.exception("Something went wrong")
        result = {"error" : str(ex)}
        status = config.HTTP_STATUS_ERROR

    resp = make_response(json.dumps(result), status )
    resp.headers['Content-Type'] = 'application/json'

    return resp

@app.route('/api/decrypt-code', methods=['post'])
def decrypt_code():
    logger.info("Processing request for gerete code")
    status = config.HTTP_STATUS_OK
    try:
        if(request.headers['Content-Type'] != 'application/json'):
                return make_response('{"error":"unsupported content type"}', config.HTTP_STATUS_ERROR)

        # Get POST parameters
        input_json = request.json
        plain_text = input_json["cipher_text"]
        cipher_text = EM.decrypt(plain_text)
        result = {"plain_text":cipher_text}
    except Exception as ex:
        logger.exception("Something went wrong")
        result = {"error" : str(ex)}
        status = config.HTTP_STATUS_ERROR

    resp = make_response(json.dumps(result), status )
    resp.headers['Content-Type'] = 'application/json'

    return resp