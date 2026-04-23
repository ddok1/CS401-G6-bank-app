package bankapp;

import java.util.*;

public class Account {
	public enum ACCOUNT_STATUS {
		OPEN,
		CLOSED,
		SUSPENDED,
		FLAGGED,
		FROZEN
	}
	public enum ACCOUNT_TYPE {
		CHECKING,
		SAVINGS,
		CREDIT
	}
	
	private double balance;
	private Date lastUsed;
	private ACCOUNT_STATUS STATUS;
	private ACCOUNT_TYPE TYPE;
	private ArrayList<Person>authorizedUsers;
	
	// constructor
	public Account(double balance, ACCOUNT_STATUS status, ACCOUNT_TYPE type, Person user) {
		this.balance = balance;
		STATUS = status;
		TYPE = type;
		// person array is an argument? or one person is entered into array at initialization?
		// authorizedUsers[0] = user; //doesn't accept array 
	}
	
	public void addAuthorizedUser(Customer user, Teller t) {
		
	}
	
	public void removeAuthorizedUser(Customer user, Teller t) {
		
	}
	
	public boolean isSuspended() {
		// true if account_status == suspended
		if (STATUS == ACCOUNT_STATUS.SUSPENDED) {	
			return true;
			
		} else {
			// else, false
			return false;
		}
	}
	
	public boolean isFrozen() {
		// true if account_status == frozen
		if (STATUS == ACCOUNT_STATUS.FROZEN) {	
			return true;
			
		} else {
			// else, false
			return false;
		}	
	}
	
	public void freeze() {
		// turn account status to frozen
		STATUS = ACCOUNT_STATUS.FROZEN;
		
	}
	
	public void unfreeze() {
		// open account again
		STATUS = ACCOUNT_STATUS.OPEN;
		
	}
	
	public void closeAccount() {
		// close account status
		STATUS = ACCOUNT_STATUS.CLOSED;
	}
	
	public double withdraw(double amount) {
		// get account type (checkings/savings)
		// withdraw from account type
		
		return 0.0;
	}
	
	public double deposit(double amount) {
		return 0.0;
	}
	
	// getters
	public double getBalance() {
		return balance;
	}
	
	public Date getLastUsed() {
		return lastUsed;
	}
	
	public ACCOUNT_STATUS getSTATUS() {
		return STATUS;
	}
	
	public ACCOUNT_TYPE getTYPE() {
		return TYPE;
	}
	
	public ArrayList<Person> getAuthorizedUsers() {
		return authorizedUsers;
	}
	
	// setters
	public void setBalance(double balance) {
		this.balance = balance;
	}
	
	public void setLastUsed(Date lastUsed) {
		this.lastUsed = lastUsed;
	}
	
	public void setSTATUS(ACCOUNT_STATUS sTATUS) {
		STATUS = sTATUS;
	}
	
	public void setTYPE(ACCOUNT_TYPE tYPE) {
		TYPE = tYPE;
	}
	
	public void setAuthorizedUsers(ArrayList<Person> authorizedUsers) {
		this.authorizedUsers = authorizedUsers;
	}
}
