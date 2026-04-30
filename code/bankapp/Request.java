package bankapp;
import java.io.*;

// this class is responsible for encapsulating request data to send around in our
// application. Each message is one time use and immutable, once used it will be discarded
public class Request implements Serializable {

    private static final long serialVersionUID = 1L; // eclipse said to do this, so i did. I dont think it matters for our project

    private final REQUEST_TYPE type;
    private final USER_TYPE userType;
    private final Person person;
    private final Account sourceAccount;
    private final Account targetAccount;
    private final double amount;
    private final String text;
    private final boolean customerPresent;
    private final String sessionId;

    public enum REQUEST_TYPE {
        WITHDRAW,
        DEPOSIT,
        TRANSFER,
        VIEW_LOGS,
        VIEW_ACCOUNT,
        OPEN_ACCOUNT,
        CLOSE_ACCOUNT,
        JOIN_TELLER_QUEUE,
        CHECK_TELLER_QUEUE,
        TELLER_READY,
        TELLER_POLL_ASSIGNMENT,
        END_TELLER_SESSION,
        OTHER
    }

    public enum USER_TYPE {
        CUSTOMER,
        TELLER,
        MANAGER,
        ATM
    }

    public Request(REQUEST_TYPE t, USER_TYPE u, Person p, Account s, Account target, double a, String txt, boolean customerPresent, String sessionID) {
        type = t;
        userType = u;
        person = p;
        sourceAccount = s;
        targetAccount = target;
        amount = a;
        text = txt;
        this.customerPresent = customerPresent;
        sessionId = sessionID;
    }

    public REQUEST_TYPE getType() {
        return type;
    }

    public USER_TYPE getUserType() {
        return userType;
    }

    public Person getPerson() {
        return person;
    }

    public Account getSourceAccount() {
        return sourceAccount;
    }

    public Account getTargetAccount() {
        return targetAccount;
    }

    public double getAmount() {
        return amount;
    }

    public String getText() {
        return text;
    }
    // no setters

	public boolean isCustomerPresent() {
		return customerPresent;
	}
	
    public String getSessionId() {
        return sessionId;
    }
}