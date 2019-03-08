import os
import sys
from flask import json, make_response, request
from werkzeug.utils import secure_filename
from datetime import datetime

from face_recognizer import __version__, app, logger, config
from face_recognizer.utils import image_utils
from face_recognizer.models import visitor_model


@app.route('/api/find-visitor-by-face', methods=['POST'])
def find_visitor_by_face():
    '''
        Find a visitor by face
    '''
    logger.info("Processing request for find-visitor-by-face endpoint")
    status = config.HTTP_STATUS_OK

    try:
        message = do_post(request)
    except Exception as ex:
        logger.exception("Something went wrong !")
        message = {"error": str(ex)}
        status = config.HTTP_STATUS_ERROR

    resp = make_response(json.dumps(message), status)
    resp.headers['Content-Type'] = 'application/json'
    return resp


def do_post(request):
    '''
        Process the uploaded images and compute their similarity
    '''
    # step 1 : check if contet type is json
    
    if(request.headers.get('Content-Type') != 'application/json'):
        raise Exception("Contet ype should be json")

    # Get POST parameters
    input_json = request.json
    image_text = input_json["image"]
    img = image_utils.read_b64(image_text)

    res = visitor_model.find(img)
    return {"visitor_id": int(res)}

