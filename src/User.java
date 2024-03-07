import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

/**
 * This class implements hashed passwords instead of storing them as plaintext
 **/
public class User {
  private String username;
  private String hashedPassword;

  public User(String username, String password) throws NoSuchAlgorithmException {
    this.username = username;
    this.hashedPassword = hashPassword(password);
  }

  public String hashPassword(String password) throws NoSuchAlgorithmException {
    MessageDigest digest = MessageDigest.getInstance("SHA-256");
    byte[] encodedhash = digest.digest(password.getBytes());
    StringBuilder hexString = new StringBuilder(2 * encodedhash.length);
    for (int i = 0; i < encodedhash.length; i++) {
      String hex = Integer.toHexString(0xff & encodedhash[i]);
      if (hex.length() == 1) {
        hexString.append('0');
      }
      hexString.append(hex);
    }
    return hexString.toString();
  }

  // Getters and setters
  public String getUsername() {
    return username;
  }

  public String getHashedPassword() {
    return hashedPassword;
  }
}
