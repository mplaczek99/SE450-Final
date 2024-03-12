import java.util.ArrayList;
import java.util.List;

public class Cart {
  private final List<Item> items = new ArrayList<>();

  private Cart() {
  } // I am not sure if this is necessary or not

  public void addItem(final Product product, final int quantity) {
    items.stream()
        .filter(item -> item.getProduct().equals(product))
        .findFirst()
        .ifPresent(item -> item.setQuantity(item.getQuantity() + quantity));

    if (items.stream().noneMatch(item -> item.getProduct().equals(product))) {
      items.add(new Item(product, quantity));
    }
  }

  public void removeItem(final Product product) {
    items.removeIf(item -> item.getProduct().equals(product));
  }

  // Will create a new instance of the Cart
  public static Cart getInstance() {
    return CartHolder.INSTANCE;
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

  /**
   * Something, something thread-safety, I don't understand why entirely
   */
  private static class CartHolder {
    private static final Cart INSTANCE = new Cart();
  }
}

class CartBuilder {
  private Cart cart;

  public CartBuilder() {
    this.cart = Cart.getInstance();
  }

  public CartBuilder addItem(final Product product, final int quantity) {
    cart.addItem(product, quantity);
    return this;
  }

  public CartBuilder removeItem(final Product product) {
    cart.removeItem(product);
    return this;
  }

  public Cart build() {
    return cart;
  }
}
