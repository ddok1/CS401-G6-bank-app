package bankapp;
import java.io.*;

// this class is responsible for encapsulating response data to send around in our 
// application. Each message is one time use and immutable, once used it will be discarded
public class Response implements Serializable {
	
	private static final long serialVersionUID = 1L; // eclipse said to do this, so i did. I dont think it matters for our project
	private final String message;
	private final RESPONSE_TYPE type;
    private final String sessionId;
    private final boolean ready;
    private final int queuePosition;
    private final String assignedCustomerName;
    private final String assignedTellerName;
	
	public enum RESPONSE_TYPE {
		SUCCESS,
		ERROR,
		WARNING,
		INFO,
		LOG
	}
	
    public Response(String message, RESPONSE_TYPE type) {
        this(message, type, null, false, -1, null, null);
    }
    public Response(String message, RESPONSE_TYPE type, String sessionId, boolean ready, 
    		int queuePosition, String assignedCustomerName, String assignedTellerName) 
    {
            this.message = message;
            this.type = type;
            this.sessionId = sessionId;
            this.ready = ready;
            this.queuePosition = queuePosition;
            this.assignedCustomerName = assignedCustomerName;
            this.assignedTellerName = assignedTellerName;
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
	// no setters
}