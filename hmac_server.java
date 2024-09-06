import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.*;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
public class hmac_server
{
  private static final String HMAC_ALGORITHM = "HmacMD5";
  public static void main (String[]args)
  {
	try
	{
	  byte[]key = generateKey ();
	  System.out.println (key);

	  ServerSocket serverSocket = new ServerSocket (12345);
	    System.out.println ("Server waiting for client on port " + serverSocket.getLocalPort ());

	  Socket socket = serverSocket.accept ();
	    System.out.println ("Just connected to " + socket.getRemoteSocketAddress ());
	  BufferedReader reader = new BufferedReader (new InputStreamReader (System.in));
	    System.out.print ("Enter your message: ");
	  String messageFromUser = reader.readLine ();
	  String hmac = calculateHMAC (messageFromUser, key);

	  DataOutputStream out = new DataOutputStream (socket.getOutputStream ());
	    out.writeUTF (messageFromUser);
	    out.writeUTF (hmac);
	    System.out.println ("Message and HMAC sent to client.");

	    socket.close ();
	    serverSocket.close ();
	} catch (IOException | NoSuchAlgorithmException | InvalidKeyException e)
	{
	  e.printStackTrace ();
	}
  }
  private static byte[] generateKey ()
  {
	return "MySecretKey12345".getBytes ();
  }
  private static String calculateHMAC (String message, byte[]key) throws
	NoSuchAlgorithmException, InvalidKeyException
  {
	Mac mac = Mac.getInstance (HMAC_ALGORITHM);
	SecretKeySpec secretKeySpec = new SecretKeySpec (key, HMAC_ALGORITHM);
	mac.init (secretKeySpec);
	byte[]hmacBytes = mac.doFinal (message.getBytes ());
	return Base64.getEncoder ().encodeToString (hmacBytes);
  }
}
