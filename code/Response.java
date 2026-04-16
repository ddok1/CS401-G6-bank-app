import java.io.*;

// this class is responsible for encapsulating data to send around in our 
// application. Each message is one time use and immutable, once used it will be discarded
public class Response implements Serializable {
	
	private static final long serialVersionUID = 1L; // eclipse said to do this, so i did. I dont think it matters for our project
	String text;
	RESPONSE_TYPE type;
	
	public enum RESPONSE_TYPE {
		SUCCESS,
		ERROR,
		WARNING,
		INFO,
		LOG
	}
	
	Response(String t, RESPONSE_TYPE r) {
		text = t;	// set text
		type = r;	// set response type
	}
	
	RESPONSE_TYPE getType() {
		return type;
	}
	String getText() {
		return text;
	}
	// no setters
	
	
	
	
}