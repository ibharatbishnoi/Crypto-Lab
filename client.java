import java.io.*;
import java.net.*;

public class client {
    public static void main(String[] args) {
        try {
            // Create client socket
            Socket socket = new Socket("localhost", 1500);
            System.out.println("21BCE5218-Lab:2");
            System.out.println("Connected to server on port 1500...");

            // Set up input and output streams
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

            // Get user input for Vigenere Cipher key
            System.out.print("Enter Vigenere Cipher key: ");
            String key = new BufferedReader(new InputStreamReader(System.in)).readLine();
            writer.println(key);

            // Create VigenereCipher instance with the provided key
            VigenereCipher vigenereCipher = new VigenereCipher(key);

            // Get user input for plaintext message
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                System.out.print("Enter plaintext message: ");
                String plaintext = userInput.readLine();
                if (plaintext.equalsIgnoreCase("exit")) {
                    break;
                }

                // Encrypt the client's message using Vigenere Cipher
                String encryptedMessage = vigenereCipher.encrypt(plaintext);
                System.out.println("Encrypted Message:" + encryptedMessage);

                // Send plaintext message to server
                writer.println(encryptedMessage);

                // Receive and display the encrypted message from the server
                // String encryptedMessageFromServer = reader.readLine();
                // System.out.println("Encrypted message from server: " + encryptedMessageFromServer);
            }

            // Close the connections
            userInput.close();
            reader.close();
            writer.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
