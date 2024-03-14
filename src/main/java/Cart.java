import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Cart {
  private static Cart instance;
  private final List<Item> items;
  private final Logger LOGGER;

  private Cart() {
    items = new ArrayList<>();
    LOGGER = Logger.getLogger(Cart.class.getName());
  }

  public void addItem(final Product product, final int quantity) {
    items.stream()
        .filter(item -> item.getProduct().equals(product))
        .findFirst()
        .ifPresentOrElse(
            item -> {
              item.setQuantity(item.getQuantity() + quantity);
              LOGGER.info("Updated quantity of " + product.getName() + " to " + item.getQuantity());
            },
            () -> {
              items.add(new Item(product, quantity));
              LOGGER.info("Added " + product.getName() + " with quantity " + quantity);
            });
  }

  public void removeItem(final Product product) {
    boolean removed = items.removeIf(item -> item.getProduct().equals(product));
    if (removed) {
      LOGGER.info("Removed " + product.getName() + " from the cart");
    } else {
      LOGGER.warning("Attempted to remove " + product.getName() + " which is not in the cart");
    }
  }

  public static synchronized Cart getInstance() { // synchronized for thread-safety
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
