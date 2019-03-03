
from flask import json, make_response, request

from face_recognizer import __version__, app, logger, config


@app.route('/', methods=['GET'])
def root_endpoint():
    '''
        Show welcome message !
    '''
    message = {"name":  __version__.name,
               "version": __version__.version}
    resp = make_response(json.dumps(message, indent=4), config.HTTP_STATUS_OK)
    resp.headers['Content-Type'] = 'application/json'
    return resp
