from udpClient import UDPClient
import json
from movie import Movie
from message import Message
import time


class movieProxy:

    def __init__(self):
        self.client = UDPClient('localhost', 8080)
        self.count = 0
        self.maxCount = 3
        self.requestId = 1
        self.close = self.client.close

    def addMovie(self, movie):
        movieJson = movie.toJson()
        data = self.doOperation("movie", 1, movieJson)

        if "Success" in data:
            print("\nMovie successfully added!\n")
            return

        if "Error" in data:
            print("\nMovie already registered!\n")
            return

        try:
            header = Message.fromJson(data)
            print(header.arguments)
        except Exception as e:
            print("Error:", str(e))
            return

    def removeMovie(self, id):
        data = self.doOperation("movie", 2, id)

        if "Error" in data:
            print("\nMovie not found!\n")
            return

        if "Success" in data:
            print("\nMovie successfully removed!\n")
            return

        try:
            header = Message.fromJson(data)
            print(header.arguments)
        except Exception as e:
            print("Error:", str(e))
            return

    def showDetails(self, id):
        data = self.doOperation("movie", 3, id)

        if "Error" in data:
            print("\nMovie not found!\n")
            return

        try:
            header = Message.fromJson(data)
            movie = Movie.fromJson(header.arguments)
            movieHour = int(movie.duration) // 60
            movieMinute = int(movie.duration) % 60
            print(f"\nTitle {movie.title}\nDirector: {movie.director}\nYear: {movie.year}\nDuration: {movieHour}h{movieMinute}min\nGenre: {movie.genre}\nClassification: {movie.classification}\nRating: {movie.rating}\nDescription: {movie.description}\n")
        except Exception as e:
            print("Error:", str(e))
            return

    def showCatalog(self):
        data = self.doOperation("movie", 4, "")

        if "Error" in data:
            print("\nEmpty catalog!\n")
            return

        try:
            header = Message.fromJson(data)
            catalog = json.loads(header.arguments)
            for movie in catalog:
                print(f"\nID: {movie['movieId']}\nTitle: {movie['title']}\n")
        except Exception as e:
            print("Error:", str(e))
            return

    def timeout(self):
        if self.count < self.maxCount:
            print("\nResponse time exceeded.\n")
            time.sleep(1)
            print("Trying again...")
            time.sleep(1)
        else:
            print("\nServer is unavailable. Try again later...\n")
            time.sleep(1)
            self.close()
            exit()

    def doOperation(self, objectReference, methodId, arguments):
        message = Message(0, objectReference, methodId, arguments, self.requestId)
        headerJson = message.toJson()

        while True:
            try:
                self.client.sendRequest(headerJson)
                data = self.client.getResponse()
            except Exception as e:
                self.timeout()
                self.count += 1
                continue
            break
        
        self.count = 0
        self.requestId += 1
        messageBytes = data[:len(data)]
        return messageBytes