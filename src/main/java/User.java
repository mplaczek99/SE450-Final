import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class User {
  private final String username;
  private final String hashedPassword;
  private final Cart cart = Cart.getInstance();

  public User(String username, String password) {
    this.username = username;
    this.hashedPassword = PasswordUtil.hashPassword(password);
  }

  public String getUsername() {
    return username;
  }

  public String getHashedPassword() {
    return hashedPassword;
  }

  public Cart getCart() {
    return cart;
  }
}

class AuthenticationService {
  private final Map<String, User> users = new HashMap<>();

  public void registerUser(String username, String password) {
    users.put(username, new User(username, password));
  }

  public User authenticate(String username, String password) {
    User user = users.get(username);
    return (user != null && user.getHashedPassword().equals(PasswordUtil.hashPassword(password))) ? user : null;
  }
}

class PasswordUtil {
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
