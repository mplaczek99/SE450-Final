import java.util.ArrayList;
import java.util.List;

public class Order {
  private final List<Item> items;
  private final double totalPrice;

  public Order(final List<Item> items) {
    this.items = items;
    this.totalPrice = calculateTotalPrice();
  }

  /**
   * Calculates the total price of all items in the order
   *
   * @return The total price of all items
   */
  private double calculateTotalPrice() {
    return items.stream().mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity()).sum();
  }

  /**
   * Returns the list of items in the order
   *
   * @return The list of items
   */
  public List<Item> getItems() {
    return items;
  }

  /**
   * Returns the total price of the order
   *
   * @return The total price
   */
  public double getTotalPrice() {
    return totalPrice;
  }
}

class OrderService {
  private final PaymentService paymentService;

  public OrderService(PaymentService paymentService) {
    this.paymentService = paymentService;
  }

  /**
   * Places an order
   *
   * @param cart The cart containing the items to be ordered
   * @return The order
   */
  // @Transactional
  public Order placeOrder(final Cart cart) {
    // Check if the cart is not empty
    if (cart.getItems().isEmpty()) {
      throw new IllegalStateException("Cannot place an order with an empty cart.");
    }

    // Process payment
    double totalPrice = cart.getTotalPrice();
    if (!paymentService.makePayment(totalPrice)) {
      throw new IllegalStateException("Payment failed.");
    }

    // Create and return the order
    List<Item> orderItems = new ArrayList<>(cart.getItems());
    Order order = new Order(orderItems);
    cart.clearItems(); // Clear the cart after placing the order
    return order;
  }

  /**
   * Generates an order summary
   *
   * @param order The order
   * @return The order summary
   */
  public String generateOrderSummary(final Order order) {
    StringBuilder summary = new StringBuilder("Order Summary:\n"); // StringBuilder...builds the String, amazing name
    for (Item item : order.getItems()) {
      summary.append(item.getProduct().getName())
          .append(" x ")
          .append(item.getQuantity())
          .append("\n");
    }
    return summary.append("Total Price: ").append(order.getTotalPrice()).toString();
  }
}
