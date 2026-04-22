package bankapp;

// validator for credit accounts
// TODO: this does not have any real credit logic, should the credit account have that?

public class CreditAccountValidator extends AccountValidator {

    public ValidationMessage validatePayment(Account a, Person p, double amount) {
        return runAccountValidation(a, p, amount);
    }

    public ValidationMessage validateCharge(Account a, Person p, double amount) {
        ValidationMessage base = baseCheck(a, p, amount);
        if (base != null) {
            return base;
        }

        return new ValidationMessage();
    }

    public ValidationMessage validateTransferToChecking(Account a, Person p, double amount) {
        ValidationMessage base = baseCheck(a, p, amount);
        if (base != null) {
            return base;
        }

        return new ValidationMessage();
    }

    public ValidationMessage validateTransferToSavings(Account a, Person p, double amount) {
        ValidationMessage base = baseCheck(a, p, amount);
        if (base != null) {
            return base;
        }

        return new ValidationMessage();
    }
}