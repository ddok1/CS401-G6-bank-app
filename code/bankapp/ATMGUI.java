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
		    String account = accountField.getText();
		    String pin = new String(pinField.getPassword());

		    // placeholder validation for now
		    if (account.equals("123") && pin.equals("0000")) {
		        cl.show(container, "ATM");
		    } else {
		        JOptionPane.showMessageDialog(frame, "Invalid login");
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

