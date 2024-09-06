import javax.net.ssl.*;
import java.io.*;
import java.security.KeyStore;

public class SSLClient {
    public static void main(String[] args) throws Exception {
        String serverAddress = "localhost";
        int serverPort = 9999;

        // Load keystore
        char[] password = "password".toCharArray();
        KeyStore keyStore = KeyStore.getInstance("JKS");
        keyStore.load(new FileInputStream("keystore.jks"), password);

        // Initialize TrustManagerFactory
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(keyStore);

        // Initialize SSLContext
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, trustManagerFactory.getTrustManagers(), null);

        // Create SSLSocketFactory
        SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

        // Create SSLSocket
        SSLSocket sslSocket = (SSLSocket) sslSocketFactory.createSocket(serverAddress, serverPort);
        System.out.println("Connected to server.");

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter writer = new PrintWriter(sslSocket.getOutputStream(), true);

        // Start reading user input and sending messages to the server
        String message;
        while ((message = reader.readLine()) != null) {
            writer.println(message);
            System.out.println("Server: " + reader.readLine());
        }

        sslSocket.close();
    }
}