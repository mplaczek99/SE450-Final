import java.util.ArrayList;
import java.util.List;

public class OrderService {
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
