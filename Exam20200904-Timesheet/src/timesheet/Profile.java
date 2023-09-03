package timesheet;

import java.util.*;

public class Profile {
	private int id;
	private List<Integer> workingHours = new ArrayList<>();
	
	public Profile(int id, int[] hours) {
		super();
		this.id = id;
		for(int i : hours) {
			workingHours.add(i);
		}
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public List<Integer> getWorkingHours() {
		return workingHours;
	}
	public void setWorkingHours(List<Integer> workingHours) {
		this.workingHours = workingHours;
	}

	
	
}
