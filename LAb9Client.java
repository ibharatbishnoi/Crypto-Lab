import java.io.*;
import java.net.*;
import java.security.*;
import java.util.Base64;
public class Client
{
  public static void main (String[]args) throws Exception
  {
	Socket socket = new Socket ("localhost", 12345);
// Initialize input and output streams
	ObjectOutputStream outputStream = new ObjectOutputStream (socket.getOutputStream ());
	ObjectInputStream inputStream = new ObjectInputStream (socket.getInputStream ());
// Generate key pair
	KeyPair keyPair = generateKeyPair ();
// Send public key to server
	  outputStream.writeObject (keyPair.getPublic ());
// Receive server's public key
	PublicKey serverPublicKey = (PublicKey) inputStream.readObject ();
	BufferedReader br = new BufferedReader (new InputStreamReader (System.in));
	while (true)
	  {
// Read message from user
		System.out.print ("Client: ");
		String message = br.readLine ();
// Sign message with private key
		  byte[] signature = sign (message, keyPair.getPrivate ());
// Print message and signature
		  System.out.println ("Client Message: " + message);
		  System.out.println ("Client Signature: " + Base64.getEncoder ().encodeToString (signature));
// Send message and signature to server
		  outputStream.writeObject (message);
		  outputStream.writeObject (signature);
// Receive server's response
		  message = (String) inputStream.readObject ();
		  signature = (byte[])inputStream.readObject ();
// Verify server's signature
		boolean verified = verify (message, signature, serverPublicKey);
// Print server's response and signature
		  System.out.println ("Server: " + message + " (Signature verified: " + verified + ")");
		  System.out.println ("Server Signature: " + Base64.getEncoder ().encodeToString (signature));
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
