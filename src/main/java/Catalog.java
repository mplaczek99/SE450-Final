import java.util.ArrayList;
import java.util.List;

public class Catalog {
  private final List<Product> products = new ArrayList<>();

  public void addProduct(Product product) {
    products.add(product);
  }

  public List<Product> getProducts() {
    return new ArrayList<>(products);
  }
}
