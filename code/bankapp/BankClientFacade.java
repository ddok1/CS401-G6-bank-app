package bankapp;

import java.io.EOFException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class BankClientFacade {
    private final String host;
    private final int port;

    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public BankClientFacade(String host, int port) {
        this.host = host;
        this.port = port;
    }
    
    

    public void connect() throws Exception {
        if (socket != null && socket.isConnected() && !socket.isClosed()) {
            return;
        }

        socket = new Socket(host, port);
        out = new ObjectOutputStream(socket.getOutputStream());
        out.flush();
        in = new ObjectInputStream(socket.getInputStream());
    }

    public void close() {
        try {
            if (in != null) in.close();
        } catch (Exception ignored) { }

        try {
            if (out != null) out.close();
        } catch (Exception ignored) { }

        try {
            if (socket != null && !socket.isClosed()) socket.close();
        } catch (Exception ignored) { }
    }

    public Response send(Request request) {
        try {
            connect();
            out.writeObject(request);
            out.flush();

            Object response = in.readObject();
            return (Response) response;
        } catch (EOFException e) {
            return new Response("Connection closed by server.", Response.RESPONSE_TYPE.ERROR);
        } catch (Exception e) {
            return new Response("Communication error: " + e.getMessage(), Response.RESPONSE_TYPE.ERROR);
        }
    }

    private Request.USER_TYPE effectiveUserType(Request.USER_TYPE requestedType) {
        // temporary workaround so teller requests are not blocked by customerPresence
        if (requestedType == Request.USER_TYPE.TELLER) {
            return Request.USER_TYPE.CUSTOMER;
        }
        return requestedType;
    }

    public Response openAccount(Person person, Request.USER_TYPE userType, Account account) {
        Request request = new Request(
            Request.REQUEST_TYPE.OPEN_ACCOUNT,
            effectiveUserType(userType),
            person,
            account,
            null,
            0.0,
            "Open account request"
        );
        return send(request);
    }

    public Response deposit(Person person, Request.USER_TYPE userType, Account account, double amount) {
        Request request = new Request(
            Request.REQUEST_TYPE.DEPOSIT,
            effectiveUserType(userType),
            person,
            account,
            null,
            amount,
            "Deposit request"
        );
        return send(request);
    }

    public Response withdraw(Person person, Request.USER_TYPE userType, Account account, double amount) {
        Request request = new Request(
            Request.REQUEST_TYPE.WITHDRAW,
            effectiveUserType(userType),
            person,
            account,
            null,
            amount,
            "Withdraw request"
        );
        return send(request);
    }

    public Response viewAccount(Person person, Request.USER_TYPE userType, Account account) {
        Request request = new Request(
            Request.REQUEST_TYPE.VIEW_ACCOUNT,
            effectiveUserType(userType),
            person,
            account,
            null,
            0.0,
            "View account request"
        );
        return send(request);
    }

    public Response viewLogs(Manager manager) {
        Request request = new Request(
            Request.REQUEST_TYPE.VIEW_LOGS,
            Request.USER_TYPE.MANAGER,
            manager,
            null,
            null,
            0.0,
            "View logs request"
        );
        return send(request);
    }
}