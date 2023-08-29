/**
 * 
 */
package university;

import java.util.Arrays;

/**
 * @author Filippo Forte
 *
 */
public class Student {
	
	private final static int MAX_ATTENDANCES=25;
	
	private String name;
	private String surname;
	private int studentID;
	private Course courses[] = new Course[MAX_ATTENDANCES];
	private int coursesMarks[] = new int[MAX_ATTENDANCES];;
	private int numberOfCoursesAttended;
	
	public Student() {
		this.name="";
		this.surname="";
		this.studentID=0;
		this.numberOfCoursesAttended=0;
	}
	
	public int getNumberOfCousesAttended() {
		return numberOfCoursesAttended;
	}
	public Course[] getCourses() {
		return courses;
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
	public int getStudentID() {
		return studentID;
	}
	public void setStudentID(int studentID) {
		this.studentID = studentID;
	}
	public void attendNewCourse(Course c) {
		
		this.courses[this.numberOfCoursesAttended]=c;
		this.coursesMarks[this.numberOfCoursesAttended]=0;
		this.numberOfCoursesAttended++;
		
	}
	public void setMark(int courseId, int grade) {
		for(int i=0; i<this.numberOfCoursesAttended; i++) {
			if(this.courses[i].getCourseId()==courseId) {
				this.coursesMarks[i]=grade;
			}
		}
	}
	public int[] getAllMarks() {
		return Arrays.copyOf(coursesMarks, numberOfCoursesAttended);
	}
	
	public int getMark(int courseId) {
		for(int i=0; i<this.getNumberOfCousesAttended(); i++) {
			if(this.getCourses()[i].getCourseId()==courseId) {
				return this.coursesMarks[i];
			}	
		}
		return -1;
	}

}
