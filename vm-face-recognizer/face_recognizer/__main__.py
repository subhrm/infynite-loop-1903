import threading
import os
from face_recognizer import config, logger, start_http_server

def main():
    '''
        The main method
    '''

    logger.info("Starting server on host : %s , port : %s !",
                config.HOST, config.PORT)

    start_http_server()


'''
    The main entry point
'''

if __name__ == '__main__':
    main()
