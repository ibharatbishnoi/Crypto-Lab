import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class Lab8{
   private static final String HMAC_SHA1_ALGORITHM = "HmacSHA1";
   private static final String HMAC_SHA256_ALGORITHM = "HmacSHA256";

   public static void main(String[] args) {
       Scanner scanner = new Scanner(System.in);

       System.out.print("Enter the first message: ");
       String message1 = scanner.nextLine();

       System.out.print("Enter the first secret key: ");
       String key1 = scanner.nextLine();

       System.out.print("Enter the second message: ");
       String message2 = scanner.nextLine();

       System.out.print("Enter the second secret key: ");
       String key2 = scanner.nextLine();

       try {
           // Calculate HMAC for the first input
           long startTime = System.nanoTime();
           byte[] hmacSHA1_1 = generateHMAC(message1, key1, HMAC_SHA1_ALGORITHM);
           long endTime = System.nanoTime();
           long durationSHA1_1 = endTime - startTime;

           startTime = System.nanoTime();
           byte[] hmacSHA256_1 = generateHMAC(message1, key1, HMAC_SHA256_ALGORITHM);
           endTime = System.nanoTime();
           long durationSHA256_1 = endTime - startTime;

           // Calculate HMAC for the second input
           startTime = System.nanoTime();
           byte[] hmacSHA1_2 = generateHMAC(message2, key2, HMAC_SHA1_ALGORITHM);
           endTime = System.nanoTime();
           long durationSHA1_2 = endTime - startTime;

           startTime = System.nanoTime();
           byte[] hmacSHA256_2 = generateHMAC(message2, key2, HMAC_SHA256_ALGORITHM);
           endTime = System.nanoTime();
           long durationSHA256_2 = endTime - startTime;

           // Print the results
           System.out.println("HMAC SHA-1 for Message 1: " + durationSHA1_1 + " nanoseconds");
           System.out.println("HMAC SHA-256 for Message 1: " + durationSHA256_1 + " nanoseconds");
           System.out.println("HMAC SHA-1 for Message 2: " + durationSHA1_2 + " nanoseconds");
           System.out.println("HMAC SHA-256 for Message 2: " + durationSHA256_2 + " nanoseconds");
       } catch (NoSuchAlgorithmException | InvalidKeyException e) {
           e.printStackTrace();
       }

       scanner.close();
   }

   private static byte[] generateHMAC(String message, String key, String algorithm)
           throws NoSuchAlgorithmException, InvalidKeyException {
       Mac mac = Mac.getInstance(algorithm);
       SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), algorithm);
       mac.init(secretKeySpec);
       return mac.doFinal(message.getBytes());
   }
}
