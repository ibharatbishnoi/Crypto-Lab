import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Scanner;

public class DesServer {

    public static void main(String[] args) {
        try {
            // Generate a DES key
            SecretKey secretKey = KeyGenerator.getInstance("DES").generateKey();

            // Initialize server socket
            ServerSocket serverSocket = new ServerSocket(12345);
            System.out.println("Server waiting for client on port 12345");

            // Accept client connection
            Socket socket = serverSocket.accept();
            System.out.println("Client connected");

            // Create input and output streams
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

            // Send the DES key to the client
            byte[] encodedKey = secretKey.getEncoded();
            dos.writeInt(encodedKey.length);
            dos.write(encodedKey);

            // Create a DES cipher for encryption and decryption
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");

            // Create a Scanner for user input
            Scanner scanner = new Scanner(System.in);

            while (true) {
                // Get user input and encrypt the message
                System.out.print("You: ");
                String message = scanner.nextLine();
                byte[] messageBytes = message.getBytes(StandardCharsets.UTF_8);
                cipher.init(Cipher.ENCRYPT_MODE, secretKey);
                byte[] encryptedMessage = cipher.doFinal(messageBytes);
                System.out.println("You (Encrypted): " + Base64.getEncoder().encodeToString(encryptedMessage));

                // Send the encrypted message to the client
                dos.writeInt(encryptedMessage.length);
                dos.write(encryptedMessage);

                // Receive encrypted message from client
                int length = dis.readInt();
                byte[] encryptedReceived = new byte[length];
                dis.readFully(encryptedReceived);

                // Decrypt the message
                cipher.init(Cipher.DECRYPT_MODE, secretKey);
                byte[] decryptedReceived = cipher.doFinal(encryptedReceived);
                String decryptedString = new String(decryptedReceived, StandardCharsets.UTF_8);
                System.out.println("Client (Original): " + decryptedString);
                System.out.println("Client (Encrypted): " + Base64.getEncoder().encodeToString(encryptedReceived));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
