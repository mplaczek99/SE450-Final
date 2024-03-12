import java.util.ArrayList;
import java.util.List;

public class Order {
  private final List<Item> items;
  private final double totalPrice;

  public Order(final List<Item> items) {
    this.items = items;
    this.totalPrice = calculateTotalPrice();
  }

  private double calculateTotalPrice() {
    return items.stream().mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity()).sum();
  }

  public List<Item> getItems() {
    return items;
  }

  public double getTotalPrice() {
    return totalPrice;
  }
}

class OrderService {
  private final PaymentService paymentService;

  public OrderService(PaymentService paymentService) {
    this.paymentService = paymentService;
  }

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

  public String generateOrderSummary(final Order order) {
    StringBuilder summary = new StringBuilder("Order Summary:\n");
    for (Item item : order.getItems()) {
      summary.append(item.getProduct().getName())
          .append(" x ")
          .append(item.getQuantity())
          .append("\n");
    }
    return summary.append("Total Price: ").append(order.getTotalPrice()).toString();
  }
}
