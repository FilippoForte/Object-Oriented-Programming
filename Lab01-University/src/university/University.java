package university;
import java.util.logging.Logger;

/**
 * This class represents a university education system.
 * 
 * It manages students and courses.
 *
 */
public class University {

	private final static int MAX_STUDENTS=1000;
	private final static int FIRST_STUDENT_ID=10000;
	private final static int MAX_COURSES=50;
	private final static int FIRST_COURSE_ID=10;
	
// R1
	private String universityName;
	private String rectorName;
	private String rectorSurname;
	private Student[] students = new Student[MAX_STUDENTS];
	private int nStudents;
	private Course courses[] = new Course[MAX_COURSES];;
	private int nCourses;
	/**
	 * Constructor
	 * @param name name of the university
	 */
	public University(String name){
		// Example of logging
		// logger.info("Creating extended university object");
		this.universityName=name;
		this.nStudents = 0;
		this.nCourses = 0;
	}
	
	/**
	 * Getter for the name of the university
	 * 
	 * @return name of university
	 */
	public String getName(){
		return this.universityName;
	}
	
	/**
	 * Defines the rector for the university
	 * 
	 * @param first first name of the rector
	 * @param last	last name of the rector
	 */
	public void setRector(String first, String last){
		this.rectorName=first;
		this.rectorSurname=last;
	}
	
	/**
	 * Retrieves the rector of the university
	 * 
	 * @return name of the rector
	 */
	public String getRector(){
		//TODO: to be implemented
		return this.rectorName + " " + this.rectorSurname;
	}
	
// R2
	/**
	 * Enrol a student in the university
	 * 
	 * @param first first name of the student
	 * @param last last name of the student
	 * 
	 * @return unique ID of the newly enrolled student
	 */
	public int enroll(String first, String last){
		this.students[nStudents] = new Student();	
		this.students[nStudents].setName(first);
		this.students[nStudents].setSurname(last);
		this.students[nStudents].setStudentID(FIRST_STUDENT_ID+nStudents);
		this.nStudents++;
		
		logger.info("New student enrolled: " + this.students[nStudents-1].getStudentID() + ", " + this.students[nStudents-1].getName() + " " +this.students[nStudents-1].getSurname());
		
		return this.students[nStudents-1].getStudentID();
	}
	
	/**
	 * Retrieves the information for a given student
	 * 
	 * @param id the ID of the student
	 * 
	 * @return information about the student
	 */
	public String student(int id){
		
		for(int i=0; i<nStudents; ++i) {
			if(this.students[i].getStudentID()==id) {
				return this.students[i].getStudentID() + " " + this.students[i].getName() + " " + this.students[i].getSurname(); 
			}
		}
		
		return "Student Not Found";
	}
	
// R3
	/**
	 * Activates a new course with the given teacher
	 * 
	 * @param title title of the course
	 * @param teacher name of the teacher
	 * 
	 * @return the unique code assigned to the course
	 */
	public int activate(String title, String teacher){
		this.courses[this.nCourses] = new Course();
		this.courses[this.nCourses].setCourseName(title);
		this.courses[this.nCourses].setTeacherName(teacher);
		this.courses[this.nCourses].setCourseId(10+this.nCourses);
		this.nCourses++;
		
		logger.info("New course activated: " + this.courses[this.nCourses-1].getCourseId() +", " + this.courses[this.nCourses-1].getCourseName() + " " + this.courses[this.nCourses-1].getTeacherName());
		
		return this.courses[this.nCourses-1].getCourseId();
	}
	
	/**
	 * Retrieve the information for a given course.
	 * 
	 * The course information is formatted as a string containing 
	 * code, title, and teacher separated by commas, 
	 * e.g., {@code "10,Object Oriented Programming,James Gosling"}.
	 * 
	 * @param code unique code of the course
	 * 
	 * @return information about the course
	 */
	public String course(int code){

		for(int i=0; i<this.nCourses; i++) {
			if(this.courses[i].getCourseId()==code)
				return this.courses[i].getCourseId() + "," + this.courses[i].getCourseName() + "," + this.courses[i].getTeacherName();
 		}

		return null;
	}
	
// R4
	/**
	 * Register a student to attend a course
	 * @param studentID id of the student
	 * @param courseCode id of the course
	 */
	public void register(int studentID, int courseCode){
		
		this.students[studentID-FIRST_STUDENT_ID].attendNewCourse(this.courses[courseCode-FIRST_COURSE_ID]);
		this.courses[courseCode-FIRST_COURSE_ID].enrollNewStudent(this.students[studentID-FIRST_STUDENT_ID]);
		
		logger.info("Student " + studentID + " signed up for course " + courseCode);
	}
	
	/**
	 * Retrieve a list of attendees
	 * 
	 * @param courseCode unique id of the course
	 * @return list of attendees separated by "\n"
	 */

	public String listAttendees(int courseCode){
		StringBuffer ans = new StringBuffer();
		
		for (Student s : this.courses[courseCode-FIRST_COURSE_ID].getStudents()) {
			if(s != null) {
				ans.append(this.student(s.getStudentID()));
				ans.append("\n");
			}
		}
			
		return ans.toString();
	}

	/**
	 * Retrieves the study plan for a student.
	 * 
	 * The study plan is reported as a string having
	 * one course per line (i.e. separated by '\n').
	 * The courses are formatted as describe in method {@link #course}
	 * 
	 * @param studentID id of the student
	 * 
	 * @return the list of courses the student is registered for
	 */
	
