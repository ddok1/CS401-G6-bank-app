package bankapp;
import java.net.*;
import java.io.*;
import java.util.*;

public class Server {
	static String ip;
	Logger logger = Logger.getInstance();	// get our instance of logger so it is ready to use
	CheckingAccountValidator checkingValidator = new CheckingAccountValidator();
	SavingsAccountValidator savingsValidator = new SavingsAccountValidator();
	CreditAccountValidator creditValidator = new CreditAccountValidator();
	List<Account> accounts = Collections.synchronizedList(new ArrayList<Account>()); // use a thread safe data structure
	
	
	public static void main(String[] args) {
		Server server = new Server();
		server.start();
	}
	
	private static final String ACCOUNTS_FILE = "accounts.dat"; // create a data file with the objects we want to store
	private void loadAccounts() {
	    File file = new File(ACCOUNTS_FILE);
	    if (!file.exists()) {
	        return;
	    }

	    try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
	        Object obj = in.readObject();
	        if (obj instanceof List<?>) {
	            accounts.clear();
	            accounts.addAll((List<Account>) obj);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	public synchronized void saveAccounts() {
	    try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(ACCOUNTS_FILE))) {
	        out.writeObject(new ArrayList<>(accounts));
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
    public void start() {
    	loadAccounts();
        try {
            ServerSocket serverSocket = new ServerSocket(7890);
            serverSocket.setReuseAddress(true);

            System.out.println("[SERVER] Listening on port 7890...");

            while (true) {
                Socket client = serverSocket.accept();

                System.out.println("[SERVER] New client connected from: " + client.getInetAddress().getHostAddress());

                ClientHandler handler = new ClientHandler(client, this);
                new Thread(handler).start();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
	
}