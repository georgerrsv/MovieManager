from json import *

class Message:

    def __init__(self, messageType, objectreference, methodId, arguments, requestId):
        self.messageType = messageType
        self.objectreference = objectreference
        self.methodId = methodId
        self.arguments = arguments
        self.requestId = requestId

    def toJson(self):
        header = {
            "messageType": self.messageType,
            "objectreference": self.objectreference,
            "methodId": self.methodId,
            "arguments": self.arguments,
            "requestId": self.requestId
        }
        return dumps(header)

    @classmethod
    def fromJson(cls, jsonStr):
        header = loads(jsonStr)
        return cls(
            header["messageType"],
            header["objectreference"],
            header["methodId"],
            header["arguments"],
            header.get("requestId", 0)
        )