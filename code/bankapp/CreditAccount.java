package bankapp;

public class CreditAccount extends Account {
	
	private double creditLimit;
	private double interestRate = 0.2;

	public CreditAccount(double balance, ACCOUNT_STATUS status, ACCOUNT_TYPE type, Person user) {
		super(balance, status, type, user);
	}
	public CreditAccount(double balance, ACCOUNT_STATUS status, ACCOUNT_TYPE type, Person user, String accountNumber) {
		super(balance, status, type, user, accountNumber);
	}
	
    public double getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(double creditLimit) {
        if (creditLimit < 0) {
            throw new IllegalArgumentException("credit limit cannot be negative");
        }
        this.creditLimit = creditLimit;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        if (interestRate < 0) {
            throw new IllegalArgumentException("interest rate cannot be negative");
        }
        this.interestRate = interestRate;
    }

    public boolean canCharge(double amount) {
        return getBalance() + amount <= creditLimit;
    }

    public double charge(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("charge amount must be greater than 0");
        }

        if (!canCharge(amount)) {
            throw new IllegalArgumentException("charge exceeds credit limit");
        }

        deposit(amount);
        return getBalance();
    }

    public void makePayment(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("payment must be greater than 0");
        }

        withdraw(amount);

        if (getBalance() < 0) {
            setBalance(0);
        }
    }

    public double applyInterest() {
        double interest = getBalance() * interestRate;
        deposit(interest);
        return interest;
    }
}
