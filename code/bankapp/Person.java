package bankapp;

import java.io.Serializable;

public class Person implements Serializable{
	private static final long serialVersionUID = 1L;
	private String firstName;
	private String lastName;
	private Address address;
	
	public Person() {
		this("","", new Address());
	}
	
	
	public Person(String firstName, String lastName, Address address) {
	    setFirstName(firstName);
	    setLastName(lastName);
	    setAddress(address);
	}
	
	public String getName() {
	    String f = firstName == null ? "" : firstName.trim();
	    String l = lastName == null ? "" : lastName.trim();

	    // This is added so that we dont construct a name such as " " when an empty string is passed
	    if (f.isEmpty() && l.isEmpty()) {
	        return "";
	    } else if (f.isEmpty()) {
	        return l;
	    } else if (l.isEmpty()) {
	        return f;
	    } else {
	        return f + " " + l;
	    }
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
	    if (firstName == null) {
	        throw new IllegalArgumentException("first name cannot be null");
	    }
	    this.firstName = firstName.trim();
	}
	
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
	    if (lastName == null) {
	        throw new IllegalArgumentException("last name cannot be null");
	    }
	    this.lastName = lastName.trim();
	}
	
	public Address getAddress() {
		return address;
	}
	
	public void setAddress(Address address) {
	    if (address == null) {
	        throw new IllegalArgumentException("address cannot be null");
	    }
	    this.address = address;
	}
	
	@Override
	public String toString() {
		return "Person{name='" + getName() + "', address=" + address + "}";
	}
}