	public String studyPlan(int studentID){
		StringBuffer ans = new StringBuffer();
		
		for( Course c : this.students[studentID-FIRST_STUDENT_ID].getCourses() ) {
			if(c!=null) {
				ans.append(course(c.getCourseId()));
				ans.append("\n");
			}
		}
		return ans.toString();
	}

// R5
	/**
	 * records the grade (integer 0-30) for an exam can 
	 * 
	 * @param studentId the ID of the student
	 * @param courseID	course code 
	 * @param grade		grade ( 0-30)
	 */
	public void exam(int studentId, int courseID, int grade) {
		this.students[studentId-FIRST_STUDENT_ID].setMark(courseID, grade);
		
		logger.info("Student " + studentId + " took an exam in course " + courseID + " with grade " + grade);

	}

	/**
	 * Computes the average grade for a student and formats it as a string
	 * using the following format 
	 * 
	 * {@code "Student STUDENT_ID : AVG_GRADE"}. 
	 * 
	 * If the student has no exam recorded the method
	 * returns {@code "Student STUDENT_ID hasn't taken any exams"}.
	 * 
	 * @param studentId the ID of the student
	 * @return the average grade formatted as a string.
	 */
	public String studentAvg(int studentId) {
		float sum=0, n=0;
		for( int mark : this.students[studentId-FIRST_STUDENT_ID].getAllMarks() ) {
			if(mark > 0) {
				sum+=mark;
				n++;
			}
		}
		
		if(n>0)
			return "Student " + studentId + " : " + sum/n;
		
		return "Student " + studentId + " hasn't taken any exams";
	}
	
	/**
	 * Computes the average grades of all students that took the exam for a given course.
	 * 
	 * The format is the following: 
	 * {@code "The average for the course COURSE_TITLE is: COURSE_AVG"}.
	 * 
	 * If no student took the exam for that course it returns {@code "No student has taken the exam in COURSE_TITLE"}.
	 * 
	 * @param courseId	course code 
	 * @return the course average formatted as a string
	 */
	public String courseAvg(int courseId) {
		float n=0, sum=0;
		
		for(Student s : this.courses[courseId-FIRST_COURSE_ID].getStudents()) {
			if(s!=null && s.getMark(courseId)!=0) {
				sum+=s.getMark(courseId);
				n++;
			}
		}
			
		if(n>0)
			return "The average for the course " + this.courses[courseId-FIRST_COURSE_ID].getCourseName() + " is: " + sum/n;
		return "No student has taken the exam in " + this.courses[courseId-FIRST_COURSE_ID].getCourseName();
	}
	

// R6
	/**
	 * Retrieve information for the best students to award a price.
	 * 
	 * The students' score is evaluated as the average grade of the exams they've taken. 
	 * To take into account the number of exams taken and not only the grades, 
	 * a special bonus is assigned on top of the average grade: 
	 * the number of taken exams divided by the number of courses the student is enrolled to, multiplied by 10.
	 * The bonus is added to the exam average to compute the student score.
	 * 
	 * The method returns a string with the information about the three students with the highest score. 
	 * The students appear one per row (rows are terminated by a new-line character {@code '\n'}) 
	 * and each one of them is formatted as: {@code "STUDENT_FIRSTNAME STUDENT_LASTNAME : SCORE"}.
	 * 
	 * @return info on the best three students.
	 */
	public String topThreeStudents() {
		
		float sum=0, n=0, avg=0, avg1=0, avg2=0, avg3=0, avgTmp=0;
		Student id1=null, id2=null, id3=null, idTmp=null, currentId=null;

		for(Student s : this.students) {
			avg=0;
			sum=0;
			n=0;
			if(s!=null) {
				for(int mark : s.getAllMarks()) {
					if(mark>0) {
						sum+=mark;
						n++;
					}
				}
				
				avg=sum/n+((n/s.getNumberOfCousesAttended())*10);		
				currentId=s;
			
				if(avg>avg1) {
					idTmp=id1;
					avgTmp=avg1;
					
					id1=currentId;
					avg1=avg;
					
					currentId=idTmp;
					avg=avgTmp;
				}
				if(avg>avg2) {
					idTmp=id2;
					avgTmp=avg2;
					
					id2=currentId;
					avg2=avg;
					
					currentId=idTmp;
					avg=avgTmp;
				}
				if(avg>avg3) {
					idTmp=id3;
					avgTmp=avg3;
					
					id3=currentId;
					avg3=avg;
					
					currentId=idTmp;
					avg=avgTmp;
				}
			}
		}
		
		if(id3!=null)
			return id1.getName()+" "+id1.getSurname()+": "+avg1+"\n"+id2.getName()+" "+id2.getSurname()+": "+avg2+"\n"+id3.getName()+" "+id3.getSurname()+": "+avg3;
		else if(id2!=null)
			return id1.getName()+" "+id1.getSurname()+": "+avg1+"\n"+id2.getName()+" "+id2.getSurname()+": "+avg2;
		else
			return id1.getName()+" "+id1.getSurname()+": "+avg1;
		}

// R7
    /**
     * This field points to the logger for the class that can be used
     * throughout the methods to log the activities.
     */
    private final static Logger logger = Logger.getLogger("University");

}
