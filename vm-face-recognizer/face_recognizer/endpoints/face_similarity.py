import os
import sys
from flask import json, make_response, request
from werkzeug.utils import secure_filename
from datetime import datetime

from face_recognizer import __version__, app, logger, config
from face_recognizer.models import FM


@app.route('/api/face-similarity', methods=['POST'])
def face_similarity_endpoint():
    '''
        Check similaity of faces in two images
    '''
    logger.info("Processing request for face similarity endpoint")
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
        Process the uploaded files and compute their similarity
    '''
    # step 1 : check if there is a file attachment
    logger.info(request.files)
    file_list = list(request.files.keys())
    num_files = len(file_list)
    if num_files < 2:
        raise Exception(
            "Needs 2 image files but received {} files".format(num_files))
    elif len(file_list) > 2:
        err_msg = "More than 2 files uploaded. Got {} files".format(num_files)
        raise Exception(err_msg)

    n = datetime.now()
    dir_name = "{}-{:02d}-{:02d}_{:02d}-{:02d}-{:02d}".format(
        n.year, n.month, n.day, n.hour, n.minute, n.second)
    full_dir_name = os.path.join(config.DATA_DIR, "requests", dir_name)
    if not os.path.exists(full_dir_name):
        os.makedirs(full_dir_name)

    file_path1 = check_save_file(request, file_list[0], full_dir_name)
    file_path2 = check_save_file(request, file_list[1], full_dir_name)

    score = FM.predict(file_path1, file_path2)
    score = float(score)
    logger.info("Similarity of %s and %s is %f", file_path1, file_path2, score)

    return {"similarity": score}


def check_save_file(request, fname, dir_name):
    # check if the file in request is not empty
    file_obj = request.files[fname]
    filename = secure_filename(file_obj.filename)
    if filename == '':
        raise Exception("No or empty file")

    content_type = file_obj.content_type
    mime_type, _ = content_type.split("/")
    logger.info("Content_type of uploaded file is : %s", str(content_type))
    if mime_type != "image":
        raise Exception("Unknown Mime Type {}".format(mime_type))

    # Save the uploaded file
    logger.info("Uploaded File Name : %s", filename)
    file_path = os.path.join(dir_name, filename)
    logger.info("Saving to : %s", file_path)
    file_obj.save(file_path)

    return file_path
