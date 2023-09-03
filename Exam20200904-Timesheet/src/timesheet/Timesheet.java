package timesheet;

import java.time.temporal.WeekFields;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.swing.event.MenuKeyEvent;

public class Timesheet {
	private List<Integer> holiday = new ArrayList<>();
	private int firstWeekDay=1;
	private SortedMap<String, Project> projectMap = new TreeMap<>();
	private int profileId=0;
	private SortedMap<String, Profile> profileMap = new TreeMap<>();
	private int workerId=0;
	private SortedMap<String, Worker> workerMap = new TreeMap<>();

  // R1
  public void setHolidays(int... holidays) {
	  if (holiday.isEmpty()) {
		  for(int h : holidays) {
			  if(h>0 && h<366 && !holiday.contains(h))
				  holiday.add(h);
		  }
	}
  }
  
  public boolean isHoliday(int day) {
    return holiday.contains(day);
  }
  
  public void setFirstWeekDay(int weekDay) throws TimesheetException {
	  if (weekDay<0 || weekDay>6) {
		throw new TimesheetException();
	}
	  this.firstWeekDay=weekDay;
  }
  
  public int getWeekDay(int day) throws TimesheetException {
	  if (day<=0) {
		throw new TimesheetException();
	}

	  return (firstWeekDay+day-1)%7;
  }
  
  // R2
  public void createProject(String projectName, int maxHours) throws TimesheetException {
	  if (maxHours<0) {
		throw new TimesheetException();
	}
	  projectMap.put(projectName, new Project(projectName, maxHours));
  }
  
  public List<String> getProjects() {
        return projectMap.values().stream().sorted(Comparator.comparing(Project::getMaxHours).reversed().thenComparing(Project::getName)).map(p -> p.getName()+": "+p.getMaxHours()).toList();
  }
  
  public void createActivity(String projectName, String activityName) throws TimesheetException {
	  if(!projectMap.containsKey(projectName)) {
		  throw new TimesheetException();
	  }
	  projectMap.get(projectName).addActivity(activityName);
  }
  
  public void closeActivity(String projectName, String activityName) throws TimesheetException {
	  if(!projectMap.containsKey(projectName)) {
		  throw new TimesheetException();
	  }
	  if (!projectMap.get(projectName).getActivityMap().containsKey(activityName)) {
		  throw new TimesheetException();
	  }
	  projectMap.get(projectName).getActivityMap().get(activityName).setCompleted(true);
  }
  
  public List<String> getOpenActivities(String projectName) throws TimesheetException {
	  
	  if (!projectMap.containsKey(projectName)) {
		throw new TimesheetException();
		
	  }
	  
        return projectMap.get(projectName).getActivityMap().values().stream()
        		.filter(a -> !a.isCompleted()).map(a -> a.getActivityName())
        		.sorted().toList();
  }

  // R3
  public String createProfile(int... workHours) throws TimesheetException {
	  if (workHours.length!=7) {
		throw new TimesheetException();
	}
	  profileId++;
	  profileMap.put(String.valueOf(profileId), new Profile(profileId, workHours));
        return String.valueOf(profileId);
  }
  
  public String getProfile(String profileID) throws TimesheetException {
	  if(!profileMap.containsKey(profileID)) {
		  throw new TimesheetException();
	  }
	  List<Integer> tmp = profileMap.get(profileID).getWorkingHours();
	  
       return "Sun: "+tmp.get(0)+"; Mon: "+tmp.get(1)+"; Tue: "+tmp.get(2)+"; Wed: "+tmp.get(3)+"; Thu: "+tmp.get(4)+"; Fri: "+tmp.get(5)+"; Sat: "+tmp.get(6);
  }
  
  public String createWorker(String name, String surname, String profileID) throws TimesheetException {
      if (!profileMap.containsKey(profileID)) {
		throw new TimesheetException();
	}  
      workerId++;
      workerMap.put(String.valueOf(workerId), new Worker(String.valueOf(workerId), name, surname, profileMap.get(profileID)));
	  
      return String.valueOf(workerId);
  }
  
