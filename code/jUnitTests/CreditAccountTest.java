package jUnitTests;

import org.junit.jupiter.api.Test;

import bankapp.Account;
import bankapp.Address;
import bankapp.CreditAccount;
import bankapp.Person;

import static org.junit.jupiter.api.Assertions.*;

public class CreditAccountTest {

    private CreditAccount createAccount(double balance) {
        Person user = new Person("Test", "User", new Address());

        CreditAccount acc = new CreditAccount(
            balance,
            Account.ACCOUNT_STATUS.OPEN,
            Account.ACCOUNT_TYPE.CREDIT,
            user
        );

        acc.setCreditLimit(5000.0);
        return acc;
    }

    @Test
    void testChargeWithinLimit() {
        CreditAccount acc = createAccount(1000.0);

        double newBalance = acc.charge(500.0);

        assertEquals(1500.0, newBalance, 0.001);
        assertEquals(1500.0, acc.getBalance(), 0.001);
    }

    @Test
    void testChargeExceedsLimit() {
        CreditAccount acc = createAccount(4900.0);

        assertThrows(IllegalArgumentException.class, () -> {
            acc.charge(200.0);
        });
    }

    @Test
    void testMakePayment() {
        CreditAccount acc = createAccount(1000.0);

        acc.makePayment(300.0);

        assertEquals(700.0, acc.getBalance(), 0.001);
    }

    @Test
    void testOverPaymentSetsBalanceToZero() {
        CreditAccount acc = createAccount(200.0);

        acc.makePayment(500.0);

        assertEquals(0.0, acc.getBalance(), 0.001);
    }

    @Test
    void testApplyInterest() {
        CreditAccount acc = createAccount(1000.0);
        acc.setInterestRate(0.2);

        double interest = acc.applyInterest();

        assertEquals(200.0, interest, 0.001);
        assertEquals(1200.0, acc.getBalance(), 0.001);
    }

    @Test
    void testInvalidChargeAmount() {
        CreditAccount acc = createAccount(1000.0);

        assertThrows(IllegalArgumentException.class, () -> {
            acc.charge(0.0);
        });
    }

    @Test
    void testInvalidPaymentAmount() {
        CreditAccount acc = createAccount(1000.0);

        assertThrows(IllegalArgumentException.class, () -> {
            acc.makePayment(0.0);
        });
    }

    @Test
    void testSetNegativeCreditLimit() {
        CreditAccount acc = createAccount(1000.0);

        assertThrows(IllegalArgumentException.class, () -> {
            acc.setCreditLimit(-100.0);
        });
    }

    @Test
    void testSetNegativeInterestRate() {
        CreditAccount acc = createAccount(1000.0);

        assertThrows(IllegalArgumentException.class, () -> {
            acc.setInterestRate(-0.1);
        });
    }
}