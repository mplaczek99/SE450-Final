public class Product {
  private final String name;
  private final double price;
  private final String description;

  public Product(final String name, final double price, final String description) {
    this.name = name;
    this.price = price;
    this.description = description;
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
}

class ProductFactory {
  public static Product createProduct(final String name, final double price, final String description) {
    return new Product(name, price, description);
  }
}
