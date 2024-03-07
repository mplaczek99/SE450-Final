public class AuthenticationService {
  private Map<String, User> users = new HashMap<>();

  public void registerUser(String username, String password) throws NoSuchAlgorithmException {
    users.put(username, new User(username, password));
  }

  public boolean authenticate(String username, String password) throws NoSuchAlgorithmException {
    User user = users.get(username);
    if (user == null) {
      return false;
    }
    String hashedPassword = user.hashPassword(password);
    return user.getHashedPassword().equals(hashedPassword);
  }
}
