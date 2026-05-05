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
	
	@Test
	void testChargeInterest() {
		CreditAccount acc = new CreditAccount(balance2, status, typeCredit, user);
		acc.chargeInterest();
		
		assertTrue(acc.getBalance() > balance2); // appproximately 101.70...
		assertTrue(acc.getBalance() < 102.0);
	}
	
	@Test
	void testTransferToAccounts() {
		CreditAccount acc = new CreditAccount(-100.0, status, typeCredit, user);
		SavingsAccount acc2 = new SavingsAccount(balance1, status, typeSavings, user);
		CheckingAccount acc3 = new CheckingAccount(balance2, status, typeChecking, user);
		
		acc.transferCreditToSavings(acc2, 50.0);
		acc.transferCreditToChecking(acc3, 50.0);
		acc.transferCreditToChecking(acc3, 50.0); // try transferring more when credit balance no longer negative
		
		System.out.println(acc.getBalance());
		System.out.println(acc2.getBalance());
		System.out.println(acc3.getBalance());
		assertEquals(0, acc.getBalance());
		assertEquals(100.50, acc2.getBalance());
		assertEquals(150.01, acc3.getBalance());
	}

}
