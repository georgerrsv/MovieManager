from udpClient import UDPClient
import json
from movie import Movie
from message import Message
import time


class Proxy:
    def __init__(self):
        self.client = UDPClient('localhost', 8080)
        self.count = 0
        self.maxCount = 3
        self.close = self.client.close
        self.requestId = 1

    def addMovie(self, movie):
        movieJson = movie.toJson()
        data = self.doOperation("movie", 1, movieJson)

        if "Successfully" in data:
            print("\nFilme cadastrado com sucesso!\n")
            return

        if "Error" in data:
            print("\nFilme ja cadastrado!\n")
            return

        try:
            header = Message.fromJson(data)
        except Exception as e:
            print("Error:", str(e))
            return

        print(header.arguments)

    def removeMovie(self, id):
        data = self.doOperation("movie", 2, id)

        if "Movie not found" in data:
            print("\nMovie not found!\n")
            return

        if "Successfully" in data:
            print("\nFilme removido com sucesso!\n")
            return

        try:
            header = Message.fromJson(data)
        except Exception as e:
            print("Error:", str(e))
            return

        print(header.arguments)

    def showDetails(self, id):
        data = self.doOperation("movie", 3, id)

        if "Movie not found" in data:
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

        if "Empty catalog" in data:
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
            print("\nTimeout!\n")
            time.sleep(1)
            print("Trying again...")
            time.sleep(1)
        else:
            print("\nServer is unavailable. Try again later...\n")
            time.sleep(1)
            self.close()
            exit()

    def doOperation(self, objectReference, methodId, arguments):
        requestId = self.requestId
        message = Message(0, objectReference, methodId, arguments, requestId)
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