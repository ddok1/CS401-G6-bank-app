package jUnitTests;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

// runs all validator test classes together
@Suite
@SelectClasses({
    AccountValidatorTest.class,
    CheckingAccountValidatorTest.class,
    SavingsAccountValidatorTest.class,
    CreditAccountValidatorTest.class
})
public class ValidatorTestSuite {
}