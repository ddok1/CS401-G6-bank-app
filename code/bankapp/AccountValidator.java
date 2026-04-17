package bankapp;
import java.util.*;

public class AccountValidator {
	protected enum VALIDATION_RESULT {
		PASS,
		FAIL
	}
	// use a private protected class for data encapsulation and easy data movement
	protected class ValidationMessage {
		private String msg;
		private Date date;
		private VALIDATION_RESULT RESULT;
		
		ValidationMessage() {
			msg = "Validation successful";
			date = new Date();
			RESULT = VALIDATION_RESULT.PASS;
		}
		ValidationMessage(String m) {
			msg = m;
			date = new Date();
			RESULT = VALIDATION_RESULT.FAIL;
		}
		public String getMsg() {
			return msg;
		}
		public Date getDate() {
			return date;
		}
		public VALIDATION_RESULT getRESULT() {
			return RESULT;
		}
		public void setMsg(String msg) {
			this.msg = msg;
		}
		public void setDate(Date date) {
			this.date = date;
		}
		public void setRESULT(VALIDATION_RESULT rESULT) {
			RESULT = rESULT;
		}
	}
	// check if the account is open. Fail if status is not 'open'
	public ValidationMessage validateAccountStatus(Account a) {
		if (a.getSTATUS() == Account.ACCOUNT_STATUS.OPEN) {
			return new ValidationMessage();			
		}
		else {
			return new ValidationMessage("Validation failed: ACCOUNT_STATUS not 'open'");
		}
	}
	// check if the user is authorized to use the account. 
	// Fail if the customer is not in the authorized user list.
	public ValidationMessage validateAuthorizedUser(Account a, Person p) {
		if (a.getAuthorizedUsers().contains(p)) {
			return new ValidationMessage();
		}
		else {
			return new ValidationMessage("Validation failed: User not found in Authorized Users");
		}
	}
	
	// this method ONLY validates that the amount is non-negative.
	// Checking the account balance is up to the children (CheckingAccountValidator, etc)
	public ValidationMessage validateAmount(double amount) {
		if (amount >= 0) {
			return new ValidationMessage();
		}
		else {
			return new ValidationMessage("Negative number entered");
		}
	}
	
}
