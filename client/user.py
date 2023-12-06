from json import *

class User:

    def __init__(self, fname, lname, email, password):
        self.fname = fname
        self.lname = lname
        self.email = email
        self.password = password

    def toJson(self):
        header = {
            "fname": self.fname,
            "lname": self.lname,
            "email": self.email,
            "password": self.password
        }
        return dumps(header)
    
    @classmethod
    def fromJson(cls, jsonStr):
        return cls(**loads(jsonStr))