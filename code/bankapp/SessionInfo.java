package bankapp;

import java.io.Serializable;

public class SessionInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String sessionId;
    private final String username;
    private final String accountNumber;
    private long lastActivityMillis;

    public SessionInfo(String sessionId, String username, String accountNumber) {
        if (sessionId == null || sessionId.isBlank()) {
            throw new IllegalArgumentException("sessionId cannot be blank");
        }
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("username cannot be blank");
        }
        if (accountNumber == null || accountNumber.isBlank()) {
            throw new IllegalArgumentException("accountNumber cannot be blank");
        }

        this.sessionId = sessionId;
        this.username = username;
        this.accountNumber = accountNumber;
        this.lastActivityMillis = System.currentTimeMillis();
    }

    public String getSessionId() {
        return sessionId;
    }

    public String getUsername() {
        return username;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public long getLastActivityMillis() {
        return lastActivityMillis;
    }

    public void touch() {
        lastActivityMillis = System.currentTimeMillis();
    }

    public boolean isExpired(long timeoutMillis) {
        return System.currentTimeMillis() - lastActivityMillis > timeoutMillis;
    }
}