package hotelPackage;

import java.util.ArrayList;
import java.util.List;

public class Customer extends User implements displayable {
    private List<Booking> bookings = new ArrayList<>();
    private BankAccount bankAccount;

    public Customer(String name, String email, BankAccount bankAccount) {
        super(name, email);
        this.bankAccount = bankAccount;
    }

    public Customer(String name, String email, String password, BankAccount bankAccount) {
        super(name, email, password);
        this.bankAccount = bankAccount;
    }

    @Override
    public void displayInfo() {
        System.out.println("Customer: " + name + ", Email: " + email + ", Balance: $" + bankAccount.getBalance());
    }

    public void addBooking(Booking booking) {
        bookings.add(booking);
    }



    public BankAccount getBankAccount() {
        return bankAccount;
    }


    public boolean makePayment(double amount) {
        return bankAccount.withdraw(amount);
    }

}
