import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShoppingCartGUI extends JFrame {
  private final Catalog catalog = new Catalog();
  private final Cart cart = Cart.getInstance();
  private final DefaultListModel<Item> cartListModel = new DefaultListModel<>();
  private final JLabel totalPriceLabel = new JLabel("Total: $0.00");
  private JComboBox<Product> productComboBox;
  private JSpinner quantitySpinner;
  private JTextArea productDetailsArea;
  private JList<Item> cartList;
  private final List<Order> orderHistory = new ArrayList<>();
  private final Map<String, String> userCredentials = new HashMap<>();
  private String currentUser;

  public ShoppingCartGUI() {
    setTitle("Shopping Cart");
    setSize(900, 600);
    setDefaultCloseOperation(EXIT_ON_CLOSE);

    initializeCatalog();
    initializeUserCredentials();

    showLoginDialog();
    initUI();
  }

  /**
   * Initializes the catalog
   */
  private void initializeCatalog() {
    catalog.addProduct(new Product("Apple", 0.99, "Fresh Apple", 100));
    catalog.addProduct(new Product("Banana", 0.59, "Fresh Banana", 150));
    catalog.addProduct(new Product("Orange", 0.79, "Fresh Orange", 120));
  }
  
  /**
   * Initializes the user default credentials
   */
  private void initializeUserCredentials() {
    userCredentials.put("user", "password");
  }

  /**
   * Initializes the UI
   */
  private void initUI() {
    setLayout(new BorderLayout());

    add(createCatalogPanel(), BorderLayout.NORTH);
    add(createDetailsPanel(), BorderLayout.EAST);
    add(createCartPanel(), BorderLayout.CENTER);
    add(createButtonPanel(), BorderLayout.SOUTH);
  }

  /**
   * Creates the catalog panel
   *
   * @return The CatalogPanel
   */
  private JPanel createCatalogPanel() {
    JPanel catalogPanel = new JPanel();
    catalogPanel.setBorder(BorderFactory.createTitledBorder("Catalog"));

    JTextField searchField = new JTextField(15);
    searchField.addActionListener(e -> updateProductComboBox(searchField.getText()));
    catalogPanel.add(searchField);

    productComboBox = new JComboBox<>();
    updateProductComboBox("");
    productComboBox.addActionListener(e -> updateProductDetails());
    catalogPanel.add(productComboBox);

    quantitySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
    catalogPanel.add(quantitySpinner);

    JButton addButton = new JButton("Add to Cart");
    addButton.addActionListener(e -> addToCart());
    catalogPanel.add(addButton);

    return catalogPanel;
  }

  /**
   * Updates the product details
   */
  private void updateProductDetails() {
    Product selectedProduct = (Product) productComboBox.getSelectedItem();
    if (selectedProduct != null) {
      productDetailsArea.setText("Name: " + selectedProduct.getName()
          + "\nPrice: $" + selectedProduct.getPrice()
          + "\nInventory: " + selectedProduct.getInventory()
          + "\nDescription: " + selectedProduct.getDescription());
    }
  }

  /**
   * Adds an item to the cart
   *
   * @param product  Product to add
   */
  private void addToCart() {
    Product selectedProduct = (Product) productComboBox.getSelectedItem();
    int quantity = (int) quantitySpinner.getValue();
    if (selectedProduct != null && quantity <= selectedProduct.getInventory()) {
      cart.addItem(selectedProduct, quantity);
      selectedProduct.setInventory(selectedProduct.getInventory() - quantity);
      updateCartDisplay();
      updateProductComboBox("");
    } else {
      JOptionPane.showMessageDialog(this, "Insufficient inventory!", "Error", JOptionPane.ERROR_MESSAGE);
    }
  }

  /**
   * Creates the details panel
   *
   * @return The DetailsPanel
   */
  private JPanel createDetailsPanel() {
    JPanel detailsPanel = new JPanel(new BorderLayout());
    detailsPanel.setBorder(BorderFactory.createTitledBorder("Product Details"));
    productDetailsArea = new JTextArea();
    productDetailsArea.setLineWrap(true);
    productDetailsArea.setWrapStyleWord(true);
    productDetailsArea.setEditable(false);
    detailsPanel.add(new JScrollPane(productDetailsArea), BorderLayout.CENTER);
    return detailsPanel;
  }

  /**
   * Creates the cart panel
   *
   * @return The CartPanel
   */
  private JPanel createCartPanel() {
    JPanel cartPanel = new JPanel(new BorderLayout());
    cartPanel.setBorder(BorderFactory.createTitledBorder("Cart"));
    cartList = new JList<>(cartListModel);
    cartPanel.add(new JScrollPane(cartList), BorderLayout.CENTER);
    cartPanel.add(totalPriceLabel, BorderLayout.SOUTH);

    JButton removeButton = new JButton("Remove Selected");
    removeButton.addActionListener(e -> removeFromCart());
    cartPanel.add(removeButton, BorderLayout.NORTH);

    return cartPanel;
  }

  /**
   * Removes the selected item from the cart
   */
  private void removeFromCart() {
    int selectedIndex = cartList.getSelectedIndex();
    if (selectedIndex != -1) {
      int confirmation = JOptionPane.showConfirmDialog(this, "Are you sure you want to remove this item?",
          "Remove Item", JOptionPane.YES_NO_OPTION);
      if (confirmation == JOptionPane.YES_OPTION) {
        Item selectedItem = cartListModel.get(selectedIndex);
        cart.removeItem(selectedItem.getProduct());
        selectedItem.getProduct().setInventory(selectedItem.getProduct().getInventory() + selectedItem.getQuantity());
        updateCartDisplay();
        updateProductComboBox("");
      }
    }
  }

  /**
   * Creates the button panel
   *
   * @return The ButtonPanel 
   */
  private JPanel createButtonPanel() {
    JPanel buttonPanel = new JPanel();

    JButton checkoutButton = new JButton("Checkout");
    checkoutButton.addActionListener(e -> checkout());
    buttonPanel.add(checkoutButton);

    JButton clearButton = new JButton("Clear Cart");
    clearButton.addActionListener(e -> clearCart());
    buttonPanel.add(clearButton);

    JButton saveButton = new JButton("Save Cart");
    saveButton.addActionListener(e -> saveCart());
    buttonPanel.add(saveButton);

    JButton loadButton = new JButton("Load Cart");
    loadButton.addActionListener(e -> loadCart());
    buttonPanel.add(loadButton);

    JButton historyButton = new JButton("Order History");
    historyButton.addActionListener(e -> showOrderHistory());
    buttonPanel.add(historyButton);

    JButton logoutButton = new JButton("Logout");
    logoutButton.addActionListener(e -> logout());
    buttonPanel.add(logoutButton);

    return buttonPanel;
  }

  /**
   * Checks out the cart
   */
  private void checkout() {
    if (!cart.getItems().isEmpty()) {
      orderHistory.add(new Order(new ArrayList<>(cart.getItems())));
      JOptionPane.showMessageDialog(this, "Checkout successful!");
      cart.clearItems();
      updateCartDisplay();
    } else {
      JOptionPane.showMessageDialog(this, "Cart is empty!", "Checkout Failed", JOptionPane.ERROR_MESSAGE);
    }
  }

  /**
   * Clears the cart
   */
  private void clearCart() {
    int confirmation = JOptionPane.showConfirmDialog(this, "Are you sure you want to clear the cart?", "Clear Cart",
        JOptionPane.YES_NO_OPTION);
    if (confirmation == JOptionPane.YES_OPTION) {
      for (Item item : cart.getItems()) {
        item.getProduct().setInventory(item.getProduct().getInventory() + item.getQuantity());
      }
      cart.clearItems();
      updateCartDisplay();
      updateProductComboBox("");
    }
  }

  /**
   * Logs the user out
   */
  private void logout() {
    currentUser = null;
    showLoginDialog();
  }

  /**
   * Updates the cart display
   */
  private void updateCartDisplay() {
    cartListModel.clear();
    cart.getItems().forEach(cartListModel::addElement);
    totalPriceLabel.setText(String.format("Total: $%.2f", cart.getTotalPrice()));
  }

  /**
   * Updates the product combo box
   *
   * @param query The Query
   */
  private void updateProductComboBox(String query) {
    productComboBox.removeAllItems();
    catalog.searchProducts(query).forEach(productComboBox::addItem);
  }

  /**
   * Shows the login dialog
   */
  private void showLoginDialog() {
    JPanel loginPanel = new JPanel(new GridLayout(3, 2));
    JTextField usernameField = new JTextField();
    JPasswordField passwordField = new JPasswordField();
    loginPanel.add(new JLabel("Username:"));
    loginPanel.add(usernameField);
    loginPanel.add(new JLabel("Password:"));
    loginPanel.add(passwordField);
    int result = JOptionPane.showConfirmDialog(this, loginPanel, "Login", JOptionPane.OK_CANCEL_OPTION);
    if (result == JOptionPane.OK_OPTION) {
      String username = usernameField.getText();
      String password = new String(passwordField.getPassword());
      if (userCredentials.containsKey(username) && userCredentials.get(username).equals(password)) {
        currentUser = username;
      } else {
        JOptionPane.showMessageDialog(this, "Invalid username or password!", "Login Failed", JOptionPane.ERROR_MESSAGE);
        showLoginDialog();
      }
    } else {
      System.exit(0);
    }
  }

  /**
   * Saves the cart
   */
  private void saveCart() {
    try (FileOutputStream fos = new FileOutputStream("cart.ser");
        ObjectOutputStream oos = new ObjectOutputStream(fos)) {
      oos.writeObject(new ArrayList<>(cart.getItems()));
      JOptionPane.showMessageDialog(this, "Cart saved successfully!");
    } catch (IOException e) {
      JOptionPane.showMessageDialog(this, "Failed to save cart: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
  }

  /**
   * Loads the cart
   */
  private void loadCart() {
    try (FileInputStream fis = new FileInputStream("cart.ser");
        ObjectInputStream ois = new ObjectInputStream(fis)) {
      List<Item> items = (List<Item>) ois.readObject();
      cart.clearItems();
      for (Item item : items) {
        if (item.getProduct().getInventory() >= item.getQuantity()) {
          cart.addItem(item.getProduct(), item.getQuantity());
          item.getProduct().setInventory(item.getProduct().getInventory() - item.getQuantity());
        } else {
          JOptionPane.showMessageDialog(this, "Insufficient inventory for " + item.getProduct().getName(), "Error",
              JOptionPane.ERROR_MESSAGE);
        }
      }
      updateCartDisplay();
      JOptionPane.showMessageDialog(this, "Cart loaded successfully!");
    } catch (IOException | ClassNotFoundException e) {
      JOptionPane.showMessageDialog(this, "Failed to load cart: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
  }

  /**
   * Shows the order history
   */
  private void showOrderHistory() {
    StringBuilder history = new StringBuilder("Order History:\n");
    for (int i = 0; i < orderHistory.size(); i++) {
      Order order = orderHistory.get(i);
      history.append("Order ").append(i + 1).append(":\n");
      for (Item item : order.getItems()) {
        history.append(item.getProduct().getName())
            .append(" x ")
            .append(item.getQuantity())
            .append("\n");
      }
      history.append("Total Price: $").append(order.getTotalPrice()).append("\n\n");
    }
    JOptionPane.showMessageDialog(this, history.toString(), "Order History", JOptionPane.INFORMATION_MESSAGE);
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> new ShoppingCartGUI().setVisible(true));
  }
}
