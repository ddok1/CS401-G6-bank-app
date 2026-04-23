package jUnitTests;

import java.util.ArrayList;

import bankapp.Account;
import bankapp.Person;
import bankapp.Account.ACCOUNT_STATUS;

public class ValidatorTestHelper {

    // creates a basic open account with one authorized user and a given balance
    public static Account makeAccount(Account account, Person authorizedUser, double balance) {
        account.setSTATUS(Account.ACCOUNT_STATUS.OPEN);
        account.setBalance(balance);

        ArrayList<Person> users = new ArrayList<Person>();
        users.add(authorizedUser);
        account.setAuthorizedUsers(users);

        return account;
    }

    // creates an open account with no authorized users
    public static Account makeUnauthorizedAccount(Account account, double balance) {
        account.setSTATUS(Account.ACCOUNT_STATUS.OPEN);
        account.setBalance(balance);
        account.setAuthorizedUsers(new ArrayList<Person>());
        return account;
    }

    // creates a non-open account with one authorized user
    public static Account makeClosedLikeAccount(Account account, Person authorizedUser, double balance) {
        account.setSTATUS(Account.ACCOUNT_STATUS.CLOSED);
        account.setBalance(balance);

        ArrayList<Person> users = new ArrayList<Person>();
        users.add(authorizedUser);
        account.setAuthorizedUsers(users);

        return account;
    }
}