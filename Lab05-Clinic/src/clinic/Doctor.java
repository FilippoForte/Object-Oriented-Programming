package clinic;

public class Doctor extends Patient{
	
	private int badgeID;
	private String specialization;
	
	
	public Doctor(String sSN, String firstName, String lastName, int badgeID, String specialization) {
		super(sSN, firstName, lastName);
		this.badgeID = badgeID;
		this.specialization = specialization;
	}


	public int getBadgeID() {
		return badgeID;
	}


	public void setBadgeID(int badgeID) {
		this.badgeID = badgeID;
	}


	public String getSpecialization() {
		return specialization;
	}


	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	} 

	
	
}
