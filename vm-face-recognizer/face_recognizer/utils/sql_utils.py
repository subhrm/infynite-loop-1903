import os
import mysql.connector

from face_recognizer import config, logger


def get_connection():
    logger.info("Trying to connect to MySQL database")

    cnx = None

    try:
        uid = os.environ.get(config.SQL_SERVER_USER_ID)
        pwd = os.environ.get(config.SQL_SERVER_USER_PWD)
        print(uid, pwd)
        cnx = mysql.connector.connect(host=config.SQL_SERVER_IP,
                                      port=config.SQL_SERVER_PORT,
                                      user=uid, 
                                      password=pwd,
                                      database=config.DB_NAME)
    except Exception:
        logger.exception("SQL Connection failed")
        exit(1)

    return cnx
