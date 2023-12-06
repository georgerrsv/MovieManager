from json import *

class Movie:
    
    def __init__(self, title, director, year, duration, genre, classification, rating, description):
        self.title = title
        self.director = director
        self.year = year
        self.duration = duration
        self.genre = genre
        self.classification = classification
        self.rating = rating
        self.description = description

    def toJson(self):
        header = {
            "title": self.title,
            "director": self.director,
            "year": self.year,
            "duration": self.duration,
            "genre": self.genre,
            "classification": self.classification,
            "rating": self.rating,
            "description": self.description
        }
        return dumps(header)
    
    @classmethod
    def fromJson(cls, jsonStr):
        return cls(**loads(jsonStr))