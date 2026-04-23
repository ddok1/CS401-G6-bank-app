package bankapp;

import java.lang.reflect.Method;
import java.util.Objects;


public class Manager extends Teller {
	private static final long serialVersionUID = 1L;

    public Manager() {
        super();
    }

    public Manager(String firstName, String lastName, Address address, int registerNumber) {
        super(firstName, lastName, address, registerNumber);
    }
    
    public Object viewLogs(Object server) {
        Objects.requireNonNull(server, "server cannot be null");

        try {
            Method validateMethod = server.getClass().getMethod("validateLogAccess", Teller.class);
            Object allowed = validateMethod.invoke(server, this);

            if (allowed instanceof Boolean && !((Boolean) allowed)) {
                throw new SecurityException("manager is not authorized to view logs");
            }
        } catch (NoSuchMethodException ignored) {
            // Optional; skip if server does not expose this method.
        } catch (SecurityException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new RuntimeException("failed validating log access: " + ex.getMessage(), ex);
        }

        try {
            Method getLogsMethod = server.getClass().getMethod("getLogs");
            return getLogsMethod.invoke(server);
        } catch (Exception ex) {
            throw new RuntimeException("failed retrieving logs from server: " + ex.getMessage(), ex);
        }
    }
}
