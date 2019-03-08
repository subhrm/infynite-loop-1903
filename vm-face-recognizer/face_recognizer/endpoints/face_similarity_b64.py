import os
import sys
from flask import json, make_response, request
from werkzeug.utils import secure_filename
from datetime import datetime

from face_recognizer import __version__, app, logger, config
from face_recognizer.models import FM
from face_recognizer.utils import image_utils


@app.route('/api/face-similarity-b64', methods=['POST'])
def face_similarity_b64_endpoint():
    '''
        Check similaity of faces in two images using base64 inputs
    '''
    logger.info("Processing request for face similarity b64 endpoint")
    status = config.HTTP_STATUS_OK

    try:
        message = do_post_b64(request)
    except Exception as ex:
        logger.exception("Something went wrong !")
        message = {"error": str(ex)}
        status = config.HTTP_STATUS_ERROR

    resp = make_response(json.dumps(message), status)
    resp.headers['Content-Type'] = 'application/json'
    return resp


def do_post_b64(request):
    '''
        Process the uploaded images and compute their similarity
    '''
    # step 1 : check if there is a file attachment
    
    if(request.headers['Content-Type'] != 'application/json'):
        raise Exception("Contetype should be json")

    # Get POST parameters
    input_json = request.json
    image1_text = input_json["image1"]
    img1 = image_utils.read_b64(image1_text)
    face1 = FM.get_face_from_image(img1)

    image2_text = input_json["image2"]
    img2 = image_utils.read_b64(image2_text)
    face2 = FM.get_face_from_image(img2)

    score = FM.predict(face1, face2)
    score = float(score)

    return {"similarity": score}

