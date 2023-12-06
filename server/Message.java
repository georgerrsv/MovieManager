import org.json.JSONObject;

public class Message {
    private int messageType;
    private String objectReference;
    private String methodId;
    private String arguments;

    public Message(int messageType, String objectReference, String methodId, String arguments) {
        this.messageType = messageType;
        this.objectReference = objectReference;
        this.methodId = methodId;
        this.arguments = arguments;
    }

    public String toJson() {
        JSONObject headerData = new JSONObject();
        headerData.put("messageType", messageType);
        headerData.put("objectreference", objectReference);
        headerData.put("methodId", methodId);
        headerData.put("arguments", arguments);
        return headerData.toString();
    }

    public static Message fromJson(String jsonStr) {
        JSONObject headerData = new JSONObject(jsonStr);
        return new Message(
                headerData.getInt("messageType"),
                headerData.getString("objectreference"),
                headerData.getString("methodId"),
                headerData.get("arguments").toString()
        );
    }

    public String getObjectReference() {
        return objectReference;
    }

    public String getMethodId() {
        return methodId;
    }

    public String getArguments() {
        return arguments;
    }
}