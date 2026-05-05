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
        CreditAccount acc = createAccount(1000);

        double newBalance = acc.charge(500);

        assertEquals(1500, newBalance);
        assertEquals(1500, acc.getBalance());
    }

    @Test
    void testChargeExceedsLimit() {
        CreditAccount acc = createAccount(4900);

        assertThrows(IllegalArgumentException.class, () -> {
            acc.charge(200);
        });
    }

    @Test
    void testMakePayment() {
        CreditAccount acc = createAccount(1000);

        acc.makePayment(300);

        assertEquals(700, acc.getBalance());
    }

    @Test
    void testOverPaymentSetsZero() {
        CreditAccount acc = createAccount(200);

        acc.makePayment(500);

        assertEquals(0, acc.getBalance());
    }

    @Test
    void testApplyInterest() {
        CreditAccount acc = createAccount(1000);
        acc.setInterestRate(0.2); // 20%

        double interest = acc.applyInterest();

        assertEquals(200, interest);
        assertEquals(1200, acc.getBalance());
    }

    @Test
    void testInvalidChargeAmount() {
        CreditAccount acc = createAccount(1000);

        assertThrows(IllegalArgumentException.class, () -> {
            acc.charge(0);
        });
    }

    @Test
    void testInvalidPaymentAmount() {
        CreditAccount acc = createAccount(1000);

        assertThrows(IllegalArgumentException.class, () -> {
            acc.makePayment(0);
        });
    }

    @Test
    void testSetNegativeCreditLimit() {
        CreditAccount acc = createAccount(1000);

        assertThrows(IllegalArgumentException.class, () -> {
            acc.setCreditLimit(-100);
        });
    }

    @Test
    void testSetNegativeInterestRate() {
        CreditAccount acc = createAccount(1000);

        assertThrows(IllegalArgumentException.class, () -> {
            acc.setInterestRate(-0.1);
        });
    }
}