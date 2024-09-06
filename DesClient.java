import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Scanner;

public class DesClient {

    public static void main(String[] args) {
        try {
            // Connect to the server
            Socket socket = new Socket("localhost", 12345);
            System.out.println("Connected to server");

            // Create input and output streams
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

            // Receive the DES key from the server
            int keyLength = dis.readInt();
            byte[] encodedKey = new byte[keyLength];
            dis.readFully(encodedKey);

            // Reconstruct the SecretKey from the encoded bytes
            SecretKey secretKey = new SecretKeySpec(encodedKey, "DES");

            // Create a DES cipher for encryption and decryption
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");

            // Create a Scanner for user input
            Scanner scanner = new Scanner(System.in);

            while (true) {
                // Receive encrypted message from server
                int length = dis.readInt();
                byte[] encryptedReceived = new byte[length];
                dis.readFully(encryptedReceived);

                // Decrypt the message
                cipher.init(Cipher.DECRYPT_MODE, secretKey);
                byte[] decryptedReceived = cipher.doFinal(encryptedReceived);
                String decryptedString = new String(decryptedReceived, StandardCharsets.UTF_8);
                System.out.println("Server (Encrypted): " + Base64.getEncoder().encodeToString(encryptedReceived));
                System.out.println("Server (Original): " + decryptedString);

                // Get user input and encrypt the message
                System.out.print("You: ");
                String message = scanner.nextLine();
                byte[] messageBytes = message.getBytes(StandardCharsets.UTF_8);
                cipher.init(Cipher.ENCRYPT_MODE, secretKey);
                byte[] encryptedMessage = cipher.doFinal(messageBytes);
                System.out.println("You (Encrypted): " + Base64.getEncoder().encodeToString(encryptedMessage));

                // Send the encrypted message to the server
                dos.writeInt(encryptedMessage.length);
                dos.write(encryptedMessage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}