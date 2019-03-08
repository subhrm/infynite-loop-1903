
from flask import json, make_response, request

from face_recognizer import __version__, app, logger, config
from face_recognizer.utils import sql_utils
from face_recognizer.models import EM


@app.route('/api/approve-request', methods=['GET'])
def approve_request():
    '''
        Show welcome message !
    '''
    # message = {"images" : sql_utils.get_photos(id)}
    # resp = make_response(json.dumps(message), config.HTTP_STATUS_OK)
    # resp.headers['Content-Type'] = 'application/json'
    # return resp

    logger.info("Processing request for approve request")
    status = config.HTTP_STATUS_OK
    result = {"success": "request approved"}
    try:
        code = request.args.get('code')
        visitor_id = int(EM.decrypt(code))
        sql_utils.approve_request(visitor_id)
    except Exception as ex:
        logger.exception("Something went wrong")
        result = {"error" : str(ex)}
        status = config.HTTP_STATUS_ERROR

    resp = make_response(json.dumps(result), status )
    resp.headers['Content-Type'] = 'application/json'

    return resp




