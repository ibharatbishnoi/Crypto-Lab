import java.io.*;
import java.math.BigInteger;
import java.net.*;
import java.util.Scanner;
public class Bharat {
    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        try {
            ServerSocket serverSocket = new ServerSocket(9999);
            System.out.println("Waiting for connection from UserOne...");
            Socket socket = serverSocket.accept();
            System.out.println("Connection established with UserOne.");
            DataInputStream input = new DataInputStream(socket.getInputStream());
            DataOutputStream output = new DataOutputStream(socket.getOutputStream());
            BigInteger q = new BigInteger(input.readUTF());
            int alpha = input.readInt();
            System.out.println("Private Number XB (<q): ");
            BigInteger xb = sc.nextBigInteger();

            BigInteger alphaBigInt = BigInteger.valueOf(alpha);
            BigInteger yb = alphaBigInt.modPow(xb, q);
            System.out.println("Public key yb: " + yb);
            BigInteger ya = new BigInteger(input.readUTF());
            System.out.println("Received public key (ya) from UserOne: " + ya);
            output.writeInt(yb.intValue());
            output.flush();
            BigInteger secret = ya.modPow(xb, q);
            System.out.println("Shared secret: " + secret);
            input.close();
            output.close();
            socket.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}