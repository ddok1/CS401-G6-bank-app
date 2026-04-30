package bankapp;

import java.io.Serializable;

public class Response implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String message;
    private final RESPONSE_TYPE type;
    private final String sessionId;
    private final boolean ready;
    private final int queuePosition;
    private final String assignedCustomerName;
    private final String assignedTellerName;

    private final Customer customer;
    private final Account account;
    private final boolean authenticated;

    public enum RESPONSE_TYPE {
        SUCCESS,
        ERROR,
        WARNING,
        INFO,
        LOG
    }

    public Response(String message, RESPONSE_TYPE type) {
        this(message, type, null, false, -1, null, null, null, null, false);
    }

    public Response(
        String message,
        RESPONSE_TYPE type,
        String sessionId,
        boolean ready,
        int queuePosition,
        String assignedCustomerName,
        String assignedTellerName
    ) {
        this(message, type, sessionId, ready, queuePosition, assignedCustomerName, assignedTellerName, null, null, false);
    }

    public Response(
        String message,
        RESPONSE_TYPE type,
        String sessionId,
        boolean ready,
        int queuePosition,
        String assignedCustomerName,
        String assignedTellerName,
        Customer customer,
        Account account,
        boolean authenticated
    ) {
        this.message = message;
        this.type = type;
        this.sessionId = sessionId;
        this.ready = ready;
        this.queuePosition = queuePosition;
        this.assignedCustomerName = assignedCustomerName;
        this.assignedTellerName = assignedTellerName;
        this.customer = customer;
        this.account = account;
        this.authenticated = authenticated;
    }

    public RESPONSE_TYPE getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }

    public String getSessionId() {
        return sessionId;
    }

    public boolean isReady() {
        return ready;
    }

    public int getQueuePosition() {
        return queuePosition;
    }

    public String getAssignedCustomerName() {
        return assignedCustomerName;
    }

    public String getAssignedTellerName() {
        return assignedTellerName;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Account getAccount() {
        return account;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }
}