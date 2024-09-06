import java.io.*;
import java.net.*;
import java.security.*;
import java.util.Base64;
public class Server
{
  public static void main (String[]args) throws Exception
  {
	ServerSocket serverSocket = new ServerSocket (12345);
	  System.out.println ("Server started. Waiting for clients...");
	Socket socket = serverSocket.accept ();
	  System.out.println ("Client connected.");
// Initialize input and output streams
	ObjectInputStream inputStream = new ObjectInputStream (socket.getInputStream ());
	ObjectOutputStream outputStream = new ObjectOutputStream (socket.getOutputStream ());
// Generate key pair
	KeyPair keyPair = generateKeyPair ();
// Receive client's public key
	PublicKey clientPublicKey = (PublicKey) inputStream.readObject ();
// Send server's public key to client
	  outputStream.writeObject (keyPair.getPublic ());
	BufferedReader br = new BufferedReader (new InputStreamReader (System.in));
	while (true)
	  {
// Receive message and signature from client
		String message = (String) inputStream.readObject ();
		  byte[] signature = (byte[])inputStream.readObject ();
// Verify client's signature
		boolean verified = verify (message, signature, clientPublicKey);
// Print client's message and signature
		  System.out.println ("Client: " + message + " (Signature verified: " + verified + ")");
		  System.out.println ("Client Signature: " + Base64.getEncoder ().encodeToString (signature));
// Read server's response from console
		  System.out.print ("Server: ");
		  message = br.readLine ();
// Sign message with private key
		  signature = sign (message, keyPair.getPrivate ());
// Print server's message and signature
		  System.out.println ("Server Message: " + message);
		  System.out.println ("Server Signature: " + Base64.getEncoder ().encodeToString (signature));
// Send server's response and signature to client
		  outputStream.writeObject (message);
		  outputStream.writeObject (signature);
	  }
  }
// Generate RSA key pair
  public static KeyPair generateKeyPair () throws Exception
  {
	KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance ("RSA");
	  keyPairGenerator.initialize (2048);
	  return keyPairGenerator.generateKeyPair ();
  }
// Sign a message with private key
  public static byte[] sign (String message, PrivateKey privateKey) throws Exception
  {
	Signature signature = Signature.getInstance ("SHA1withRSA");
	  signature.initSign (privateKey);
	  signature.update (message.getBytes ());
	  return signature.sign ();
  }
// Verify signature with public key
  public static boolean verify (String message, byte[]signature, PublicKey publicKey) throws Exception
  {
	Signature verifier = Signature.getInstance ("SHA1withRSA");
	  verifier.initVerify (publicKey);
	  verifier.update (message.getBytes ());
	  return verifier.verify (signature);
  }
}
