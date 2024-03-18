import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ProductCatalogTests {
  private Catalog catalog;
  private Product product1;
  private Product product2;

  @BeforeEach
  void setUp() {
    catalog = new Catalog();
    product1 = new Product("Product 1", 10.0, "Description 1", 10); // the 10 is temporary
    product2 = new Product("Product 2", 20.0, "Description 2", 10);
    catalog.addProduct(product1);
    catalog.addProduct(product2);
  }

  @Test
  void testAddProduct() {
    Product product3 = new Product("Product 3", 30.0, "Description 3", 10);
    catalog.addProduct(product3);
    assertEquals(3, catalog.getProducts().size(), "Catalog should contain 3 products.");
  }

  @Test
  void testGetProducts() {
    assertEquals(2, catalog.getProducts().size(), "Catalog should contain 2 products.");
  }

  @Test
  void testRemoveProduct() {
    catalog.removeProduct(product1);
    assertEquals(1, catalog.getProducts().size(), "Catalog should contain 1 product after removal.");
  }

  @Test
  void testAddDuplicateProduct() {
    catalog.addProduct(product1);
    assertEquals(2, catalog.getProducts().size(), "Catalog should still contain 2 products.");
  }
}
