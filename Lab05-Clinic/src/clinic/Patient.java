package clinic;

public class Patient {
	
	private String SSN;
	private String firstName;
	private String lastName;
	
	
	public Patient(String sSN, String firstName, String lastName) {
		
		this.SSN = sSN;
		this.firstName = firstName;
		this.lastName = lastName;
	}


	public String getSSN() {
		return SSN;
	}


	public void setSSN(String sSN) {
		SSN = sSN;
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
	
	

}
