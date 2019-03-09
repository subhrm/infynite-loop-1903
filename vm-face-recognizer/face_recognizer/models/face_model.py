import os
import numpy as np
import cv2
from keras.engine import Model
from keras_vggface.vggface import VGGFace
from sklearn.metrics.pairwise import cosine_similarity

from face_recognizer import config, logger
from face_recognizer.utils import image_utils


class Face_Model:
    def __init__(self):
        ''' Initialize '''
        logger.info("Initializing model")
        vggface = VGGFace(model='resnet50')
        out = vggface.get_layer("flatten_1").output
        self.model = Model(vggface.input, out)
        logger.info("vggface model initialized")

        
        self.face_cascade_models = []
        for fname in config.FACE_CASCADE_MODELS:
            cv2_model_file = os.path.join(config.DATA_DIR, fname)
            if not os.path.exists(cv2_model_file):
                raise Exception("CV2 model file %s does not exist", cv2_model_file)
            self.face_cascade_models.append(cv2.CascadeClassifier(cv2_model_file))

        logger.info("OpenCV cascade models initialized. # of models : %d", len(self.face_cascade_models))

    def vectorize(self, x):
        '''
            Convert the given image to a vector form using trained resnet model
        '''
        return self.model.predict(x)

    def predict(self, face1, face2):
        '''
            Predict the similarity of faces in the two given images
        '''
        v1 = self.vectorize(face1.reshape((1, 224, 224, 3)))
        v2 = self.vectorize(face2.reshape((1, 224, 224, 3)))

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
        logger.info("# of faces found is %d", len(faces))
        
        face = image_utils.merge_faces(faces)
        x, y, w, h = face
        cropped_face = img[y:y + h, x:x + w]
        cropped_face = cv2.cvtColor(cropped_face, cv2.COLOR_BGR2RGB)
        cropped_face = cv2.resize(cropped_face, (224, 224))

        return cropped_face
