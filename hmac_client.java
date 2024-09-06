import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.*;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
public class hmac_client
{
  private static final String HMAC_ALGORITHM = "HmacMD5";
  public static void main (String[]args)
  {
	try
	{
	  byte[]key = generateKey ();

	  Socket socket = new Socket ("localhost", 12345);
	    System.out.println ("Connected to server " + socket.getRemoteSocketAddress ());

	  DataInputStream in = new DataInputStream (socket.getInputStream ());
	  DataOutputStream out = new DataOutputStream (socket.getOutputStream ());

	  BufferedReader reader = new BufferedReader (new InputStreamReader (System.in));
	    System.out.print ("Enter your message: ");
	  String message = reader.readLine ();

	    out.writeUTF (message);
	    System.out.println ("Message sent to server: " + message);

	  String messageFromServer = in.readUTF ();
	  String hmacFromServer = in.readUTF ();
	    System.out.println ("Message from server: " + messageFromServer);
	    System.out.println ("HMAC from server: " + hmacFromServer);

	  String calculatedHMAC = calculateHMAC (messageFromServer, key);
	    System.out.println ("Calculated HMAC: " + calculatedHMAC);

	  if (hmacFromServer.equals (calculatedHMAC))
		{
		  System.out.println ("HMAC verified. Server authenticated.");
		}
	  else
		{
		  System.out.println ("HMAC verification failed. Server not authenticated.");
		}
	  socket.close ();
	}
	catch (IOException | NoSuchAlgorithmException | InvalidKeyException e)
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
