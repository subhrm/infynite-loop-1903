
import os
# import tempfile

import cv2
import numpy as np

from flask import request, make_response, json
from face_recognizer import app, logger, config
from face_recognizer.models import FM
from face_recognizer.utils import image_utils, sql_utils

'''
    Handle photo uploads
'''

@app.route('/api/upload-photo-b64', methods=['post'])
def upload_photo_b64():
    logger.info("Processing request for upload-photo-b64")
    status = config.HTTP_STATUS_OK
    try:
        if(request.headers['Content-Type'] != 'application/json'):
            return make_response('{"error":"unsupported content type"}', config.HTTP_STATUS_ERROR)

        # Get POST parameters
        input_json = request.json
        image_text = input_json["image"]
        img = image_utils.read_b64(image_text)
        cropped_face = FM.get_face_from_image(img)
        base64_image = image_utils.image_to_b64(cropped_face)

        image_id = sql_utils.add_photo(base64_image)

        result={"image_id": image_id, "image_text":base64_image}

    except Exception as ex:
        logger.exception("Something went wrong")
        result = {"error" : str(ex)}
        status = config.HTTP_STATUS_ERROR

    resp = make_response(json.dumps(result), status )
    resp.headers['Content-Type'] = 'application/json'

    return resp

@app.route('/api/upload-photo', methods=['post'])
def upload_photo():
    logger.info("Processing request for upload-photo")
    status = config.HTTP_STATUS_OK
    try:
        file_list = list(request.files.keys())
        num_files = len(file_list)
        logger.info(file_list)
        if num_files == 0:
            raise Exception(
                "Needs 1 image file but received {} files".format(num_files))
        elif len(file_list) > 1:
            err_msg = "More than 2 files uploaded. Got {} files".format(num_files)
            raise Exception(err_msg)

        # Get POST parameters
        img = cv2.imdecode(np.fromstring(request.files[file_list[0]].read(), np.uint8), cv2.IMREAD_COLOR)
        # img = image_utils.read_b64(image_text)
        cropped_face = FM.get_face_from_image(img)
        base64_image = image_utils.image_to_b64(cropped_face)

        image_id = sql_utils.add_photo(base64_image)

        result={"image_id": image_id, "image_text":base64_image}

    except Exception as ex:
        logger.exception("Something went wrong")
        result = {"error" : str(ex)}
        status = config.HTTP_STATUS_ERROR

    resp = make_response(json.dumps(result), status )
    resp.headers['Content-Type'] = 'application/json'

    return resp
