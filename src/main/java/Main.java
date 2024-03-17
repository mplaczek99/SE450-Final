import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JFrame {
    private final Catalog catalog;
    private final Cart cart;
    private final DefaultListModel<String> cartListModel;
    private final JLabel totalPriceLabel;

    public Main() {
        setTitle("Shopping Cart");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        catalog = new Catalog();
        cart = Cart.getInstance();

        // Sample products
        catalog.addProduct(new Product("Apple", 0.99, "Fresh Apple"));
        catalog.addProduct(new Product("Banana", 0.59, "Fresh Banana"));
        catalog.addProduct(new Product("Orange", 0.79, "Fresh Orange"));

        cartListModel = new DefaultListModel<>();
        totalPriceLabel = new JLabel("Total: $0.00");

        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());

        // Catalog panel
        JPanel catalogPanel = new JPanel(new GridLayout(0, 1));
        catalogPanel.setBorder(BorderFactory.createTitledBorder("Catalog"));
        for (Product product : catalog.getProducts()) {
            JButton button = new JButton(product.getName() + " - $" + product.getPrice());
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    cart.addItem(product, 1);
                    updateCartDisplay();
                }
            });
            catalogPanel.add(button);
        }
        add(catalogPanel, BorderLayout.WEST);

        // Cart panel
        JPanel cartPanel = new JPanel(new BorderLayout());
        cartPanel.setBorder(BorderFactory.createTitledBorder("Cart"));
        JList<String> cartList = new JList<>(cartListModel);
        cartPanel.add(new JScrollPane(cartList), BorderLayout.CENTER);
        cartPanel.add(totalPriceLabel, BorderLayout.SOUTH);
        add(cartPanel, BorderLayout.CENTER);

        // Checkout button
        JButton checkoutButton = new JButton("Checkout");
        checkoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(Main.this, "Checkout successful!");
                cart.clearItems();
                updateCartDisplay();
            }
        });
        add(checkoutButton, BorderLayout.SOUTH);
    }

    private void updateCartDisplay() {
        cartListModel.clear();
        for (Item item : cart.getItems()) {
            cartListModel.addElement(item.getProduct().getName() + " x " + item.getQuantity());
        }
        totalPriceLabel.setText(String.format("Total: $%.2f", cart.getTotalPrice()));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Main gui = new Main();
                gui.setVisible(true);
            }
        });
    }
}
