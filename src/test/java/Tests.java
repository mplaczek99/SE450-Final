import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ShoppingCartTests {
  private Cart cart;
  private Product product1;
  private Product product2;

  @BeforeEach
  void setUp() {
    cart = Cart.getInstance();
    cart.clearItems();
    product1 = new Product("Product 1", 10.0, "Description 1");
    product2 = new Product("Product 2", 20.0, "Description 2");
    cart.addItem(product1, 2);
    cart.addItem(product2, 3);
  }

  @Test
  void testAddItem() {
    Product product3 = new Product("Product 3", 30.0, "Description 3");
    cart.addItem(product3, 1);
    assertEquals(3, cart.getItems().size(), "Cart should contain 3 items.");
  }

  @Test
  void testRemoveItem() {
    cart.removeItem(product1);
    assertEquals(1, cart.getItems().size(), "Cart should contain 1 item after removal.");
  }

  @Test
  void testGetTotalPrice() {
    assertEquals(80.0, cart.getTotalPrice(), "Total price should be 80.0.");
  }
}

class ProductCatalogTests {
  private Catalog catalog;
  private Product product1;
  private Product product2;

  @BeforeEach
  void setUp() {
    catalog = new Catalog();
    product1 = new Product("Product 1", 10.0, "Description 1");
    product2 = new Product("Product 2", 20.0, "Description 2");
    catalog.addProduct(product1);
    catalog.addProduct(product2);
  }

  @Test
  void testAddProduct() {
    Product product3 = new Product("Product 3", 30.0, "Description 3");
    catalog.addProduct(product3);
    assertEquals(3, catalog.getProducts().size(), "Catalog should contain 3 products.");
  }

  @Test
  void testGetProducts() {
    assertEquals(2, catalog.getProducts().size(), "Catalog should contain 2 products.");
  }
}

class OrderProcessingTests {
  private Cart cart;
  private Product product1;
  private Product product2;
  private OrderService orderService;

  @BeforeEach
  void setUp() {
    cart = Cart.getInstance();
    product1 = new Product("Product 1", 10.0, "Description 1");
    product2 = new Product("Product 2", 20.0, "Description 2");
    cart.addItem(product1, 2);
    cart.addItem(product2, 3);
    orderService = new OrderService();
  }

  @Test
  void testPlaceOrder() {
    Order order = orderService.placeOrder(cart);
    assertEquals(80.0, order.getTotalPrice(), "Total price of the order should be 80.0.");
    assertTrue(cart.getItems().isEmpty(), "Cart should be empty after placing the order.");
  }

  @Test
  void testGenerateOrderSummary() {
    Order order = orderService.placeOrder(cart);
    String summary = orderService.generateOrderSummary(order);
    assertTrue(summary.contains("Product 1 x 2"), "Order summary should contain Product 1.");
    assertTrue(summary.contains("Product 2 x 3"), "Order summary should contain Product 2.");
    assertTrue(summary.contains("Total Price: 80.0"), "Order summary should contain total price.");
  }
}
