import java.util.ArrayList;
import java.util.List;

public class Cart {
  private final List<Item> items = new ArrayList<>();

  private Cart() {
  }

  public void addItem(final Product product, final int quantity) {
    items.stream()
        .filter(item -> item.getProduct().equals(product))
        .findFirst()
        .ifPresentOrElse(
            item -> item.setQuantity(item.getQuantity() + quantity),
            () -> items.add(new Item(product, quantity)));
  }

  public void removeItem(final Product product) {
    items.removeIf(item -> item.getProduct().equals(product));
  }

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

  private static class CartHolder {
    private static final Cart INSTANCE = new Cart();
  }
}

class CartBuilder {
  private final Cart cart;

  public CartBuilder() {
    this.cart = Cart.getInstance();
    this.cart.clearItems(); // Ensure the cart is empty before building
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
