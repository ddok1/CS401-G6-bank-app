package bankapp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ATMGUI {
	private ATM atm;
	
	private JFrame frame;
	private JTextField amountField;
	private JTextArea outputArea;
	private JPasswordField pinField;
	private JTextField accountField;
	private JTextField targetAccountField;
	// added more variables
	
	// Placeholder Variables for testing
	private Account currentAccount;
	private Person currentPerson;
	

	// Creating main frame
	public ATMGUI() {
	atm = new ATM("127.0.0.1");
	frame = new JFrame("ATM Machine");
	frame.setSize(400, 400);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	CardLayout cl = new CardLayout();
	JPanel container = new JPanel(cl);
	frame.setContentPane(container);

	// Panel + GridLayout as auto rows
	JPanel panel = new JPanel();
	panel.setLayout(new GridLayout(0,1));
	
	// Input fields
	// Login Panel
	accountField = new JTextField();
	pinField = new JPasswordField();
	JButton loginButton = new JButton("Login");
	
	panel.add(new JLabel("Account ID:"));
	panel.add(accountField);
	
	panel.add(new JLabel("Pin"));
	panel.add(pinField);
	
	panel.add(loginButton);
	
	// Login Logic
		loginButton.addActionListener(e -> {
		   try {
			int id = Integer.parseInt(accountField.getText());
		    int pin = Integer.parseInt(new String(pinField.getPassword()));
		    
		    boolean success = atm.login(id, pin);
		    // placeholder validation for now
		    if (success) {
		    	    currentPerson = new Person(
		    	        "John",
		    	        "Doe",
		    	        new Address()
		    	    );

		    	    currentAccount = new Account(
		    	        0.0,
		    	        Account.ACCOUNT_STATUS.OPEN,
		    	        Account.ACCOUNT_TYPE.CHECKING,
		    	        currentPerson,
		    	        String.valueOf(id)
		    	    );

		    	    cl.show(container, "ATM");
		    	
		    } else {
		        JOptionPane.showMessageDialog(frame, "Invalid login");
		    }
		    
		   } catch (Exception ex) {
		    	JOptionPane.showMessageDialog(frame, "Enter valid numerics: ");
		    }
		});
	
	// ATM Panel
	JPanel atmPanel = new JPanel();
	atmPanel.setLayout(new GridLayout(0,1));
	
	amountField = new JTextField();
	
	// Necessary Buttons
	JButton withdrawButton = new JButton("Withdraw");
	JButton depositButton = new JButton("Deposit");
	JButton balanceButton = new JButton("Check Balance");
	JButton transferButton = new JButton("Transfer");
	
	outputArea = new JTextArea();
	outputArea.setEditable(false);
	
	// Panel
	atmPanel.add(transferButton);
	atmPanel.add(withdrawButton);
	atmPanel.add(depositButton);
	atmPanel.add(balanceButton);
	
	
	//Withdraw panel
	JPanel withdrawPanel = new JPanel(new GridLayout(0,1));
	JTextField withdrawAmount = new JTextField();
	JButton withdrawSubmit = new JButton("Submit");
	//Back Button
	JButton backBtn1 = new JButton("Back");
	withdrawPanel.add(backBtn1);

	// Withdraw logic
	withdrawSubmit.addActionListener(e -> {
	    try {
	        double amount = Double.parseDouble(withdrawAmount.getText());

	        Response res = atm.withdraw(amount, currentAccount, currentPerson);

	        outputArea.append("[WITHDRAW] " + res.getMessage() + "\n");

	    } catch (Exception ex) {
	        outputArea.append("Invalid withdraw input\n");
	    }
	});
	
	backBtn1.addActionListener(e -> cl.show(container, "ATM"));

	withdrawPanel.add(new JLabel("Enter amount:"));
	withdrawPanel.add(withdrawAmount);
	withdrawPanel.add(withdrawSubmit);
	
	//Deposit panel
	JPanel depositPanel = new JPanel(new GridLayout(0,1));
	JTextField depositAmount = new JTextField();
	JButton depositSubmit = new JButton("Submit");
	//Back button
	JButton backBtn2 = new JButton("Back");
	depositPanel.add(backBtn2);
	backBtn2.addActionListener(e -> cl.show(container, "ATM"));
	
	// Added logic for deposit
	depositSubmit.addActionListener(e -> {
	    try {
	        double amount = Double.parseDouble(depositAmount.getText());

	        Response res = atm.deposit(amount, currentAccount, currentPerson);

	        outputArea.append("[DEPOSIT] " + res.getMessage() + "\n");

	    } catch (Exception ex) {
	        outputArea.append("Invalid deposit input\n");
	    }
	});
	
	depositPanel.add(new JLabel("Enter amount:"));
	depositPanel.add(depositAmount);
	depositPanel.add(depositSubmit);
	
	//Balance panel
	JPanel balancePanel = new JPanel(new GridLayout(0,1));
	JButton checkBalanceBtn = new JButton("Check Balance");
	//Back Button
	JButton backBtn3 = new JButton("Back");
	balancePanel.add(backBtn3);
	backBtn3.addActionListener(e -> cl.show(container, "ATM"));
	
	// Check Balance Logic
	checkBalanceBtn.addActionListener(e -> {
	    try {
	        Response res = atm.checkBalance(currentAccount, currentPerson);

	        outputArea.append("[BALANCE] " + res.getMessage() + "\n");

	    } catch (Exception ex) {
	        outputArea.append("Balance request failed\n");
	    }
	});
	
	balancePanel.add(checkBalanceBtn);
	
	//Transfer panel
	JPanel transferPanel = new JPanel(new GridLayout(0,1));
	JTextField transferAmount = new JTextField();
	targetAccountField = new JTextField();
	JButton transferSubmit = new JButton("Submit");
	//Back Button
	JButton backBtn4 = new JButton("Back");
	transferPanel.add(backBtn4);
	backBtn4.addActionListener(e -> cl.show(container, "ATM"));
	
	//Transfer Logic
	transferSubmit.addActionListener(e -> {
	    try {
	        double amount = Double.parseDouble(transferAmount.getText());
	        String targetId = targetAccountField.getText();

	        Account targetAccount = new Account(
	                0.0,
	                Account.ACCOUNT_STATUS.OPEN,
	                Account.ACCOUNT_TYPE.CHECKING,
	                currentPerson,
	                targetId
	        );

	        Response res = atm.transfer(amount, currentAccount, targetAccount, currentPerson);

	        outputArea.append("[TRANSFER] " + res.getMessage() + "\n");

	    } catch (Exception ex) {
	        outputArea.append("Invalid transfer input\n");
	    }
	});
	
	transferPanel.add(new JLabel("Target Account:"));
	transferPanel.add(targetAccountField);
	transferPanel.add(new JLabel("Amount:"));
	transferPanel.add(transferAmount);
	transferPanel.add(transferSubmit);
	
	
	atmPanel.add(new JScrollPane(outputArea));
	
	// Adding Frame
	container.add(panel, "LOGIN");
	container.add(atmPanel, "ATM");
	container.add(withdrawPanel, "WITHDRAW");
	container.add(depositPanel, "DEPOSIT");
	container.add(balancePanel, "BALANCE");
	container.add(transferPanel, "TRANSFER");
	
	// Action Listeners
	withdrawButton.addActionListener(e -> cl.show(container, "WITHDRAW"));
	depositButton.addActionListener(e -> cl.show(container, "DEPOSIT"));
	balanceButton.addActionListener(e -> cl.show(container, "BALANCE"));
	transferButton.addActionListener(e -> cl.show(container, "TRANSFER"));
	
	//Back Button Logic
	JButton backBtn = new JButton("Back");
	panel.add(backBtn);
	backBtn.addActionListener(e -> cl.show(container, "ATM"));
	
	frame.setVisible(true);
	} 

public static void main(String[] args) {
	new ATMGUI();
}
}

