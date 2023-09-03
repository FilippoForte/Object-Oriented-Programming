package timesheet;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class Project {
	private String name;
	private int maxHours;
	
	private SortedMap<String, Activity> activityMap = new TreeMap<>();
	private List<Report> reports = new ArrayList<>();
	
	
	public Project(String name, int maxHours) {
		super();
		this.name = name;
		this.maxHours = maxHours;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getMaxHours() {
		return maxHours;
	}
	public void setMaxHours(int maxHours) {
		this.maxHours = maxHours;
	}
	public void addActivity(String activityName) {
		this.activityMap.put(activityName, new Activity(this, activityName));
		
	}
	public SortedMap<String, Activity> getActivityMap() {
		return activityMap;
	}
	public void setActivityMap(SortedMap<String, Activity> activityMap) {
		this.activityMap = activityMap;
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
