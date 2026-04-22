package bankapp;

// validator for checking accounts
public class CheckingAccountValidator extends AccountValidator {

    public ValidationMessage validateWithdrawal(Account a, Person p, double amount) {
        ValidationMessage base = baseCheck(a, p, amount);
        if (base != null) {
            return base;
        }

        return validateSufficientFunds(a, amount,
                "Validation failed: Insufficient funds for withdrawal");
    }

    public ValidationMessage validateDeposit(Account a, Person p, double amount) {
        return runAccountValidation(a, p, amount);
    }

    public ValidationMessage validateTransferToSavings(Account a, Person p, double amount) {
        ValidationMessage base = baseCheck(a, p, amount);
        if (base != null) {
            return base;
        }

        return validateSufficientFunds(a, amount,
                "Validation failed: Insufficient funds for transfer to savings");
    }

    public ValidationMessage validateTransferToChecking(Account a, Person p, double amount) {
        ValidationMessage base = baseCheck(a, p, amount);
        if (base != null) {
            return base;
        }

        return validateSufficientFunds(a, amount,
                "Validation failed: Insufficient funds for transfer to checking");
    }
}