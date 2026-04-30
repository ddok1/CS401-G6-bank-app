package bankapp;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Objects;

public class TellerGUI extends JFrame {
    private static final long serialVersionUID = 1L;

    private final Teller teller;
    private Customer customer;
    private Account account;
    private final BankClientFacade client;

    private JTextField amountField;
    private JLabel sessionLabel;
    private String currentSessionId;

    public TellerGUI(Teller teller, Customer customer, Account account, BankClientFacade client) {
        this.teller = Objects.requireNonNull(teller);
        this.customer = customer;
        this.account = account;
        this.client = Objects.requireNonNull(client);

        buildUi();
    }

    private void buildUi() {
        setTitle("Teller Console - " + teller.getName());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(960, 340);
        setLocationRelativeTo(null);

        JPanel root = new JPanel(new BorderLayout(10, 10));
        root.setBorder(new EmptyBorder(15, 15, 15, 15));

        JLabel header = new JLabel("Teller: " + teller.getName() + " | Register #" + teller.getRegisterNumber());
        header.setFont(new Font("SansSerif", Font.BOLD, 20));
        root.add(header, BorderLayout.NORTH);

        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));

        sessionLabel = new JLabel("No active customer.");
        sessionLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));

        JPanel amountPanel = new JPanel(new BorderLayout(10, 10));
        JLabel label = new JLabel("Amount:");
        label.setFont(new Font("SansSerif", Font.PLAIN, 18));

        amountField = new JTextField();
        amountField.setFont(new Font("SansSerif", Font.PLAIN, 18));
        amountField.setPreferredSize(new Dimension(200, 40));

        amountPanel.add(label, BorderLayout.WEST);
        amountPanel.add(amountField, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));

        JButton readyBtn = new JButton("Ready for Next Customer");
        JButton findCustomerBtn = new JButton("Find Customer");
        JButton createCustomerBtn = new JButton("Create Account");
        JButton balanceBtn = new JButton("Check Balance");
        JButton depositBtn = new JButton("Deposit");
        JButton withdrawBtn = new JButton("Withdraw");
        JButton endSessionBtn = new JButton("End Session");

        styleButton(readyBtn);
        styleButton(findCustomerBtn);
        styleButton(createCustomerBtn);
        styleButton(balanceBtn);
        styleButton(depositBtn);
        styleButton(withdrawBtn);
        styleButton(endSessionBtn);

        readyBtn.addActionListener(e -> readyForNextCustomer());
        findCustomerBtn.addActionListener(e -> findCustomer());
        createCustomerBtn.addActionListener(e -> createCustomerAccount());
        balanceBtn.addActionListener(e -> checkBalance());
        depositBtn.addActionListener(e -> deposit());
        withdrawBtn.addActionListener(e -> withdraw());
        endSessionBtn.addActionListener(e -> endSession());

        buttonPanel.add(readyBtn);
        buttonPanel.add(findCustomerBtn);
        buttonPanel.add(createCustomerBtn);
        buttonPanel.add(balanceBtn);
        buttonPanel.add(depositBtn);
        buttonPanel.add(withdrawBtn);
        buttonPanel.add(endSessionBtn);

        center.add(sessionLabel);
        center.add(Box.createVerticalStrut(10));
        center.add(amountPanel);
        center.add(buttonPanel);

        root.add(center, BorderLayout.CENTER);

        setContentPane(root);
    }

    private void readyForNextCustomer() {
        try {
            Response response = client.tellerReady(teller);
            if (response == null) {
                showError("no response from server");
                return;
            }

            if (response.isReady()) {
                currentSessionId = response.getSessionId();

                if (response.getCustomer() == null || response.getAccount() == null) {
                    showError("server assigned a teller session but did not return customer/account data");
                    return;
                }

                this.customer = response.getCustomer();
                this.account = response.getAccount();

                teller.beginSession(customer);
                if (customer.getActiveChannel() == Customer.ACCESS_CHANNEL.NONE) {
                    customer.startTellerSession();
                }

                sessionLabel.setText("Serving: " + customer.getName());
                showResponse(response, "Assigned");
                return;
            }

            showResponse(response, "Waiting");

            Timer timer = new Timer(2000, e -> {
                Response poll = client.tellerPollAssignment(teller);
                if (poll != null && poll.isReady()) {
                    ((Timer) e.getSource()).stop();
                    currentSessionId = poll.getSessionId();

                    if (poll.getCustomer() == null || poll.getAccount() == null) {
                        showError("server assigned a teller session but did not return customer/account data");
                        return;
                    }

                    this.customer = poll.getCustomer();
                    this.account = poll.getAccount();

                    teller.beginSession(customer);
                    if (customer.getActiveChannel() == Customer.ACCESS_CHANNEL.NONE) {
                        customer.startTellerSession();
                    }

                    sessionLabel.setText("Serving: " + customer.getName());
                    showResponse(poll, "Assigned");
                }
            });
            timer.start();
        } catch (Exception ex) {
            showError(ex.getMessage());
        }
    }

    private void checkBalance() {
        try {
            requireActiveSession();
            Response response = client.viewAccount(teller, Request.USER_TYPE.TELLER, account);
            showResponse(response, "Balance");
        } catch (Exception ex) {
            showError(ex.getMessage());
        }
    }

    private void deposit() {
        try {
            requireActiveSession();
            double amount = parseAmount();
            Response response = client.deposit(teller, Request.USER_TYPE.TELLER, account, amount);
            showResponse(response, "Deposit");
            amountField.setText("");
        } catch (Exception ex) {
            showError(ex.getMessage());
        }
    }

    private void withdraw() {
        try {
            requireActiveSession();
            double amount = parseAmount();
            Response response = client.withdraw(teller, Request.USER_TYPE.TELLER, account, amount);
            showResponse(response, "Withdraw");
            amountField.setText("");
        } catch (Exception ex) {
            showError(ex.getMessage());
        }
    }

    private void endSession() {
        try {
            if (currentSessionId != null) {
                Response response = client.endTellerSession(teller, currentSessionId);
                if (teller.isCustomerPresent()) {
                    teller.endSession();
                }
                if (customer != null && customer.getActiveChannel() != Customer.ACCESS_CHANNEL.NONE) {
                    customer.endSession();
                }
                customer = null;
                account = null;
                currentSessionId = null;
                sessionLabel.setText("No active customer.");
                showResponse(response, "Session");
                return;
            }

            if (teller.isCustomerPresent()) {
                teller.endSession();
            }
            if (customer != null && customer.getActiveChannel() != Customer.ACCESS_CHANNEL.NONE) {
                customer.endSession();
            }
            customer = null;
            account = null;
            sessionLabel.setText("No active customer.");
        } catch (Exception ex) {
            showError(ex.getMessage());
        }
    }

    private void findCustomer() {
        try {
            if (teller.isCustomerPresent()) {
                throw new IllegalStateException("end the current session before loading another customer");
            }

            String username = JOptionPane.showInputDialog(this, "Customer username:");
            if (username == null || username.trim().isEmpty()) {
                return;
            }

            Response response = client.findCustomer(username.trim());
            if (response == null) {
                showError("no response from server");
                return;
            }

            if (response.getCustomer() == null || response.getAccount() == null) {
                showResponse(response, "Find Customer");
                return;
            }

            this.customer = response.getCustomer();
            this.account = response.getAccount();

            teller.beginSession(customer);
            if (customer.getActiveChannel() == Customer.ACCESS_CHANNEL.NONE) {
                customer.startTellerSession();
            }
            sessionLabel.setText("Serving: " + customer.getName());

            showResponse(
                new Response("Loaded existing customer account", Response.RESPONSE_TYPE.SUCCESS),
                "Find Customer"
            );
        } catch (Exception ex) {
            showError(ex.getMessage());
        }
    }

    private void createCustomerAccount() {
        try {
            if (teller.isCustomerPresent()) {
                throw new IllegalStateException("end the current session before creating another customer account");
            }

            String first = JOptionPane.showInputDialog(this, "Customer first name:");
            if (first == null || first.trim().isEmpty()) return;

            String last = JOptionPane.showInputDialog(this, "Customer last name:");
            if (last == null || last.trim().isEmpty()) return;

            String username = JOptionPane.showInputDialog(this, "Customer username:");
            if (username == null || username.trim().isEmpty()) return;

            String pinText = JOptionPane.showInputDialog(this, "Customer PIN:");
            if (pinText == null || pinText.trim().isEmpty()) return;

            Response existing = client.findCustomer(username.trim());
            if (existing != null && existing.getCustomer() != null) {
                showError("customer already exists; use Find Customer instead");
                return;
            }

            int pin = Integer.parseInt(pinText.trim());
            Customer newCustomer = new Customer(first.trim(), last.trim(), new Address(), username.trim(), pin);

            Account newAccount = new CheckingAccount(
                0.0,
                Account.ACCOUNT_STATUS.OPEN,
                Account.ACCOUNT_TYPE.CHECKING,
                newCustomer
            );

            Response response = client.openAccount(teller, Request.USER_TYPE.TELLER, newAccount);
            if (response != null && response.getType() == Response.RESPONSE_TYPE.SUCCESS) {
                this.customer = newCustomer;
                this.account = newAccount;
                teller.beginSession(newCustomer);
                newCustomer.startTellerSession();
                sessionLabel.setText("Serving: " + newCustomer.getName());
            }

            showResponse(response, "Create Account");
        } catch (Exception ex) {
            showError(ex.getMessage());
        }
    }

    private double parseAmount() {
        String text = amountField.getText();
        if (text == null || text.trim().isEmpty()) {
            throw new IllegalArgumentException("enter an amount");
        }

        try {
            double amount = Double.parseDouble(text.trim());
            if (amount <= 0) {
                throw new IllegalArgumentException("amount must be greater than 0");
            }
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
        if (text == null || text.trim().isEmpty()) {
            text = "operation completed but response text was empty";
        }

        int messageType =
            response.getType() == Response.RESPONSE_TYPE.ERROR
                ? JOptionPane.ERROR_MESSAGE
                : JOptionPane.INFORMATION_MESSAGE;

        JOptionPane.showMessageDialog(this, text, title, messageType);
    }

    private void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void styleButton(JButton b) {
        b.setFont(new Font("SansSerif", Font.BOLD, 14));
        b.setPreferredSize(new Dimension(170, 40));
    }

    private void requireActiveSession() {
        if (!teller.isCustomerPresent()) {
            throw new IllegalStateException("cannot complete teller operation: no customer is present at the register");
        }
        if (account == null) {
            throw new IllegalStateException("no customer account is assigned to this teller session");
        }
    }
}