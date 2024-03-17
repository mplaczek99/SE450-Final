public class Product {
  private final String name;
  private final double price;
  private final String description;

  public Product(final String name, final double price, final String description) {
    this.name = name;
    this.price = price;
    this.description = description;
  }

  /**
   * Returns the name of the product
   *
   * @return The name
   */
  public String getName() {
    return name;
  }

  /**
   * Returns the price of the product
   *
   * @return The price
   */
  public double getPrice() {
    return price;
  }

  /**
   * Returns the description of the product
   *
   * @return The description
   */
  public String getDescription() {
    return description;
  }
}

class ProductFactory {
  /**
   * Creates a new product
   *
   * @param name        The name
   * @param price       The price
   * @param description The description
   * @return The product
   */
  public static Product createProduct(final String name, final double price, final String description) {
    return new Product(name, price, description);
  }
}
