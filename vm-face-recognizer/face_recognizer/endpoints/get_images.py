
from flask import json, make_response, request

from face_recognizer import __version__, app, logger, config
from face_recognizer.utils import sql_utils

@app.route('/api/get-images', methods=['GET'])
def get_images():
    '''
        Show welcome message !
    '''
    message = {"images" : sql_utils.get_photos()}
    resp = make_response(json.dumps(message), config.HTTP_STATUS_OK)
    resp.headers['Content-Type'] = 'application/json'
    return resp
