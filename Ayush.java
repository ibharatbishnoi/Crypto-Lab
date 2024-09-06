import java.io. *;
import java.math.BigInteger;
import java.net. *;
import java.util.Scanner;
public class Ayush
{
  public static void main (String args[])
  {
	Scanner sc = new Scanner (System.in);
	  System.out.println ("Prime number q: ");
	BigInteger q = sc.nextBigInteger ();
	int alpha = PrimitiveRoot.findPrimitive (q.intValue ());
	  System.out.println ("Primitive Root: " + alpha);
	  System.out.println ("Private Number XA (<q): ");
	BigInteger xa = sc.nextBigInteger ();
	BigInteger alphaBigInt = BigInteger.valueOf (alpha);
	BigInteger ya = alphaBigInt.modPow (xa, q);
	  System.out.println ("Public key (ya): " + ya);
	  try
	{
	  Socket socket = new Socket ("localhost", 9999);
	  DataInputStream input = new DataInputStream (socket.getInputStream ());
	  DataOutputStream output = new
		DataOutputStream (socket.getOutputStream ());
	    output.writeUTF (q.toString ());
	    output.writeInt (alpha);
	    output.writeUTF (ya.toString ());
	    output.flush ();
	  int yb = input.readInt ();
	    System.out.println ("Received public key (yb) from UserTwo: " + yb);
	  BigInteger secret = BigInteger.valueOf (yb).modPow (xa, q);
	    System.out.println ("Shared secret: " + secret);
	    input.close ();
	    output.close ();
	    socket.close ();
	} catch (IOException e)
	{
	  e.printStackTrace ();
	}
  }
}
