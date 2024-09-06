import java.io.*;
import java.net.*;

public class server {
    public static void main(String[] args) {
        try {
            // Create server socket
            ServerSocket serverSocket = new ServerSocket(1500);
            System.out.println("21BCE5218");
            System.out.println("Server waiting for client on port 1500...");

            // Accept client connection
            Socket clientSocket = serverSocket.accept();
            System.out.println("Connection established with " + clientSocket);

            // Set up input and output streams
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);

            String key = reader.readLine();

            // Create VigenereCipher instance with a key
            VigenereCipher vigenerecipher = new VigenereCipher(key);

            // Start listening for client messages
            String clientMessage;
            while ((clientMessage = reader.readLine()) != null) {
                // Decrypt the client's message using VigenereCipher
                String decryptedMessage = vigenerecipher.decrypt(clientMessage);

                // Display the encrypted message on the server's terminal
                System.out.println("Received from client: " + clientMessage);
                System.out.println("Decrypted message: " + decryptedMessage);

                // Send the decrypted message back to the client
                // writer.println(decryptedMessage);
            }

            // Close the connections
            reader.close();
            writer.close();
            clientSocket.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
