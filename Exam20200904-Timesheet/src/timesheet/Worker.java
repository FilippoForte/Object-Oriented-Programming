package timesheet;

import java.util.ArrayList;
import java.util.List;

public class Worker {
	private String id;
	private String name;
	private String surname;
	private Profile profile;
	private List<Report> reports = new ArrayList<>();
	
	public Worker(String id, String name, String surname, Profile profile) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.profile = profile;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public Profile getProfile() {
		return profile;
	}
	public void setProfile(Profile profile) {
		this.profile = profile;
	}
	
	public void addReport(Report r) {
		reports.add(r);
	}
	public List<Report> getReports() {
		return reports;
	}
	public void setReports(List<Report> reports) {
		this.reports = reports;
	}
	
}
