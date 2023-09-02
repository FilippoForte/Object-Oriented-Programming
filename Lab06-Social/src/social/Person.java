package social;

import java.util.ArrayList;
import java.util.List;

public class Person {
	
	private String code;
	private String name;
	private String surname;
	private List<String> friends;

	public Person(String code, String name, String surname) {
		super();
		this.code = code;
		this.name = name;
		this.surname = surname;
		friends = new ArrayList<>();
	}

	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getSurname() {
		return surname;
	}
	
	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	public void addFriend(String f) {
		friends.add(f);
	}
	
	public List<String> getFriends() {
		return friends;
	}
}
