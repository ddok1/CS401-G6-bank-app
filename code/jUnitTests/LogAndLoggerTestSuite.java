package jUnitTests;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

// runs only the log-related tests
@Suite
@SelectClasses({
    LogTest.class,
    LoggerTest.class
})
public class LogAndLoggerTestSuite {
}