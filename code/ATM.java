
public class ATM {
	private double cash;
	private int failedAttempts;
	private boolean serviceCompleted;
	
	
	public ATM(String serverIP) {
		this.failedAttempts = 0;
		this.serviceCompleted = false;
	}
	
	// Basic ATM Functions
	public double withdraw(double amount) {
		return amount;
	}
	
	public double deposit(double amount) {
		return amount;
	}
	
	public double checkBalance(double amount) {
		return amount;
	}
	
	public void displayConfirmation() {
		
	}
	public void displayError() {
		
	}
	public double getCashAmount() {
		return cash;
	}
	
	// Login Functions
	public void login(int id, int pin) {
		
	}
	
	public void logAttempt(Log log) {
		
	}
	
	// Getters 
	public double getDailyWithdrawalLimit() {
		return cash;
		
	}
	public double getDailyDepositLimit() {
		return cash;
		
	}
	public int getFailedAttempts() {
		return this.failedAttempts;
		
	}
	public boolean getServiceCompletion() {
		return this.serviceCompleted;
		
	}
	
	// Setters
	public void setCashAmount(double amount) {
		return;
	}
	public void setFailedAttempts(int amount) {
		return;
	}
	public void setServiceCompletion(boolean result) {
		return;
	}

}
