import os
import base64

from cryptography.hazmat.backends import default_backend
from cryptography.hazmat.primitives.asymmetric import rsa, padding
from cryptography.hazmat.primitives import hashes, serialization

from face_recognizer import logger, config


class Encryption_Model:
    def __init__(self):
        self.altchars = "#!".encode("utf-8")
        self.private_key_file=os.path.join(
            config.DATA_DIR, "private_key.pem")
        self.public_key_file=os.path.join(config.DATA_DIR, "public_key.pem")
        self.load_keys()
        logger.info("Encryption Model initialized")

    def load_keys(self):
        # check if both the key files exists
        if os.path.exists(self.private_key_file) and os.path.exists(self.public_key_file):
            logger.info("Loading keys from file")
            with open(self.private_key_file, "rb") as key_file:
                self.private_key=serialization.load_pem_private_key(
                    key_file.read(),
                    password = None,
                    backend = default_backend()
                )
            with open(self.public_key_file, "rb") as key_file:
                self.public_key = serialization.load_pem_public_key(
                    key_file.read(),
                    backend=default_backend()
                )
            logger.info("Keys loaded from disk")

        else:
            logger.warn("key files does not exist. Creating them")
            self.private_key = rsa.generate_private_key(
                public_exponent=65537,
                key_size=config.KEY_SIZE,
                backend=default_backend()
            )
            self.public_key = self.private_key.public_key()
            logger.info("Keys generated. Now saving to disk")

            with open(self.private_key_file, 'wb') as f:
                pem = self.private_key.private_bytes(
                    encoding=serialization.Encoding.PEM,
                    format=serialization.PrivateFormat.PKCS8,
                    encryption_algorithm=serialization.NoEncryption()
                )
                f.write(pem)
                logger.info("Private key saved at : %s", self.private_key_file)

            with open(self.public_key_file, 'wb') as f:
                pem = self.public_key.public_bytes(
                    encoding=serialization.Encoding.PEM,
                    format=serialization.PublicFormat.SubjectPublicKeyInfo
                )
                f.write(pem)
                logger.info("Public key saved at %s", self.public_key_file)

    def encrypt(self, text):
        ''' Encrypt the text '''
        text = str(text).encode("utf-8")
        cipher = self.public_key.encrypt(
            text,
            padding.OAEP(
                mgf=padding.MGF1(algorithm=hashes.SHA256()),
                algorithm=hashes.SHA256(),
                label=None
            )
        )
        return base64.b64encode(cipher, self.altchars).decode("utf-8")

    def decrypt(self, cipher_text):
        ''' decrypt the text '''
        cipher_bytes = base64.b64decode(cipher_text.encode("utf-8"),altchars=self.altchars)
        plain_text = self.private_key.decrypt(
            cipher_bytes,
            padding.OAEP(
                mgf=padding.MGF1(algorithm=hashes.SHA256()),
                algorithm=hashes.SHA256(),
                label=None
            )
        )
        return plain_text.decode("utf-8")
