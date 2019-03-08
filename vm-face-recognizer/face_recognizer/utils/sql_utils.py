import os
import mysql.connector

from face_recognizer import config, logger


def get_connection():
    logger.info("Trying to connect to MySQL database")

    cnx = None

    try:
        uid = os.environ.get(config.SQL_SERVER_USER_ID)
        pwd = os.environ.get(config.SQL_SERVER_USER_PWD)
        # print(uid, pwd)
        cnx = mysql.connector.connect(host=config.SQL_SERVER_IP,
                                      port=config.SQL_SERVER_PORT,
                                      user=uid, 
                                      password=pwd,
                                      database=config.DB_NAME)
    except Exception:
        logger.exception("SQL Connection failed")
        exit(1)

    return cnx

def add_photo(img):

    cnx = get_connection()
    image_id = 0

    try:
        cursor = cnx.cursor()
        cursor.execute("SELECT max(image_id) FROM images ")
        res = cursor.fetchone()
        image_id = res[0] + 1
        cursor.close()
        # print("Image id :", image_id)
        # print(text)
        cursor = cnx.cursor()
        cursor.execute("INSERT INTO images (image_id,image_data) VALUES (%s ,%s)", (image_id, img))
        cnx.commit()
        cursor.close()
        cnx.close()
    except Exception as ex:
        logger.exception(str(ex))
        raise Exception("Some thing went wrong")

    return image_id

def get_photos():

    cnx = get_connection()
    
    try:
        cursor = cnx.cursor()
        cursor.execute("SELECT image_id,image_data FROM images ")
        res = cursor.fetchall()
        resp = []
        for row in res:
            resp.append(row)
        cursor.close()
        cnx.close()
    except Exception as ex:
        logger.exception(str(ex))
        raise Exception("Some thing went wrong")

    return resp

