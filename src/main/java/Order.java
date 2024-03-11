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
