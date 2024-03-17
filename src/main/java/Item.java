import java.util.Objects;

public class Item {
  private final Product product;
  private int quantity;

  public Item(final Product product, final int quantity) {
    this.product = product;
    this.quantity = quantity;
  }

  /**
   * Returns the product
   *
   * @return The product
   */
  public Product getProduct() {
    return product;
  }

  /**
   * Returns the quantity
   *
   * @return The quantity
   */
  public int getQuantity() {
    return quantity;
  }

  /**
   * Sets the quantity
   *
   * @param quantity The quantity
   */
  public void setQuantity(final int quantity) {
    this.quantity = quantity;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    Item item = (Item) o;
    return quantity == item.quantity &&
        Objects.equals(product, item.product);
  }

  @Override
  public int hashCode() {
    return Objects.hash(product, quantity);
  }
}
