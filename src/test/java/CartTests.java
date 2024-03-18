import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CartTests {
  private Cart cart;
  private Product product1;
  private Product product2;

  @BeforeEach
  void setUp() {
    cart = new CartBuilder().build();
    product1 = new Product("Product 1", 10.0, "Description 1", 10);
    product2 = new Product("Product 2", 20.0, "Description 2", 10);
  }

  @Test
  void testAddItem() {
    cart.addItem(product1, 1);
    assertEquals(1, cart.getItems().size(), "Cart should contain 1 item.");
    assertEquals(product1, cart.getItems().get(0).getProduct(), "The product in the cart should be Product 1.");
  }

  @Test
  void testRemoveItem() {
    cart.addItem(product1, 1);
    cart.removeItem(product1);
    assertTrue(cart.getItems().isEmpty(), "Cart should be empty after removal.");
  }

  @Test
  void testGetTotalPrice() {
    cart.addItem(product1, 2);
    cart.addItem(product2, 3);
    assertEquals(80.0, cart.getTotalPrice(), "Total price of the cart should be 80.0.");
  }

  @Test
  void testGetItems() {
    cart.addItem(product1, 1);
    cart.addItem(product2, 1);
    assertEquals(2, cart.getItems().size(), "Cart should contain 2 items.");
    assertTrue(cart.getItems().contains(new Item(product1, 1)), "Cart should contain Product 1.");
    assertTrue(cart.getItems().contains(new Item(product2, 1)), "Cart should contain Product 2.");
  }

  @Test
  void testClearItems() {
    cart.addItem(product1, 1);
    cart.addItem(product2, 1);
    cart.clearItems();
    assertTrue(cart.getItems().isEmpty(), "Cart should be empty after clearing items.");
  }

  @Test
  void testAddMultipleItems() {
    cart.addItem(product1, 2);
    cart.addItem(product2, 3);
    assertEquals(2, cart.getItems().size(), "Cart should contain 2 items.");
    assertEquals(80.0, cart.getTotalPrice(), "Total price should be 80.0.");
  }

  @Test
  void testRemoveNonExistentItem() {
    cart.addItem(product1, 1);
    cart.removeItem(product2);
    assertEquals(1, cart.getItems().size(), "Cart should still contain 1 item.");
  }

  @Test
  void testUpdateItemQuantity() {
    cart.addItem(product1, 1);
    cart.addItem(product1, 2);
    assertEquals(1, cart.getItems().size(), "Cart should contain 1 item.");
    assertEquals(3, cart.getItems().get(0).getQuantity(), "Quantity of Product 1 should be updated to 3.");
  }
}
