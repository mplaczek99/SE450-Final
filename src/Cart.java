import java.util.ArrayList;
import java.util.List;

public class Cart {
  private final List<Item> items = new ArrayList<>();

  public void addItem(Product product, int quantity) {
    items.stream()
        .filter(item -> item.getProduct().equals(product))
        .findFirst()
        .ifPresentOrElse(
            item -> item.setQuantity(item.getQuantity() + quantity),
            () -> items.add(new Item(product, quantity)));
  }

  public void removeItem(Product product) {
    items.removeIf(item -> item.getProduct().equals(product));
  }

  public double getTotalPrice() {
    return items.stream()
        .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
        .sum();
  }

  public List<Item> getItems() {
    return new ArrayList<>(items);
  }
}
