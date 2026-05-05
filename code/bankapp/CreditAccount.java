package bankapp;
import java.math.*;
import java.time.*;

public class CreditAccount extends Account {
	
	private double creditLimit;
	private double interestRate = 0.2; // 20%
	private LocalDate date;
	

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
	// initial charge, no interest yet
	public double charge(double amount) {
		
		deposit(amount);
		return getBalance();
		
	}
	
	// add to interest after a certain amount of time (end of the month)
	public double chargeInterest() {
		
		// get daily rate
		BigDecimal interestRateBD = BigDecimal.valueOf(interestRate);
		BigDecimal dailyRate = interestRateBD.divide(BigDecimal.valueOf(365), 10, RoundingMode.HALF_UP); // 
		
		
		BigDecimal balanceBD = BigDecimal.valueOf(getBalance());
		date = LocalDate.now();
		BigDecimal daysInMonth = BigDecimal.valueOf(date.lengthOfMonth());

		
		// multiply amount by interest rate
		BigDecimal balanceRate = balanceBD.multiply(dailyRate); // balance * daily rate
		BigDecimal monthlyInterest = balanceRate.multiply(daysInMonth); // balance rate * days in month
		
		// add to credit balance
		deposit(monthlyInterest.doubleValue());
		
		// add to balance
		return getBalance();
	}
	
	// transfer to checking or savings if credit balance is negative (bank owes user money)
	public double transferCreditToSavings(SavingsAccount account, double amount) {
		// check if amount is less than or equal to credit balance and is also negative
		if (amount <= (getBalance() * -1) && getBalance() < 0.0) {
			account.deposit(amount);
			
			// add to amount
			deposit(amount);
		}
		
		return getBalance();
	}
	
	public double transferCreditToChecking(CheckingAccount account, double amount) {
		
		// make sure credit is negative 
		if (amount <= (getBalance() * -1) && getBalance() < 0.0) {
			// add amount to checking
			account.deposit(amount);
			
			// add to credit balance
			deposit(amount);

		}
		return getBalance();
	}
}
