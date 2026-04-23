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
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
	}
	
	public String getName() {
		return firstName + " " + lastName;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public Address getAddress() {
		return address;
	}
	
	public void setAddress(Address address) {
        this.address = address;
    }
	
	@Override
	public String toString() {
		return "Person{name='" + getName() + "', address=" + address + "}";
	}
}
