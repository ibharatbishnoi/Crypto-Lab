import java.io.*;
import java.net.*;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class Server {
    public static void main(String[] args) {
        try {
            // Create server socket
            System.out.println("##21BCE5218##");
            ServerSocket serverSocket = new ServerSocket(12345);
            System.out.println("Server waiting for client on port 12345...");
            // Accept client connection
            Socket clientSocket = serverSocket.accept();
            System.out.println("Connection established with " + clientSocket);
            // Set up input and output streams
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
            // Receive the AES key from the client
            String keyString = reader.readLine();
            System.out.println("AES key received: " + keyString);
            byte[] keyBytes = Base64.getDecoder().decode(keyString);
            SecretKey secretKey = new SecretKeySpec(keyBytes, "AES");
            // Start listening for client messages
            while (true) {
                String clientMessage = reader.readLine();
                if (clientMessage == null) {
                    break;
                }
                // Decrypt the client's message using AES
                String decryptedMessage = AESUtil.decrypt(clientMessage, secretKey);
                // Display the decrypted message on the server's terminal
                System.out.println("Received from client (encrypted): " + clientMessage);
                System.out.println("Decrypted message: " + decryptedMessage);
            }
            // Close the connections
            reader.close();
            writer.close();
            clientSocket.close();
            serverSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}