package bankapp;

public class CreditAccount extends Account {
	
	private double creditLimit;
	private double interestRate = 0.2;

	public CreditAccount(double balance, ACCOUNT_STATUS status, ACCOUNT_TYPE type, Person user) {
		super(balance, status, type, user);
		// TODO Auto-generated constructor stub -- keeping for now for testing purposes
	}
	public CreditAccount(double balance, ACCOUNT_STATUS status, ACCOUNT_TYPE type, Person user, String accountNumber) {
		super(balance, status, type, user, accountNumber);
	}

	// reduce debt
	public void makePayment(double amount) {
		// subtract amount from balance
		withdraw(amount);
		
	}
	
	// increase debt
	public double charge(double amount) {
		
		// multiply amount by interest rate
		// add to balance
		double total = 0.0;
		return total;
	}
}
