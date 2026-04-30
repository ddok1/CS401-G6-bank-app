package bankapp;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class BankAppMain {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(BankAppMain::launch);
    }

    private static void launch() {
        String serverIp = JOptionPane.showInputDialog(
            null,
            "Enter server IP:",
            "Connect to Bank Server",
            JOptionPane.QUESTION_MESSAGE
        );

        if (serverIp == null || serverIp.trim().isEmpty()) {
            return;
        }

        serverIp = serverIp.trim();
        BankClientFacade client = new BankClientFacade(serverIp, 7890);

        String[] roles = {"Customer", "Teller", "Manager", "Quit"};
        int role = JOptionPane.showOptionDialog(
            null,
            "Choose how to connect.",
            "Bank App",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            roles,
            roles[0]
        );

        if (role == 0) {
            launchCustomer(client, serverIp);
        } else if (role == 1) {
            launchTeller(client);
        } else if (role == 2) {
            launchManager(client);
        } else {
            client.close();
        }
    }

    private static void launchCustomer(BankClientFacade client, String serverIp) {
        String first = prompt("Customer first name:");
        if (first == null) return;

        String last = prompt("Customer last name:");
        if (last == null) return;

        String username = prompt("Customer username:");
        if (username == null) return;

        String pinText = prompt("Customer PIN:");
        if (pinText == null) return;

        Customer customer;
        try {
            int pin = Integer.parseInt(pinText.trim());
            customer = new Customer(first, last, new Address(), username, pin);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Account account = new CheckingAccount(
            0.0,
            Account.ACCOUNT_STATUS.OPEN,
            Account.ACCOUNT_TYPE.CHECKING,
            customer
        );

        String[] choices = {"ATM", "Teller", "Cancel"};
        int mode = JOptionPane.showOptionDialog(
            null,
            "Choose customer access channel.",
            "Customer Access",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            choices,
            choices[0]
        );

        if (mode == 0) {
            ATM atm = new ATM(serverIp);
            ATMGUI gui = new ATMGUI(atm);
            gui.setVisible(true);
        } else if (mode == 1) {
            customer.startTellerSession();
            String sessionId = client.createSessionId();
            WaitingForTellerDialog waiting = new WaitingForTellerDialog(customer, account, client, sessionId);
            waiting.setVisible(true);
        }
    }

    private static void launchTeller(BankClientFacade client) {
        String first = prompt("Teller first name:");
        if (first == null) return;

        String last = prompt("Teller last name:");
        if (last == null) return;

        String registerText = prompt("Register number:");
        if (registerText == null) return;

        try {
            int register = Integer.parseInt(registerText.trim());
            Teller teller = new Teller(first, last, new Address(), register);
            TellerGUI gui = new TellerGUI(teller, null, null, client);
            gui.setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void launchManager(BankClientFacade client) {
        String first = prompt("Manager first name:");
        if (first == null) return;

        String last = prompt("Manager last name:");
        if (last == null) return;

        String registerText = prompt("Manager register number:");
        if (registerText == null) return;

        try {
            int register = Integer.parseInt(registerText.trim());
            Manager manager = new Manager(first, last, new Address(), register);

            Account account = new CheckingAccount(
                0.0,
                Account.ACCOUNT_STATUS.OPEN,
                Account.ACCOUNT_TYPE.CHECKING,
                manager
            );

            ManagerGUI gui = new ManagerGUI(manager, account, client);
            gui.setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static String prompt(String message) {
        String value = JOptionPane.showInputDialog(null, message, "Bank App", JOptionPane.QUESTION_MESSAGE);
        if (value == null) return null;
        value = value.trim();
        if (value.isEmpty()) return null;
        return value;
    }
}