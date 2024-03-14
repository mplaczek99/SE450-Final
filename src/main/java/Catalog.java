import java.util.ArrayList;
import java.util.List;

public class Catalog {
  private final List<Product> products = new ArrayList<>();

  public void addProduct(final Product product) {
    if (!products.contains(product)) {
      products.add(product);
    }
  }

  public void removeProduct(final Product product) {
    if (products.contains(product)) {
      products.remove(product);
    }
  }

  public List<Product> getProducts() {
    return new ArrayList<>(products);
  }
}
