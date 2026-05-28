package hotelPackage;

public class BankAccount implements Payable {
    private double balance;

    public BankAccount(double initialBalance) {
        try {
            if (initialBalance <= 0) {
                throw new IllegalArgumentException();
            }
            this.balance = initialBalance;
        }catch (IllegalArgumentException e){
            System.out.println("Balance must be positive");
        }
    }

    public boolean withdraw(double amount) {
        try {
            if (amount <= 0) {
                throw new IllegalArgumentException("Withdrawal amount must be positive.");
            }
            if (balance < amount) {
                throw new IllegalStateException("Insufficient balance for withdrawal.");
            }
            balance -= amount;
            System.out.println("Successfully withdrew: $" + amount);
            return true;
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.err.println("Withdrawal failed");
            return false;
        }
    }

    @Override
    public void processPayment(double amount) {

        try {
            // Check if the amount is valid
            if (amount <= 0) {
                throw new IllegalArgumentException();
            }
            // Attempt withdrawal
            if (withdraw(amount)) {
                System.out.println("Payment of $" + amount + " processed successfully.");
            } else {
                throw new IllegalStateException();
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Payment amount must be positive.");
        } catch (IllegalStateException e) {
            System.out.println("Insufficient balance to process payment of $" + amount);
        }
    }

    public double getBalance() {
        return balance;
    }
}
