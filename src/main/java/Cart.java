import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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

  /**
   * Adds an item to the cart, and updates the quantity if it exists
   *
   * @param product  Product to add
   * @param quantity Quantity to add
   */
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

  /**
   * Removes an item from the cart
   *
   * @param product Product to remove
   */
  public void removeItem(final Product product) {
    boolean removed = items.removeIf(item -> item.getProduct().equals(product));
    if (removed) {
      LOGGER.info("Removed " + product.getName() + " from the cart");
    } else {
      LOGGER.warning("Attempted to remove " + product.getName() + " which is not in the cart");
    }
  }

  /**
   * Returns the singleton instance of the cart
   * 
   * @return The singleton instance
   */
  public static synchronized Cart getInstance() { // synchronized for thread-safety
    if (instance == null) {
      instance = new Cart();
    }
    return instance;
  }

  /**
   * Calculates the total price of all items in the cart
   *
   * @return The total price of all items
   */
  public double getTotalPrice() {
    return items.stream()
        .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
        .sum();
  }

  /**
   * Returns the list of items in the cart
   *
   * @return The list of items
   */
  public List<Item> getItems() {
    return new ArrayList<>(items);
  }

  public void setItems(List<Item> items) {
    this.items.clear();
    this.items.addAll(items);
  }

  /**
   * Removes all items from the cart
   */
  public void clearItems() {
    items.clear();
  }

  /**
   * Saves the cart to a file
   */
  /*
   * public void saveCart() {
   * try (FileOutputStream fos = new FileOutputStream("cart.ser");
   * ObjectOutputStream oos = new ObjectOutputStream(fos)) {
   * oos.writeObject(items);
   * LOGGER.info("Cart saved successfully!");
   * } catch (IOException e) {
   * LOGGER.warning("Failed to save cart: " + e.getMessage());
   * }
   * }
   */
  /**
   * Loads the cart from a file
   */
  /*
   * public void loadCart() {
   * try (FileInputStream fis = new FileInputStream("cart.ser");
   * ObjectInputStream ois = new ObjectInputStream(fis)) {
   * Object loadedObject = ois.readObject();
   * if (loadedObject instanceof List) {
   * List<?> loadedItems = (List<?>) loadedObject;
   * items.clear();
   * for (Object obj : loadedItems) {
   * if (obj instanceof Item) {
   * items.add((Item) obj);
   * } else {
   * LOGGER.warning("Failed to load cart: Invalid format in file");
   * return;
   * }
   * }
   * LOGGER.info("Cart loaded successfully!");
   * } else {
   * LOGGER.warning("Failed to load cart: Invalid format in file");
   * }
   * } catch (IOException | ClassNotFoundException e) {
   * LOGGER.warning("Failed to load cart: " + e.getMessage());
   * }
   * }
   */
}

class CartBuilder {
  private final Cart cart;

  public CartBuilder() {
    this.cart = Cart.getInstance();
    this.cart.clearItems(); // Ensures the cart is empty before building
  }

  /**
   * Adds an item to the cart
   *
   * @param product  Product to add
   * @param quantity Quantity to add
   * @return The CartBuilder
   */
  public CartBuilder addItem(final Product product, final int quantity) {
    cart.addItem(product, quantity);
    return this;
  }

  /**
   * Removes an item from the cart
   *
   * @param product Product to remove
   * @return The CartBuilder
   */
  public CartBuilder removeItem(final Product product) {
    cart.removeItem(product);
    return this;
  }

  /**
   * Builds the cart
   *
   * @return The Cart
   */
  public Cart build() {
    return cart;
  }
}
