package bankapp;

import java.util.*;


public class SavingsAccount extends Account {

	public SavingsAccount(double balance, ACCOUNT_STATUS status, ACCOUNT_TYPE type, Customer user) {
		super(balance, status, type, user);
		// TODO Auto-generated constructor stub -- keeping for now for testing purposes
	}

	public SavingsAccount(double balance, ACCOUNT_STATUS status, ACCOUNT_TYPE type, Customer user, String accountNumber) {
		super(balance, status, type, user, accountNumber);
	}
	
	public void applyInterest() {
		
	}
	
	public double transferSavingsToChecking(CheckingAccount account, double amount) {
		// add amount to checkings	
		account.deposit(amount);
		
		// subtract from savings
		withdraw(amount);

		// return balance of savings account
		return getBalance();
	}
}