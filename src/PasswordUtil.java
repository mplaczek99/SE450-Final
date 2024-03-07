import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordUtil {
  public static String hashPassword(String password) {
    try {
      MessageDigest digest = MessageDigest.getInstance("SHA-256");
      byte[] hash = digest.digest(password.getBytes());
      StringBuilder hexString = new StringBuilder();
      for (byte b : hash) {
        String hex = Integer.toHexString(0xff & b);
        hexString.append(hex.length() == 1 ? "0" : "").append(hex);
      }
      return hexString.toString();
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException("SHA-256 hashing algorithm not found");
    }
  }
}
