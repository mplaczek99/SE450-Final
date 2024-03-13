import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class OrderTests {
  private Cart cart;
  private Product product1;
  private Product product2;
  private OrderService orderService;
  private Order order;

  @Mock
  private PaymentService mockPaymentService;

  @BeforeEach
  void setUp() {
    cart = new CartBuilder().build();
    cart.clearItems(); // Clears the cart before adding items
    product1 = new Product("Product 1", 10.0, "Description 1");
    product2 = new Product("Product 2", 20.0, "Description 2");
    cart.addItem(product1, 2);
    cart.addItem(product2, 3);

    lenient().when(mockPaymentService.makePayment(anyDouble())).thenReturn(true);
    orderService = new OrderService(mockPaymentService);

    List<Item> items = new ArrayList<>();
    items.add(new Item(product1, 2));
    items.add(new Item(product2, 3));
    order = new Order(items);
  }

  @Test
  void testPlaceOrderWithSuccessfulPayment() {
    Order placedOrder = orderService.placeOrder(cart);
    assertEquals(80.0, placedOrder.getTotalPrice(), "Total price of the order should be 80.0.");
    assertTrue(cart.getItems().isEmpty(), "Cart should be empty after placing the order.");
  }

  @Test
  void testGenerateOrderSummary() {
    String summary = orderService.generateOrderSummary(order);
    assertTrue(summary.contains("Product 1 x 2"), "Order summary should contain Product 1.");
    // $20
    assertTrue(summary.contains("Product 2 x 3"), "Order summary should contain Product 2.");
    // $60
    assertTrue(summary.contains("Total Price: 80.0"), "Order summary should contain total price.");
  }

  @Test
  void testOrderTotalPriceIsCorrect() {
    assertEquals(80.0, order.getTotalPrice(), "Total price of the order should be 80.0.");
  }

  @Test
  void testOrderItemsAreCorrect() {
    assertEquals(2, order.getItems().size(), "Order should contain 2 items.");
    assertEquals(product1, order.getItems().get(0).getProduct(), "First item should be Product 1.");
    assertEquals(2, order.getItems().get(0).getQuantity(), "Quantity of Product 1 should be 2.");
  }

  @Test
  void testPlaceOrderWithEmptyCart() {
    Cart emptyCart = new CartBuilder().build();
    assertThrows(IllegalStateException.class, () -> orderService.placeOrder(emptyCart),
        "Placing an order with an empty cart should throw an exception.");
  }

  @Test
  void testPlaceOrderWithFailedPayment() {
    when(mockPaymentService.makePayment(anyDouble())).thenReturn(false);
    assertThrows(IllegalStateException.class, () -> orderService.placeOrder(cart),
        "Placing an order with failed payment should throw an exception.");
  }
}
