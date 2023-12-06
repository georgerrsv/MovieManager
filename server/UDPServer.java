package server;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
//import java.net.InetAddress;
import java.sql.SQLException;
import java.util.TreeMap;

public class UDPServer {
    private DatagramSocket serverSocket;
    private Database db;
    private TreeMap<String, String> lastRequest;

    public UDPServer(int port) throws IOException, SQLException {
        this.serverSocket = new DatagramSocket(port);
        this.db = new Database();
        this.lastRequest = new TreeMap<>();
    }

    public void getRequest() throws IOException {
        byte[] receiveData = new byte[1024];
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        serverSocket.receive(receivePacket);

        String data = new String(receivePacket.getData(), 0, receivePacket.getLength(), "UTF-8");

        int PORT = receivePacket.getPort();
        String requestId = data.substring(data.length() - 2, data.length() - 1);
        String ADDR = receivePacket.getAddress().toString();
        ADDR = ADDR.substring(1);
        String msg = "Received: " + data + " from client with IP: " + ADDR + " and PORT: " + PORT;

        System.out.println(msg);

        //if it is duplicated, ignore the message
        if (isDuplicateMessage(PORT, requestId, ADDR)) {
            System.out.println("Duplicated message detected.");
            return;
        }

        Dispatcher dispatcher = new Dispatcher(db);
        Message responseMessage = dispatcher.invoke(data);

        //saves the last message received
        lastRequest.put("PORT", Integer.toString(PORT));
        lastRequest.put("requestId", requestId);
        lastRequest.put("ADDR", ADDR);
        lastRequest.put("lastResponse", responseMessage.toJson());

        //simulates packet loss with a 3 seconds delay
        /*
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        */

        sendResponse(responseMessage, receivePacket);
    }

    //checks if the message is duplicated
    private boolean isDuplicateMessage(int PORT, String requestId, String ADDR) {
        return lastRequest.containsKey("PORT") && lastRequest.containsKey("requestId") &&
                lastRequest.containsKey("ADDR") &&
                lastRequest.get("PORT").equals(Integer.toString(PORT)) &&
                lastRequest.get("requestId").equals(requestId) &&
                lastRequest.get("ADDR").equals(ADDR);
    }

    private void sendResponse(Message responseMessage, DatagramPacket receivePacket) throws IOException {
        String responseStr = responseMessage.toJson();
        byte[] sendData = responseStr.getBytes("UTF-8");
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, receivePacket.getAddress(), receivePacket.getPort());
        serverSocket.send(sendPacket);
        System.out.println("Send: " + responseStr);
    }

    public static void main(String[] args) throws Exception {
        UDPServer server = new UDPServer(8080);
        System.out.println("UDP Server initialized. Waiting for connections...");
        while (true) {
            server.getRequest();
        }
    }
}