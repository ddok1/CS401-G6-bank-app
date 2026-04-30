package jUnitTests;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

// runs all tests
@Suite
@SelectClasses({
	LogTest.class,						 LoggerTest.class,
	AddressTest.class,                   ManagerTest.class,
	ATMJTest.class,                      PersonTest.class,
	CheckingAccountValidatorTest.class,  SavingsAccountValidatorTest.class,
	CreditAccountValidatorTest.class,    TellerTest.class,
	CustomerTest.class,                  TotalTestSuite.class,
	ValidatorTestHelper.class
})
public class TotalTestSuite {
}