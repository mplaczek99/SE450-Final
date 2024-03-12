import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class OrderProcessingTests {
  private CartBuilder cartBuilder;
  private Cart cart;
  private Product product1;
  private Product product2;
  private OrderService orderService;

  @BeforeEach
  void setUp() {
    cartBuilder = new CartBuilder();
    cart = cartBuilder.build();
    product1 = new Product("Product 1", 10.0, "Description 1");
    product2 = new Product("Product 2", 20.0, "Description 2");
    cartBuilder.addItem(product1, 2).addItem(product2, 3);
    cart = cartBuilder.build();
    orderService = new OrderService();
  }

  @Test
  void testPlaceOrder() {
    Order order = orderService.placeOrder(cart);
    assertEquals(80.0, order.getTotalPrice(), "Total price of the order should be 80.0.");
    assertTrue(cart.getItems().isEmpty(), "Cart should be empty after placing the order.");
  }

  @Test
  void testGenerateOrderSummary() {
    Order order = orderService.placeOrder(cart);
    String summary = orderService.generateOrderSummary(order);
    assertTrue(summary.contains("Product 1 x 2"), "Order summary should contain Product 1.");
    assertTrue(summary.contains("Product 2 x 3"), "Order summary should contain Product 2.");
    assertTrue(summary.contains("Total Price: 80.0"), "Order summary should contain total price.");
  }
}
