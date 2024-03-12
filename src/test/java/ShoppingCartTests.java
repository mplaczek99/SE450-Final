import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ShoppingCartTests {
  private CartBuilder cartBuilder;
  private Cart cart;
  private Product product1;
  private Product product2;

  @BeforeEach
  void setUp() {
    cartBuilder = new CartBuilder();
    cart = cartBuilder.build();
    product1 = new Product("Product 1", 10.0, "Description 1");
    product2 = new Product("Product 2", 20.0, "Description 2");
  }

  @Test
  void testAddItem() {
    cartBuilder.addItem(product1, 1);
    cart = cartBuilder.build();
    assertEquals(1, cart.getItems().size(), "Cart should contain 1 item.");
  }

  @Test
  void testRemoveItem() {
    cartBuilder.addItem(product1, 1);
    cart = cartBuilder.build();
    cartBuilder.removeItem(product1);
    cart = cartBuilder.build();
    assertTrue(cart.getItems().isEmpty(), "Cart should be empty after removal.");
  }

  @Test
  void testGetTotalPrice() {
    cartBuilder.addItem(product1, 2).addItem(product2, 3);
    cart = cartBuilder.build();
    assertEquals(80.0, cart.getTotalPrice(), "Total price of the cart should be 80.0.");
  }
}
