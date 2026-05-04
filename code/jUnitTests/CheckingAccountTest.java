package jUnitTests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import bankapp.Account;
import bankapp.CheckingAccount;
import bankapp.SavingsAccount;
import bankapp.CreditAccount;
import bankapp.Person;
import bankapp.Teller;
import bankapp.Customer;

class CheckingAccountTest {
	
	// creating account instance
	double balance1 = 50.30;
	double balance2 = 20.30;
	Account.ACCOUNT_STATUS status = Account.ACCOUNT_STATUS.OPEN; // status
	Account.ACCOUNT_TYPE typeChecking = Account.ACCOUNT_TYPE.CHECKING; //type
	Account.ACCOUNT_TYPE typeSavings = Account.ACCOUNT_TYPE.SAVINGS; //type
	Customer user = new Customer(); //user

	@Test
	void testTransferCheckingToSaving() {
		// create savings and checkings account
		CheckingAccount accChecking = new CheckingAccount(balance1, status, typeChecking, user);
		SavingsAccount accSavings = new SavingsAccount(balance2, status, typeSavings, user);
		
		accChecking.transferToSavings(accSavings, 20.00); 

		assertEquals(40.30, accSavings.getBalance()); // 20.30 + 20.00 = 40.30 for saving balance
		assertEquals(30.30, accChecking.getBalance()); // 50.30 - 20.00 = 30.30 for checkings balance
		
	}
	
	@Test
	void testTransferCheckingToCredit() {
		CheckingAccount accChecking = new CheckingAccount(balance2, status, typeChecking, user); //20.30
		CreditAccount accCredit = new CreditAccount(balance1, status, typeSavings, user); // 50.30
		
		accChecking.transferToCredit(accCredit, 40.01);
		
		assertEquals(90.31, accCredit.getBalance());
		assertEquals(-19.71, accChecking.getBalance());
		
	}

}
