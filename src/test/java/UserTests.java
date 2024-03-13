import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserTests {
  private AuthenticationService authService;
  private User user1, user2;
  private Product product1, product2;

  @BeforeEach
  void setUp() {
    authService = new AuthenticationService();
    user1 = new User("user1", "password1");
    user2 = new User("user2", "password2");
    product1 = new Product("Product 1", 10.0, "Description 1");
    product2 = new Product("Product 2", 20.0, "Description 2");

    authService.registerUser(user1.getUsername(), "password1");
    authService.registerUser(user2.getUsername(), "password2");
  }

  @Test
  void testUserRegistration() {
    User newUser = new User("newUser", "newPassword");
    authService.registerUser(newUser.getUsername(), "newPassword");
    assertNotNull(authService.authenticate(newUser.getUsername(), "newPassword"),
        "New user should be registered successfully.");
  }

  @Test
  void testUserAuthenticationSuccess() {
    User authenticatedUser = authService.authenticate("user1", "password1");
    assertNotNull(authenticatedUser, "User should be authenticated successfully.");
  }

  @Test
  void testUserAuthenticationFailure() {
    User authenticatedUser = authService.authenticate("user1", "wrongpassword");
    assertNull(authenticatedUser, "User should not be authenticated with incorrect password.");
  }

  @Test
  void testUserCartInteraction() {
    user1.getCart().addItem(product1, 1);
    user1.getCart().addItem(product2, 2);
    assertEquals(2, user1.getCart().getItems().size(), "User's cart should contain 2 items.");
    assertEquals(product1, user1.getCart().getItems().get(0).getProduct(),
        "The first product in the user's cart should be Product 1.");
    assertEquals(product2, user1.getCart().getItems().get(1).getProduct(),
        "The second product in the user's cart should be Product 2.");
  }

  @Test
  void testUserCartClear() {
    user2.getCart().addItem(product1, 3);
    user2.getCart().clearItems();
    assertTrue(user2.getCart().getItems().isEmpty(), "User's cart should be empty after clearing items.");
  }
}
