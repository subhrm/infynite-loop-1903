import cv2
import numpy as np
import base64

def check_overlap(a,b):
    '''
       Check if two rectangular shapes overlap.
       If they overlap, then return the merged shape. 
    '''
    x1,y1,w1,h1 = a
    x2,y2,w2,h2 = b
    
    if (x2 >= x1+w1) or (x1 >= x2+w2):
        return False
    
    if (y2 >= y1 + h1) or (y1 > y2+h2):
        return False
    
    x_points = sorted([x1, x1+w1, x2, x2+w2])
    y_points = sorted([y1, y1+h1, y2, y2+h2])
    
    # w_i = x_points[2] - x_points[1]
    # h_i = y_points[2] - y_points[1]
    
    # # area_of_intersection
    # a_i = w_i * h_i
    # pct_overlap = max(a_i/(w1*h1), a_i/(w2*h2))
    
    # if pct_overlap < 0.1:
    #     return False
    
    combined_w = x_points[3] - x_points[0]
    combined_h = y_points[3] - y_points[0]
    
    return [x_points[0], y_points[0], combined_w, combined_h]
    
def merge_faces(faces):
    '''
        Merge faces detected by opencv harr-classifier
    '''
    if len(faces) == 0:
        raise Exception("No face Found")
    elif len(faces) == 1:
        return faces[0]
    
    print("Faces :", faces)
    res = faces[0]
    for f in faces[1:]:
        merged = check_overlap(res,f) 
        if merged:
            res = merged
        else:
            raise Exception("Found Two faces in the photograph")
            
    return res


def read_b64(text):
   # encoded_data = uri.split(',')[1]
   encoded_data = str(text)
   np_arr = np.fromstring(base64.b64decode(encoded_data), np.uint8)
   img = cv2.imdecode(np_arr, cv2.IMREAD_COLOR)
   return img

def read_from_file(file_path):
    return cv2.imread(image_path)

def image_to_b64(img):
    _, buffer = cv2.imencode(".jpg", cv2.cvtColor(img, cv2.COLOR_RGB2BGR))
    img_as_text = base64.b64encode(buffer)
    return img_as_text.decode("utf-8")