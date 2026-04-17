package bankapp;

// validator for savings accounts
public class SavingsAccountValidator extends AccountValidator {

    public ValidationMessage validateWithdrawal(Account a, Person p, double amount) {
        ValidationMessage base = baseCheck(a, p, amount);
        if (base != null) {
            return base;
        }

        return validateSufficientFunds(a, amount,
                "Validation failed: Insufficient funds for savings withdrawal");
    }

    public ValidationMessage validateDeposit(Account a, Person p, double amount) {
        return runAccountValidation(a, p, amount);
    }

    public ValidationMessage validateTransferToChecking(Account a, Person p, double amount) {
        ValidationMessage base = baseCheck(a, p, amount);
        if (base != null) {
            return base;
        }

        return validateSufficientFunds(a, amount,
                "Validation failed: Insufficient funds for transfer to checking");
    }

    public ValidationMessage validateTransferToCredit(Account a, Person p, double amount) {
        ValidationMessage base = baseCheck(a, p, amount);
        if (base != null) {
            return base;
        }

        return validateSufficientFunds(a, amount,
                "Validation failed: Insufficient funds for transfer to credit");
    }
}