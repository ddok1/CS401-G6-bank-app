package bankapp;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Objects;

public class ManagerGUI extends JFrame {
    private static final long serialVersionUID = 1L;

    private final Manager manager;
    private Customer customer;
    private Account account;
    private java.util.List<Account> customerAccounts;
    private final BankClientFacade client;

    private JTextField amountField;
    private JLabel sessionLabel;
    private JLabel accountLabel;
    private String currentSessionId;

    public ManagerGUI(Manager manager, Account account, BankClientFacade client) {
        this.manager = Objects.requireNonNull(manager);
        this.account = account;
        this.client = Objects.requireNonNull(client);
        buildUi();
        refreshAccountLabel();
    }

    private void buildUi() {
        setTitle("[MANAGER] " + manager.getName());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1100, 520);
        setLocationRelativeTo(null);

        JPanel root = new JPanel(new BorderLayout(10, 10));
        root.setBorder(new EmptyBorder(15, 15, 15, 15));

        JLabel header = new JLabel("Manager: " + manager.getName() + " | Register #" + manager.getRegisterNumber());
        header.setFont(new Font("SansSerif", Font.BOLD, 20));
        root.add(header, BorderLayout.NORTH);

        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));

        sessionLabel = new JLabel("No active customer.");
        sessionLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));

        accountLabel = new JLabel("No active account.");
        accountLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));

        JPanel amountPanel = new JPanel(new BorderLayout(10, 10));
        JLabel label = new JLabel("Amount:");
        label.setFont(new Font("SansSerif", Font.PLAIN, 18));

        amountField = new JTextField();
        amountField.setFont(new Font("SansSerif", Font.PLAIN, 18));
        amountField.setPreferredSize(new Dimension(220, 40));

        amountPanel.add(label, BorderLayout.WEST);
        amountPanel.add(amountField, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout(0, 3, 10, 10));

        JButton readyBtn = new JButton("Ready for Customer");
        JButton loadCustomerBtn = new JButton("Load / Onboard Customer");
        JButton chooseAccountBtn = new JButton("Choose Customer Account");
        JButton openNewAccountBtn = new JButton("Open New Account");
        JButton freezeBtn = new JButton("Freeze Account");
        JButton unfreezeBtn = new JButton("Unfreeze Account");
        JButton checkRequestBtn = new JButton("Check Customer Request");
        JButton balanceBtn = new JButton("Check Balance");
        JButton depositBtn = new JButton("Deposit");
        JButton withdrawBtn = new JButton("Withdraw");
        JButton transferBtn = new JButton("Transfer");
        JButton closeAccountBtn = new JButton("Close Account");
        JButton selectAnyAccountBtn = new JButton("Select Any Account");
        JButton logsBtn = new JButton("View Logs");
        JButton endSessionBtn = new JButton("End Session");
        JButton exitBtn = new JButton("Exit");

        JButton[] buttons = {
            readyBtn, loadCustomerBtn, chooseAccountBtn, openNewAccountBtn, freezeBtn, unfreezeBtn, checkRequestBtn,
            balanceBtn, depositBtn, withdrawBtn, transferBtn, freezeBtn, unfreezeBtn,
            closeAccountBtn, selectAnyAccountBtn, logsBtn, endSessionBtn, exitBtn
        };
        for (JButton b : buttons) styleButton(b);

        readyBtn.addActionListener(e -> readyForNextCustomer());
        loadCustomerBtn.addActionListener(e -> loadOrOnboardCustomer());
        chooseAccountBtn.addActionListener(e -> chooseExistingAccount());
        openNewAccountBtn.addActionListener(e -> openAdditionalAccount());
        freezeBtn.addActionListener(e -> freezeAccount());
        unfreezeBtn.addActionListener(e -> unfreezeAccount());
        checkRequestBtn.addActionListener(e -> checkCustomerRequest());
        balanceBtn.addActionListener(e -> checkBalance());
        depositBtn.addActionListener(e -> deposit());
        withdrawBtn.addActionListener(e -> withdraw());
        transferBtn.addActionListener(e -> transfer());
        freezeBtn.addActionListener(e -> freezeAccount());
        unfreezeBtn.addActionListener(e -> unfreezeAccount());
        closeAccountBtn.addActionListener(e -> closeAccount());
        selectAnyAccountBtn.addActionListener(e -> selectAnyAccount());
        logsBtn.addActionListener(e -> viewLogs());
        endSessionBtn.addActionListener(e -> endSession());
        exitBtn.addActionListener(e -> exit());

        for (JButton b : buttons) buttonPanel.add(b);

        center.add(sessionLabel);
        center.add(Box.createVerticalStrut(6));
        center.add(accountLabel);
        center.add(Box.createVerticalStrut(10));
        center.add(amountPanel);
        center.add(Box.createVerticalStrut(10));
        center.add(new JScrollPane(buttonPanel));

        root.add(center, BorderLayout.CENTER);
        setContentPane(root);
    }

    private Account chooseAccount(java.util.List<Account> accounts) {
        if (accounts == null || accounts.isEmpty()) return null;

        String[] options = new String[accounts.size()];
        for (int i = 0; i < accounts.size(); i++) {
            Account a = accounts.get(i);
            options[i] = a.getTYPE() + " | #" + a.getAccountNumber() + " | Balance: " + a.getBalance()
                + " | Status: " + a.getSTATUS();
        }

        int selected = JOptionPane.showOptionDialog(
            this,
            "Choose an account:",
            "Select Account",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[0]
        );

        if (selected < 0) return null;
        return accounts.get(selected);
    }

    private Account.ACCOUNT_TYPE promptAccountType() {
        Object[] options = {"Checking", "Savings", "Credit"};
        int choice = JOptionPane.showOptionDialog(
            this,
            "Choose account type:",
            "Account Type",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[0]
        );
        if (choice == 0) return Account.ACCOUNT_TYPE.CHECKING;
        if (choice == 1) return Account.ACCOUNT_TYPE.SAVINGS;
        if (choice == 2) return Account.ACCOUNT_TYPE.CREDIT;
        return null;
    }

    private Account buildAccountForCustomer(Customer customer, Account.ACCOUNT_TYPE type) {
        if (type == Account.ACCOUNT_TYPE.CHECKING) {
            return new CheckingAccount(0.0, Account.ACCOUNT_STATUS.OPEN, Account.ACCOUNT_TYPE.CHECKING, customer);
        }
        if (type == Account.ACCOUNT_TYPE.SAVINGS) {
            return new SavingsAccount(0.0, Account.ACCOUNT_STATUS.OPEN, Account.ACCOUNT_TYPE.SAVINGS, customer);
        }
        if (type == Account.ACCOUNT_TYPE.CREDIT) {
            return new CreditAccount(0.0, Account.ACCOUNT_STATUS.OPEN, Account.ACCOUNT_TYPE.CREDIT, customer);
        }
        return null;
    }

    private void readyForNextCustomer() {
        try {
            if (manager.isCustomerPresent()) {
                throw new IllegalStateException("end the current session before taking the next customer");
            }

            Response response = client.tellerReady(manager);
            if (response == null) {
                showError("no response from server");
                return;
            }

            if (response.isReady()) {
                assignCustomerFromResponse(response);
                showResponse(response, "Assigned");
                return;
            }

            showResponse(response, "Waiting");

            Timer timer = new Timer(2000, e -> {
                Response poll = client.tellerPollAssignment(manager);
                if (poll != null && poll.isReady()) {
                    ((Timer) e.getSource()).stop();
                    try {
                        assignCustomerFromResponse(poll);
                        showResponse(poll, "Assigned");
                    } catch (Exception ex) {
                        showError(ex.getMessage());
                    }
                }
            });
            timer.start();
        } catch (Exception ex) {
            showError(ex.getMessage());
        }
    }

    private void assignCustomerFromResponse(Response response) {
        currentSessionId = response.getSessionId();
        if (response.getCustomer() == null) {
            throw new IllegalStateException("server did not return assigned customer data");
        }

        this.customer = response.getCustomer();
        this.account = response.getAccount();
        this.customerAccounts = response.getAccounts();

        manager.beginSession(customer);
        if (customer.getActiveChannel() == Customer.ACCESS_CHANNEL.NONE) {
            customer.startTellerSession();
        }

        if (this.account == null) {
            sessionLabel.setText("Serving: " + customer.getName() + " | New customer - open an account");
        } else {
            sessionLabel.setText("Serving: " + customer.getName() + " | Manager teller session");
        }
        refreshAccountLabel();
    }

    private void loadOrOnboardCustomer() {
        try {
            if (manager.isCustomerPresent()) {
                throw new IllegalStateException("end the current session before loading another customer");
            }

            String username = JOptionPane.showInputDialog(this, "Customer username:");
            if (username == null || username.trim().isEmpty()) return;

            Response response = client.findCustomer(username.trim());
            if (response == null) {
                showError("no response from server");
                return;
            }

            if (response.getCustomer() != null) {
                this.customer = response.getCustomer();
                this.customerAccounts = response.getAccounts();
                this.account = chooseAccount(customerAccounts == null || customerAccounts.isEmpty()
                    ? java.util.Arrays.asList(response.getAccount())
                    : customerAccounts);

                if (this.account == null) return;

                manager.beginSession(customer);
                if (customer.getActiveChannel() == Customer.ACCESS_CHANNEL.NONE) {
                    customer.startTellerSession();
                }

                sessionLabel.setText("Serving: " + customer.getName() + " | Manager teller session");
                refreshAccountLabel();
                showResponse(new Response("Loaded existing customer account", Response.RESPONSE_TYPE.SUCCESS), "Customer");
                return;
            }

            int create = JOptionPane.showConfirmDialog(
                this,
                "No customer found. Create new customer?",
                "Create Customer",
                JOptionPane.YES_NO_OPTION
            );
            if (create == JOptionPane.YES_OPTION) createNewCustomerAndFirstAccount(username.trim());
        } catch (Exception ex) {
            showError(ex.getMessage());
        }
    }

    private void createNewCustomerAndFirstAccount(String defaultUsername) {
        try {
            String first = JOptionPane.showInputDialog(this, "Customer first name:");
            if (first == null || first.trim().isEmpty()) return;
            String last = JOptionPane.showInputDialog(this, "Customer last name:");
            if (last == null || last.trim().isEmpty()) return;
            String pinText = JOptionPane.showInputDialog(this, "Customer PIN:");
            if (pinText == null || pinText.trim().isEmpty()) return;
            Account.ACCOUNT_TYPE type = promptAccountType();
            if (type == null) return;

            int pin = Integer.parseInt(pinText.trim());
            Response response = client.createCustomerAndAccount(
                manager,
                first.trim(),
                last.trim(),
                defaultUsername.trim(),
                pin,
                type
            );

            if (response != null && response.getType() == Response.RESPONSE_TYPE.SUCCESS) {
                this.customer = response.getCustomer();
                this.account = response.getAccount();
                this.customerAccounts = new java.util.ArrayList<Account>();
                this.customerAccounts.add(account);
                manager.beginSession(customer);
                if (customer.getActiveChannel() == Customer.ACCESS_CHANNEL.NONE) {
                    customer.startTellerSession();
                }
                sessionLabel.setText("Serving: " + customer.getName() + " | Manager teller session");
                refreshAccountLabel();
            }
            showResponse(response, "Create Account");
        } catch (Exception ex) {
            showError(ex.getMessage());
        }
    }

    private void openAdditionalAccount() {
        try {
            requireLoadedCustomer();
            Account.ACCOUNT_TYPE type = promptAccountType();
            if (type == null) return;

            Account newAccount = buildAccountForCustomer(customer, type);
            Response response = client.openAccount(manager, Request.USER_TYPE.MANAGER, newAccount);

            if (response != null && response.getType() == Response.RESPONSE_TYPE.SUCCESS) {
                Response refreshed = client.findCustomer(customer.getUsername());
                if (refreshed != null && refreshed.getCustomer() != null) {
                    this.customer = refreshed.getCustomer();
                    this.customerAccounts = refreshed.getAccounts();
                    if (customerAccounts != null && !customerAccounts.isEmpty()) {
                        this.account = chooseAccount(customerAccounts);
                    }
                }
                refreshAccountLabel();
            }
            showResponse(response, "Open New Account");
        } catch (Exception ex) {
            showError(ex.getMessage());
        }
    }

    private void chooseExistingAccount() {
        try {
            requireLoadedCustomer();
            Response refreshed = client.findCustomer(customer.getUsername());
            if (refreshed == null || refreshed.getCustomer() == null) {
                throw new IllegalStateException("unable to refresh customer accounts");
            }
            this.customer = refreshed.getCustomer();
            this.customerAccounts = refreshed.getAccounts();
            Account selected = chooseAccount(customerAccounts);
            if (selected != null) {
                this.account = selected;
                sessionLabel.setText("Serving: " + customer.getName() + " | Active: " + account.getTYPE());
                refreshAccountLabel();
            }
        } catch (Exception ex) {
            showError(ex.getMessage());
        }
    }

    private void checkCustomerRequest() {
        try {
            requireActiveSession();
            Response response = client.tellerPollCustomerRequest(manager);
            if (response == null) {
                showError("no response from server");
                return;
            }
            if (response.getType() != Response.RESPONSE_TYPE.SUCCESS) {
                showResponse(response, "Customer Request");
                return;
            }
            String action = response.getRequestedAction();
            double amount = response.getRequestedAmount();
            if (action == null || amount <= 0) {
                showResponse(new Response("customer request payload was incomplete", Response.RESPONSE_TYPE.ERROR), "Customer Request");
                return;
            }
            amountField.setText(String.valueOf(amount));
            showResponse(new Response("Customer requested " + action.toLowerCase() + " of " + amount, Response.RESPONSE_TYPE.INFO), "Customer Request");
        } catch (Exception ex) {
            showError(ex.getMessage());
        }
    }

    private void checkBalance() {
        try {
            requireActiveAccount();
            Response response = client.viewAccount(manager, Request.USER_TYPE.MANAGER, account);
            showResponse(response, "Balance");
        } catch (Exception ex) {
            showError(ex.getMessage());
        }
    }

    private void deposit() {
        try {
            requireActiveAccount();
            double amount = parseAmount();
            Response response = client.deposit(manager, Request.USER_TYPE.MANAGER, account, amount);
            updateAccountFromResponse(response);
            completeCustomerRequestIfNeeded("Deposit completed");
            showResponse(response, "Deposit");
            amountField.setText("");
        } catch (Exception ex) {
            showError(ex.getMessage());
        }
    }

    private void withdraw() {
        try {
            requireActiveAccount();
            double amount = parseAmount();
            Response response = client.withdraw(manager, Request.USER_TYPE.MANAGER, account, amount);
            updateAccountFromResponse(response);
            completeCustomerRequestIfNeeded("Withdrawal completed");
            showResponse(response, "Withdraw");
            amountField.setText("");
        } catch (Exception ex) {
            showError(ex.getMessage());
        }
    }

    private void transfer() {
        try {
            requireActiveAccount();
            double amount = parseAmount();
            Response all = client.getAllAccounts();
            if (all == null || all.getAccounts() == null || all.getAccounts().isEmpty()) {
                showError("no target accounts available");
                return;
            }
            Account target = chooseAccount(all.getAccounts());
            if (target == null) return;
            Response response = client.transfer(manager, Request.USER_TYPE.MANAGER, account, target, amount);
            showResponse(response, "Transfer");
            amountField.setText("");
        } catch (Exception ex) {
            showError(ex.getMessage());
        }
    }

    private void freezeAccount() {
        try {
            requireActiveSession();

            Response response = client.freezeAccount(
                manager,
                Request.USER_TYPE.MANAGER,
                account
            );

            if (response != null && response.getType() != Response.RESPONSE_TYPE.ERROR) {
            	if (response != null && response.getAccount() != null) {
            	    account = response.getAccount();
            	}
            	refreshAccountLabel();
            }

            showResponse(response, "Freeze Account");
        } catch (Exception ex) {
            showError(ex.getMessage());
        }
    }

    private void unfreezeAccount() {
        try {
            requireActiveSession();

            Response response = client.unfreezeAccount(
                manager,
                Request.USER_TYPE.MANAGER,
                account
            );

            if (response != null && response.getType() != Response.RESPONSE_TYPE.ERROR) {
                account.setSTATUS(Account.ACCOUNT_STATUS.OPEN);
                refreshAccountLabel();
            }

            showResponse(response, "Unfreeze Account");
        } catch (Exception ex) {
            showError(ex.getMessage());
        }
    }

    private void closeAccount() {
        try {
            requireActiveAccount();
            Response response = client.closeAccount(manager, Request.USER_TYPE.MANAGER, account);
            showResponse(response, "Close Account");
            if (response != null && response.getType() == Response.RESPONSE_TYPE.SUCCESS) {
                account = null;
                refreshAccountLabel();
            }
        } catch (Exception ex) {
            showError(ex.getMessage());
        }
    }

    private void selectAnyAccount() {
        Response response = client.getAllAccounts();
        if (response == null || response.getAccounts() == null || response.getAccounts().isEmpty()) {
            showError("no accounts available");
            return;
        }
        Account selected = chooseAccount(response.getAccounts());
        if (selected != null) {
            this.account = selected;
            this.customer = null;
            this.customerAccounts = null;
            sessionLabel.setText("Manager selected an account directly.");
            refreshAccountLabel();
            showResponse(new Response("Account selected", Response.RESPONSE_TYPE.SUCCESS), "Manager");
        }
    }

    private void viewLogs() {
        Response response = client.viewLogs(manager);
        if (response == null) {
            showError("operation failed: response was null");
            return;
        }
        if (response.getType() == Response.RESPONSE_TYPE.ERROR) {
            showError(response.getMessage());
            return;
        }

        String text = response.getMessage();
        if (text == null || text.trim().isEmpty()) text = "No logs available.";

        JTextArea logArea = new JTextArea(text);
        logArea.setEditable(false);
        logArea.setCaretPosition(0);
        logArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
        logArea.setLineWrap(false);
        logArea.setWrapStyleWord(false);

        JScrollPane scrollPane = new JScrollPane(logArea);
        scrollPane.setPreferredSize(new Dimension(900, 500));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        JOptionPane.showMessageDialog(this, scrollPane, "Manager Logs", JOptionPane.INFORMATION_MESSAGE);
    }

    private void endSession() {
        try {
            if (currentSessionId != null) {
                Response response = client.endTellerSession(manager, currentSessionId);
                clearLocalSession();
                showResponse(response, "Session");
                return;
            }
            clearLocalSession();
        } catch (Exception ex) {
            showError(ex.getMessage());
        }
    }

    private void clearLocalSession() {
        if (manager.isCustomerPresent()) manager.endSession();
        if (customer != null && customer.getActiveChannel() != Customer.ACCESS_CHANNEL.NONE) customer.endSession();
        customer = null;
        account = null;
        customerAccounts = null;
        currentSessionId = null;
        sessionLabel.setText("No active customer.");
        refreshAccountLabel();
    }

    private void exit() {
        try {
            if (manager.isCustomerPresent() || currentSessionId != null) endSession();
        } catch (Exception ignored) { }
        dispose();
    }

    private void completeCustomerRequestIfNeeded(String prefix) {
        if (currentSessionId != null && account != null) {
            client.markTellerTransactionComplete(
                manager,
                currentSessionId,
                account,
                prefix + ". Account: " + account.getTYPE()
                    + ", Status: " + account.getSTATUS()
                    + ", Balance: " + account.getBalance()
            );
        }
    }

    private void updateAccountFromResponse(Response response) {
        if (response != null && response.getType() != Response.RESPONSE_TYPE.ERROR && response.getAccount() != null) {
            this.account = response.getAccount();
        }
        refreshAccountLabel();
    }

    private void refreshAccountLabel() {
        if (account == null) {
            accountLabel.setText("No active account.");
            return;
        }
        accountLabel.setText(
            "Account: " + account.getTYPE()
                + " | #" + account.getAccountNumber()
                + " | Status: " + account.getSTATUS()
                + " | Balance: " + account.getBalance()
        );
    }

    private double parseAmount() {
        String text = amountField.getText();
        if (text == null || text.trim().isEmpty()) {
            throw new IllegalArgumentException("enter an amount");
        }
        try {
            double amount = Double.parseDouble(text.trim());
            if (amount <= 0) throw new IllegalArgumentException("amount must be greater than 0");
            return amount;
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("amount must be a valid number");
        }
    }

    private void showResponse(Response response, String title) {
        if (response == null) {
            showError("operation failed: response was null");
            return;
        }
        String text = response.getMessage();
        if (text == null || text.trim().isEmpty()) text = "operation completed but response text was empty";

        int messageType = response.getType() == Response.RESPONSE_TYPE.ERROR
            ? JOptionPane.ERROR_MESSAGE
            : response.getType() == Response.RESPONSE_TYPE.WARNING
                ? JOptionPane.WARNING_MESSAGE
                : JOptionPane.INFORMATION_MESSAGE;

        JOptionPane.showMessageDialog(this, text, title, messageType);
    }

    private void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void styleButton(JButton b) {
        b.setFont(new Font("SansSerif", Font.BOLD, 14));
        b.setPreferredSize(new Dimension(190, 40));
    }

    private void requireLoadedCustomer() {
        if (customer == null) throw new IllegalStateException("load or connect to a customer first");
    }

    private void requireActiveSession() {
        if (!manager.isCustomerPresent()) {
            throw new IllegalStateException("cannot complete teller operation: no customer is present at the register");
        }
        requireActiveAccount();
    }

    private void requireActiveAccount() {
        if (account == null) throw new IllegalStateException("no account selected");
    }
}
