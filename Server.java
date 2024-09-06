import java.io.*;
import java.net.*;
import java.util.*;
public class Server
{
  public static void main (String[]args)
  {
	try
	{
	  ServerSocket se = new ServerSocket (2000);
	    System.out.println ("Server is waiting for client ..... ");
	  Socket cl = se.accept ();
	    System.out.println ("Client connected ..... ");
	  BufferedReader in =
		new BufferedReader (new InputStreamReader (cl.getInputStream ()));
	  int p = Integer.parseInt (in.readLine ());
	  int q = Integer.parseInt (in.readLine ());
	  int n = p * q;
	  int phiN = (p - 1) * (q - 1);
	    System.out.println("Enter a value for e such that gcd(e, phi(n)) = 1 and 1 < e < " + phiN);
	  int e =Integer.
		parseInt (new BufferedReader (new InputStreamReader (System.in)).readLine ());
	  int d = modInverse (e, phiN);
	  PrintWriter out = new PrintWriter (cl.getOutputStream (), true);
	    out.println (e);
	    out.println (n);
	    System.out.println ("Enter the message M to get transferred:");
	  String message =
		new BufferedReader (new InputStreamReader (System.in)).readLine ();
	  int encryptedMessage = modPow (Integer.parseInt (message), e, n);
	    System.out.println ("Encrypted message: " + encryptedMessage);
	    out.println (encryptedMessage);
	    se.close ();
	} catch (IOException e)
	{
	  e.printStackTrace ();
	}
  }
  private static int modInverse (int a, int m)
  {
	for (int i = 1; i < m; i++)
	  {
		if ((a * i) % m == 1)
		  {
			return i;
		  }
	  }
	return -1;
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
