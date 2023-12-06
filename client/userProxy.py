from udpClient import UDPClient
import json
from user import User
from message import Message
import time

class userProxy:
    
        def __init__(self):
            self.client = UDPClient('localhost', 8080)
            self.count = 0
            self.maxCount = 3
            self.requestId = 1
            self.close = self.client.close

        def addUser(self, user):
            userJson = user.toJson()
            data = self.doOperation("user", "add", userJson)

            if "Success" in data:
                print("\nUser successfully added!\n")
                return

            if "Error" in data:
                print("\nUser already registered!\n")
                return

            try:
                header = Message.fromJson(data)
                print(header.arguments)
            except Exception as e:
                print("Error:", str(e))
                return
        
        def login(self, email, password):
            user = User("", "", email, password)
            userJson = user.toJson()
            data = self.doOperation("user", "login", userJson)

            if "Error" in data:
                print("\nUser not found!\n")
                return

            if "Success" in data:
                print("\nUser successfully logged in!\n")
                return

            try:
                header = Message.fromJson(data)
                print(header.arguments)
            except Exception as e:
                print("Error:", str(e))
                return
            
        def recovery(self, email):
            data = self.doOperation("user", "recovery", email)

            if "Error" in data:
                print("\nUser not found!\n")
                return

            if "Success" in data:
                print("\nEmail sent!\n")
                return

            try:
                header = Message.fromJson(data)
                print(header.arguments)
            except Exception as e:
                print("Error:", str(e))
                return
            
        def removeUser(self, userId):
            data = self.doOperation("user", "remove", userId)

            if "Error" in data:
                print("\nUser not found!\n")
                return

            if "Success" in data:
                print("\nUser successfully removed!\n")
                return

            try:
                header = Message.fromJson(data)
                print(header.arguments)
            except Exception as e:
                print("Error:", str(e))
                return
            
        def showDetails(self, userId):
            data = self.doOperation("user", "show", userId)

            if "Error" in data:
                print("\nUser not found!\n")
                return
            
            try:
                header = Message.fromJson(data)
                print(header.arguments)
            except Exception as e:
                print("Error:", str(e))
                return
            
        def showAll(self):
            data = self.doOperation("user", "showAll", "")

            if "Error" in data:
                print("\nUser not found!\n")
                return
            
            try:
                header = Message.fromJson(data)
                catalog = json.loads(header.arguments)
                for user in catalog:
                    print(f"\nID: {user['userId']}\nUsername: {user['fname']} {user['lname']}")
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