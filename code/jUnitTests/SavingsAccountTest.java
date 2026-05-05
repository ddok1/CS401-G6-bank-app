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
	Account.ACCOUNT_TYPE typeCredit = Account.ACCOUNT_TYPE.CREDIT; //type
	Person user = new Person(); //user
	
	@Test
	void testTransferSavingsToChecking() {
		CheckingAccount accChecking = new CheckingAccount(balance1, status, typeChecking, user);
		SavingsAccount accSavings = new SavingsAccount(balance2, status, typeSavings, user);
		
		accSavings.transferSavingsToChecking(accChecking, 5.01);
		
		assertEquals(105.51, accChecking.getBalance()); // 100.50 + 5.01 = 105.51
		assertEquals(195.00, accSavings.getBalance()); // 200.01 - 5.01 = 195
	}
	
	@Test
	void testInterestOneMonth() {
		SavingsAccount accSavings = new SavingsAccount(100.00, status, typeSavings, user);
		
		accSavings.applyInterest();

		assertTrue(accSavings.getBalance() >= 100.33); //(100 * 0.4)/12 = 0.3333.., 0.33.. + 100 = 100.33
	}
	
	@Test
	void testTransferSavingsToCredit() {
		SavingsAccount accSavings = new SavingsAccount(balance2, status, typeSavings, user); // 200.01
		CreditAccount accCredit = new CreditAccount(balance1, status, typeCredit, user); // 100.50
		
		accSavings.transferSavingsToCredit(accCredit, 50.0);
		
		assertEquals(150.01, accSavings.getBalance()); // 200.01 - 50.00 - 150.01
		assertEquals(50.50, accCredit.getBalance()); // 100.50 - 50.00 = 50.50
	}

}
