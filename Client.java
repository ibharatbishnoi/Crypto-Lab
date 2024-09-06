import java.io.*;
import java.net.*;
public class Client
{
  public static void main (String[]args)
  {
	try
	{
	  Socket so = new Socket ("127.0.0.1", 2000);
	  PrintWriter ou = new PrintWriter (so.getOutputStream (), true);
	    System.out.println ("p:");
	    ou.println (new BufferedReader (new InputStreamReader (System.in)).
					readLine ());
	    System.out.println ("q:");
	    ou.println (new BufferedReader (new InputStreamReader (System.in)).
					readLine ());
	  BufferedReader in =
		new BufferedReader (new InputStreamReader (so.getInputStream ()));
	  int e = Integer.parseInt (in.readLine ());
	  int n = Integer.parseInt (in.readLine ());
	    System.out.println ("Received public key (e, n): {" + e + ", " + n +
							"}");
	  int em = Integer.parseInt (in.readLine ());
	    System.out.println ("Received encrypted message:" + em);
	    System.out.
		println
		("Enter the private key (d) such that (e * d) % phi(n) = 1 and 1 < d < n:");
	  int d =
		Integer.
		parseInt (new BufferedReader (new InputStreamReader (System.in)).
				  readLine ());
	  int dm = modPow (em, d, n);
	    System.out.println ("Decrypted message:" + dm);
	    so.close ();
	} catch (IOException e)
	{
	  e.printStackTrace ();
	}
  }
  private static int modPow (int base, int exponent, int modulus)
  {
	int result = 1;
	while (exponent > 0)
	  {
		if (exponent % 2 == 1)
		  {
			result = (result * base) % modulus;
		  }
		base = (base * base) % modulus;
		exponent /= 2;
	  }
	return result;
  }
}
