package bankapp;

import java.util.*;


public class CheckingAccount extends Account {

	private double withdrawalLimit;
	private double depositLimit;

	public CheckingAccount(double balance, ACCOUNT_STATUS status, ACCOUNT_TYPE type, Person user) {
		super(balance, status, type, user, UUID.randomUUID().toString());
		// TODO Auto-generated constructor stub -- keeping for now for testing purposes
	}

	public CheckingAccount(double balance, ACCOUNT_STATUS status, ACCOUNT_TYPE type, Person user, String accountNumber) {
		super(balance, status, type, user, accountNumber);
	}

	
	public double transferToSavings(SavingsAccount account, double amount) {
		// add amount to savings	
		account.deposit(amount);
		
		// subtract from checking
		withdraw(amount);

		// return balance of checking account
		return getBalance();
	}
	
	public double transferToCredit(CreditAccount account, double amount) {
		// add amount to credit
		account.deposit(amount);
		
		//subtract from checking
		withdraw(amount);
		
		return getBalance();
	}
}