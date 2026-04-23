package bankapp;

import java.time.LocalDate;
import java.util.Objects;

public class Customer extends Person {
    private static final long serialVersionUID = 1L;
    
    private LocalDate joinDate;
    private LocalDate lastTellerVisit;
    private LocalDate lastAtmVisit;
    private String username;
    private int pin;
    
    public Customer() {
        super();
        this.joinDate = LocalDate.now();
        this.lastTellerVisit = null;
        this.lastAtmVisit = null;
        this.username = "";
        this.pin = 0;
    }

    public Customer(String firstName, String lastName, Address address, String username, int pin) {
        super(firstName, lastName, address);
        this.joinDate = LocalDate.now();
        this.lastTellerVisit = null;
        this.lastAtmVisit = null;
        this.username = requireUsername(username);
        validatePin(pin);
        this.pin = pin;
    }
    
    public LocalDate getJoinDate() {
        return joinDate;
    }

    public LocalDate getLastTellerVisit() {
        return lastTellerVisit;
    }

    public void setLastTellerVisit(LocalDate lastTellerVisit) {
        this.lastTellerVisit = lastTellerVisit;
    }

    public LocalDate getLastAtmVisit() {
        return lastAtmVisit;
    }

    public void setLastAtmVisit(LocalDate lastAtmVisit) {
        this.lastAtmVisit = lastAtmVisit;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = requireUsername(username);
    }

    public int getPin() {
        return pin;
    }

    public void changePin(int newPin) {
        validatePin(newPin);
        this.pin = newPin;
        this.lastTellerVisit = LocalDate.now();
    }
    
    public boolean verifyPin(int attemptedPin) {
        return this.pin == attemptedPin;
    }
    
    public void markTellerVisit() {
        this.lastTellerVisit = LocalDate.now();
    }

    public void markAtmVisit() {
        this.lastAtmVisit = LocalDate.now();
    }
    
    public double transfer(Account from, Account to, double amount) {
        Objects.requireNonNull(from, "from account cannot be null");
        Objects.requireNonNull(to, "to account cannot be null");
        validateAmount(amount);

        from.withdraw(amount);
        return to.deposit(amount);
    }
    
    public double deposit(Account account, double amount) {
        Objects.requireNonNull(account, "account cannot be null");
        validateAmount(amount);
        return account.deposit(amount);
    }

    public double withdraw(Account account, double amount) {
        Objects.requireNonNull(account, "account cannot be null");
        validateAmount(amount);
        return account.withdraw(amount);
    }
    
    @Override
    public String toString() {
        return "Customer{name='" + getName() + "', username='" + username + "', address=" + getAddress() + "}";
    }

    protected static void validateAmount(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be above 0");
        }
    }

    private static String requireUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("username cannot be blank");
        }
        return username.trim();
    }

    private static void validatePin(int pin) {
        if (pin < 0 || pin > 999999) {
            throw new IllegalArgumentException("pin must be between 0 and 999999");
        }
    }
}
