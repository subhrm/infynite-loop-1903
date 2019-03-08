import os
import numpy as np
import cv2
import keras
from keras.engine import Model
# from keras_vggface.vggface import VGGFace
from sklearn.metrics.pairwise import cosine_similarity

from face_recognizer import config, logger
from face_recognizer.utils import image_utils


class Face_Model:
    def __init__(self):
        ''' Initialize '''
        logger.info("Initializing model")
        # vggface = VGGFace(model='resnet50')
        # out = vggface.get_layer("flatten_1").output
        # self.model = Model(vggface.input, out)
        self.model = keras.models.load_model(os.path.join(config.DATA_DIR, "facenet_keras.h5"))
        logger.info("facenet model initialized")

        
        self.face_cascade_models = []
        for fname in config.FACE_CASCADE_MODELS:
            cv2_model_file = os.path.join(config.DATA_DIR, fname)
            if not os.path.exists(cv2_model_file):
                raise Exception("CV2 model file %s does not exist", cv2_model_file)
            self.face_cascade_models.append(cv2.CascadeClassifier(cv2_model_file))

        logger.info("OpenCV cascade models initialized. # of models : %d", len(self.face_cascade_models))

    def vectorize(self, image):
        '''
            Convert the given image to a vector form using trained resnet model
        '''
        return self.model.predict(image.reshape((1, 160, 160, 3)))

    def predict(self, image1_path, image2_path):
        '''
            Predict the similarity of faces in the two given images
        '''
        img1 = cv2.imread(image1_path)
        img2 = cv2.imread(image2_path)
        face1 = self.get_face_from_image(img1)
        face2 = self.get_face_from_image(img2)

        v1 = self.vectorize(face1)
        v2 = self.vectorize(face2)

        score = cosine_similarity(v1, v2).reshape((1,))

        return score[0]

    
    def get_face_from_image(self, img):
        '''
            Find , crop and resize the face in the given image.

            Raises exception if no or more than 1 face found in the image
        '''
        
        faces = []
        for model in self.face_cascade_models:
            faces.extend(list(model.detectMultiScale(img)))
        logger.info("# of faces found : %d", len(faces))
        
        face = image_utils.merge_faces(faces)
        x, y, w, h = face
        cropped_face = img[y:y + h, x:x + w]
        cropped_face = cv2.cvtColor(cropped_face, cv2.COLOR_BGR2RGB)
        cropped_face = cv2.resize(cropped_face, (160, 160))

        return cropped_face
