package social;

import java.util.ArrayList;
import java.util.List;

public class Group {
	
	private String name;
	private List<String> listOfMembers;
	
	
	public Group(String name) {
		super();
		this.name = name;
		this.listOfMembers = new ArrayList<>();
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public List<String> getListOfMembers() {
		return listOfMembers;
	}
	
	public void addMember(String code) {
		listOfMembers.add(code);
	}
	
}
