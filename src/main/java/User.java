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
