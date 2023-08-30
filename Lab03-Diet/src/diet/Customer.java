package diet;


public class Customer {
	private String name;
	private String surname;
	private String email;
	private String phone;

	
	public Customer(String name, String surname, String email, String phone) {
		super();
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.phone = phone;
	}

	public String getLastName() {
		return surname;
	}
	
	public String getFirstName() {
		return name;
	}
	
	public String getEmail() {
		return email;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public void SetEmail(String email) {
		this.email=email;
	}
	
	public void setPhone(String phone) {
		this.phone=phone;
	}
	
	@Override
	public String toString() {
		return name + " " + surname;
		
	}
	
}
