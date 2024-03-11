public class ProductFactory {
  public static Product createProduct(String name, double price, String description) {
    return new Product(name, price, description);
  }
}
