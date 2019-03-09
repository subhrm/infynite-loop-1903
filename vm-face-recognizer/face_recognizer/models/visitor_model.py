'''
    find visitor by face
'''
import numpy as np
from face_recognizer import logger
from face_recognizer.models import FM
from face_recognizer.utils import sql_utils, image_utils
from sklearn.metrics.pairwise import cosine_similarity

def find(img):
    face = FM.get_face_from_image(img)
    face_vector = FM.vectorize(face.reshape((1,224,224,3)))

    all_visitors = sql_utils.get_all_visitor_photos()
    logger.info("fetched %d visitor photos", len(all_visitors))

    visitor_ids = [v[0] for v in all_visitors]
    visitor_faces = np.vstack([ image_utils.read_b64(v[1]).reshape((1,224,224,3)) for v in all_visitors])

    logger.info(str(visitor_faces.shape))

    visitor_vectors = FM.vectorize(visitor_faces)

    c = cosine_similarity(face_vector, visitor_vectors)
    c = c.reshape((c.shape[-1],))

    max_similarity = np.max(c)

    logger.info("max simmilarity : {:.6f}".format(float(max_similarity)) )

    if max_similarity >= 0.60:
        return visitor_ids[int(np.argmax(c))]

    raise Exception("No Match found")