  public String getWorker(String workerID) throws TimesheetException {
	  if(!workerMap.containsKey(workerID))
        throw new TimesheetException();
        
        Worker tmp = workerMap.get(workerID);
        return tmp.getName() + " " + tmp.getSurname() + " (" + getProfile(String.valueOf(tmp.getProfile().getId())) +")";
  }
  
  // R4
  public void addReport(String workerID, String projectName, String activityName, int day, int workedHours) throws TimesheetException {

	  if(!workerMap.containsKey(workerID)) {
		  throw new TimesheetException();
	  }
	  if(!projectMap.containsKey(projectName)) {
		  throw new TimesheetException();
	  }
	  if(!projectMap.get(projectName).getActivityMap().containsKey(activityName)) {
		  throw new TimesheetException();
	  }
	  if(day<0 || isHoliday(day) || workedHours<0 || workerMap.get(workerID).getProfile().getWorkingHours().get(getWeekDay(day))<workedHours) {
		  throw new TimesheetException();
	  }

	  if(projectMap.get(projectName).getActivityMap().get(activityName).isCompleted()) {
		  throw new TimesheetException();
	  }

	  if(getProjectHours(projectName)+workedHours>projectMap.get(projectName).getMaxHours()) {
		  throw new TimesheetException();
	  }
	  projectMap.get(projectName).addReport(new Report(workerID, projectName, activityName, day, workedHours));
	  workerMap.get(workerID).addReport(new Report(workerID, projectName, activityName, day, workedHours));
  }
  
  public int getProjectHours(String projectName) throws TimesheetException {
        if(!projectMap.containsKey(projectName)) {
        	throw new TimesheetException();
        }
        return projectMap.get(projectName).getReports().stream().collect(Collectors.summingInt(r -> r.getWorkedHours()));
        
  }
  
  public int getWorkedHoursPerDay(String workerID, int day) throws TimesheetException {
        if (!workerMap.containsKey(workerID)) {
        	throw new TimesheetException();
		}
        if (day<=0) {
			throw new TimesheetException();
		}
        return (int) workerMap.get(workerID).getReports().stream().filter( r -> r.getDay()==day).map(r -> r.getWorkedHours()).collect(Collectors.summingInt(r -> r));

  }
  
  // R5
  public Map<String, Integer> countActivitiesPerWorker() {
	  
	  Map<String, Integer> tmpMap = new HashMap<>();
	  for(Worker w : workerMap.values()) {
		  Set<String> tmpSet = new HashSet<>();
		  
		  for(Report r : w.getReports()) {
			  tmpSet.add(r.getProjectName()+r.getActivityName());
			  
		  }
		  tmpMap.put(w.getId(), tmpSet.size());
	  }
	  return tmpMap;
  }
  
  public Map<String, Integer> getRemainingHoursPerProject() {
	  Map<String, Integer> tmpMap = new HashMap<>();
	  for(String p : projectMap.keySet()) {
		  try {
			tmpMap.put(projectMap.get(p).getName(), projectMap.get(p).getMaxHours()-getProjectHours(p));
		} catch (TimesheetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  }

        return tmpMap;
  }
  
  public Map<String, Map<String, Integer>> getHoursPerActivityPerProject() {
	  Map<String, Map<String, Integer>> tmpMap = new HashMap<>();
	  for(Project p : projectMap.values()) {
		  Map<String, Integer> tmpMap2 = new HashMap<>();
		  
		  for(Activity a : p.getActivityMap().values()) {
			  tmpMap2.put(a.getActivityName(), p.getReports().stream().filter(r -> r.getActivityName()==a.getActivityName()).collect(Collectors.summingInt(r -> r.getWorkedHours())));
			  
		  }
		  tmpMap.put(p.getName(), tmpMap2);
	  }
  return tmpMap;
  }
  
}