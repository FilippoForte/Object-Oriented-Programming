package timesheet;

public class Activity {
	
	private Project project;
	private String activityName;
	private boolean completed;
	
	public Activity(Project project, String activityName) {
		super();
		this.project = project;
		this.activityName = activityName;
		completed=false;
	}

	public String getActivityName() {
		return activityName;
	}
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}
	public boolean isCompleted() {
		return completed;
	}
	public void setCompleted(boolean completed) {
		this.completed = completed;
	}
	
	
	
}
