package timesheet;

public class Report {
	private String workerID;
	private String projectName;
	private String activityName;
	private int day;
	private int workedHours;
	
	public Report(String workerID, String projectName, String activityName, int day, int workedHours) {
		super();
		this.workerID = workerID;
		this.projectName = projectName;
		this.activityName = activityName;
		this.day = day;
		this.workedHours = workedHours;
	}

	public String getWorkerID() {
		return workerID;
	}

	public void setWorkerID(String workerID) {
		this.workerID = workerID;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getWorkedHours() {
		return workedHours;
	}

	public void setWorkedHours(int workedHours) {
		this.workedHours = workedHours;
	}
	
	
}
