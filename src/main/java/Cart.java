import java.util.ArrayList;
import java.util.List;

public class Cart {
  private static Cart instance;
  private final List<Item> items = new ArrayList<>();

  private Cart() {
  } // I am not sure if this is necessary or not

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

  // Will create a new instance of the Cart
  public static Cart getInstance() {
    if (instance == null) {
      instance = new Cart();
    }
    return instance;
  }

  public double getTotalPrice() {
    return items.stream()
        .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
        .sum();
  }

  public List<Item> getItems() {
    return new ArrayList<>(items);
  }

  public void clearItems() {
    items.clear();
  }
}
