class PaymentService {
  private final PaymentGateway paymentGateway;

  public PaymentService(final PaymentGateway paymentGateway) {
    this.paymentGateway = paymentGateway;
  }

  /**
   * Makes a payment
   * 
   * @param amount Amount to be paid
   * @return true if payment was processed
   */
  public boolean makePayment(double amount) {
    return paymentGateway.processPayment(amount);
  }
}

class MockPaymentGateway implements PaymentGateway {
  /**
   * Simulate payment processing
   * 
   * @param amount Amount to be paid
   * @return true if payment was processed
   */
  @Override
  public boolean processPayment(double amount) {
    System.out.println("Processing payment of $" + amount);
    return true;
  }
}

interface PaymentGateway {
  boolean processPayment(double amount);
}
