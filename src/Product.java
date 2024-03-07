public class Product {
  private final String name;
  private final double price;
  private final String description;

  public Product(String name, double price, String description) {
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
