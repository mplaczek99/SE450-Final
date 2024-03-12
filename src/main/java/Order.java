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
  public Order placeOrder(final Cart cart) {
    final List<Item> orderItems = new ArrayList<>(cart.getItems());
    final Order order = new Order(orderItems);
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
