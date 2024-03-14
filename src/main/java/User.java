import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class User {
  private final String username;
  private final String hashedPassword;
  private final Cart cart;

  public User(final String username, final String password) {
    this.username = username;
    this.hashedPassword = PasswordUtil.hashPassword(password);
    cart = Cart.getInstance();
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

  public void registerUser(final String username, final String password) {
    users.put(username, new User(username, password));
  }

  public User authenticate(final String username, final String password) {
    final User user = users.get(username);
    return (user != null && user.getHashedPassword().equals(PasswordUtil.hashPassword(password))) ? user : null;
  }
}

class PasswordUtil {
  public static String hashPassword(final String password) {
    try {
      final MessageDigest digest = MessageDigest.getInstance("SHA-256");
      final byte[] hash = digest.digest(password.getBytes());
      final StringBuilder hexString = new StringBuilder();
      for (final byte b : hash) {
        final String hex = Integer.toHexString(0xff & b);
        hexString.append(hex.length() == 1 ? "0" : "").append(hex);
      }
      return hexString.toString();
    } catch (final NoSuchAlgorithmException e) {
      throw new RuntimeException("SHA-256 hashing algorithm not found");
    }
  }
}
