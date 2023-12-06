from json import *

class Message:

    def __init__(self, messageType, objectReference, methodId, arguments, requestId):
        self.messageType = messageType
        self.objectReference = objectReference
        self.methodId = methodId
        self.arguments = arguments
        self.requestId = requestId

    def toJson(self):
        headerData = {
            "messageType": self.messageType,
            "objectreference": self.objectreference,
            "methodId": self.methodId,
            "arguments": self.arguments,
            "requestId": self.requestId
        }
        return dumps(headerData)

    @classmethod
    def fromJson(cls, json_str):
        headerData = loads(json_str)
        return cls(
            headerData["messageType"],
            headerData["objectreference"],
            headerData["methodId"],
            headerData["arguments"],
            headerData.get("requestId", 0)
        )