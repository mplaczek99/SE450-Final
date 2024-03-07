public class User {
  private final String username;
  private final String hashedPassword;
  private final Cart cart;

  public User(String username, String password) {
    this.username = username;
    this.hashedPassword = PasswordUtil.hashPassword(password);
    this.cart = new Cart();
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
