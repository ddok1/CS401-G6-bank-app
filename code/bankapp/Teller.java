package bankapp;

import java.util.Objects;

public class Teller extends Person {
	private static final long serialVersionUID = 1L;
	private int registerNumber;
	
	public Teller() {
		super();
		this.registerNumber = 0;
	}
	
	public Teller(String firstName, String lastName, Address address, int registerNumber) {
        super(firstName, lastName, address);
        this.registerNumber = registerNumber;
    }

    public int getRegisterNumber() {
        return registerNumber;
    }

    public void setRegisterNumber(int registerNumber) {
        this.registerNumber = registerNumber;
    }
    
    public double withdraw(Account account, double amount) {
        Objects.requireNonNull(account, "Account cannot be null");
        validateAmount(amount);
        return account.withdraw(amount);
    }

    public double deposit(Account account, double amount) {
        Objects.requireNonNull(account, "Account cannot be null");
        validateAmount(amount);
        return account.deposit(amount);
    }
    
    public double transfer(Account from, Account to, double amount) {
        Objects.requireNonNull(from, "Account [from] cannot be null");
        Objects.requireNonNull(to, "Account [to] cannot be null");
        validateAmount(amount);

        from.withdraw(amount);
        return to.deposit(amount);
    }
    
    public void assistCustomer(Customer customer) {
        if (customer != null) {
            customer.markTellerVisit();
        }
    }
 

    protected static void validateAmount(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount needs to be above 0");
        }
    }
    
    
}
