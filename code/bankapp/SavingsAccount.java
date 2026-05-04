package bankapp;

import java.util.*;


public class SavingsAccount extends Account {

	private double interestRate = 0.04;
	private double minimumBalance;
	
	public SavingsAccount(double balance, ACCOUNT_STATUS status, ACCOUNT_TYPE type, Person user) {
		super(balance, status, type, user);
		// TODO Auto-generated constructor stub -- keeping for now for testing purposes
	}

	public SavingsAccount(double balance, ACCOUNT_STATUS status, ACCOUNT_TYPE type, Person user, String accountNumber) {
		super(balance, status, type, user, accountNumber);
	}
	
	public void applyInterest() {
		// interest is applied monthly
		// interest = (balance * 40%) / 12
		
		double monthlyInterest = (getBalance() * interestRate) / 12;
		deposit(monthlyInterest);
	}
	
	public double transferSavingsToChecking(CheckingAccount account, double amount) {
		// add amount to checking
		account.deposit(amount);
		
		// subtract from savings
		withdraw(amount);

		// return balance of savings account
		return getBalance();
	}
	
	public double transferSavingsToCredit(CreditAccount account, double amount) {
		// add amount to credit (reduces credit balance)
		account.withdraw(amount);
				
		//subtract from checking
		withdraw(amount);
		
		return getBalance();
	}
}