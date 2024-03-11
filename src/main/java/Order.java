import java.util.ArrayList;
import java.util.List;

public class Order {
  private final List<Item> items;
  private final double totalPrice;

  public Order(List<Item> items) {
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
  public Order placeOrder(Cart cart) {
    List<Item> orderItems = new ArrayList<>(cart.getItems());
    Order order = new Order(orderItems);
    cart.clearItems(); // Clear the cart after placing the order
    return order;
  }

  public String generateOrderSummary(Order order) {
    StringBuilder summary = new StringBuilder();
    summary.append("Order Summary:\n");
    for (Item item : order.getItems()) {
      summary.append(item.getProduct().getName()).append(" x ").append(item.getQuantity()).append("\n");
    }
    summary.append("Total Price: ").append(order.getTotalPrice());
    return summary.toString();
  }
}
