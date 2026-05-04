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

class SavingsAccountTest {

	// creating account instance
	double balance1 = 100.50;
	double balance2 = 200.01;
	Account.ACCOUNT_STATUS status = Account.ACCOUNT_STATUS.OPEN; // status
	Account.ACCOUNT_TYPE typeChecking = Account.ACCOUNT_TYPE.CHECKING; //type
	Account.ACCOUNT_TYPE typeSavings = Account.ACCOUNT_TYPE.SAVINGS; //type
	Customer user = new Customer(); //user
	
	@Test
	void testTransferSavingsToChecking() {
		CheckingAccount accChecking = new CheckingAccount(balance1, status, typeChecking, user);
		SavingsAccount accSavings = new SavingsAccount(balance2, status, typeSavings, user);
		
		accSavings.transferSavingsToChecking(accChecking, 5.01);
		
		assertEquals(105.51, accChecking.getBalance());
		assertEquals(195.00, accSavings.getBalance());
	}

}
