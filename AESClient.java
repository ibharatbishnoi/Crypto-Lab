import java.io.*;
import java.net.*;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.KeyGenerator;
import java.util.Base64;

public class Client {
    public static void main(String[] args) {
        try {
            // Create client socket
            Socket socket = new Socket("localhost", 12345);
            System.out.println("##21BCE5218##");
            System.out.println("Connected to server on port 12345...");
            // Set up input and output streams
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            // Generate an AES key
            SecretKey secretKey = AESUtil.generateSecretKey();
            String keyString = Base64.getEncoder().encodeToString(secretKey.getEncoded());
            // Send the AES key to the server
            writer.println(keyString);
            System.out.println("AES key generated: " + keyString);
            // Start user input for plaintext message
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                System.out.print("Enter plaintext message (or type 'exit' to quit): ");
                String plaintext = userInput.readLine();
                if (plaintext.equalsIgnoreCase("exit")) {
                    break;
                }
                // Encrypt the client's message using AES
                String encryptedMessage = AESUtil.encrypt(plaintext, secretKey);
                System.out.println("Encrypted Message: " + encryptedMessage);
                // Send encrypted message to server
                writer.println(encryptedMessage);
            }
            // Close the connections
            userInput.close();
            reader.close();
            writer.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}