import java.io.Serializable;
import java.util.Objects;

public class Product implements Serializable {
  private final String name;
  private final double price;
  private final String description;
  private int inventory;

  public Product(String name, double price, String description, int inventory) {
    this.name = name;
    this.price = price;
    this.description = description;
    this.inventory = inventory;
  }

  public String getName() {
    return name;
  }

  public double getPrice() {
    return price;
  }

  public String getDescription() {
    return description;
  }

  public int getInventory() {
    return inventory;
  }

  public void setInventory(int inventory) {
    this.inventory = inventory;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    Product product = (Product) o;
    return Double.compare(product.price, price) == 0 &&
        Objects.equals(name, product.name) &&
        Objects.equals(description, product.description);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, price, description);
  }

  @Override
  public String toString() {
    return name + " - $" + price;
  }
}

class ProductFactory {
  /**
   * Creates a new product
   *
   * @param name        The name
   * @param price       The price
   * @param description The description
   * @param inventory    The inventory
   * @return The product
   */
  public static Product createProduct(final String name, final double price, final String description,
      final int inventory) {
    return new Product(name, price, description, inventory);
  }
}
