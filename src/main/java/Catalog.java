import java.util.ArrayList;
import java.util.List;

public class Catalog {
  private final List<Product> products = new ArrayList<>();

  /**
   * Adds a product to the catalog
   *
   * @param product Product to add
   */
  public void addProduct(final Product product) {
    if (!products.contains(product)) {
      products.add(product);
    }
  }

  /**
   * Removes a product from the catalog
   *
   * @param product Product to remove
   */
  public void removeProduct(final Product product) {
    if (products.contains(product)) {
      products.remove(product);
    }
  }

  /**
   * Returns the list of products in the catalog
   *
   * @return The list of products
   */
  public List<Product> getProducts() {
    return new ArrayList<>(products);
  }
}
