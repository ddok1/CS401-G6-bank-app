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

class CreditAccountTest {

	// creating account instance
	double balance1 = 50.50;
	double balance2 = 100.01;
	Account.ACCOUNT_STATUS status = Account.ACCOUNT_STATUS.OPEN; // status
	Account.ACCOUNT_TYPE typeChecking = Account.ACCOUNT_TYPE.CHECKING; //type
	Account.ACCOUNT_TYPE typeSavings = Account.ACCOUNT_TYPE.SAVINGS; //type
	Account.ACCOUNT_TYPE typeCredit = Account.ACCOUNT_TYPE.CREDIT; //type
	Person user = new Person(); //user
	
	@Test
	void testMakePayment() {
		CreditAccount acc = new CreditAccount(balance2, status, typeCredit, user);
		
		acc.makePayment(balance1);
		
		assertEquals(49.51, acc.getBalance()); // 100.01 - 50.50 = 49.51
	}

}
