package university;

public class Course {
	
	private final static int MAX_COURSE_STUDENTS=100;
	
	private String courseName;
	private String teacherName;
	private int courseId;
	private Student[] students = new Student[MAX_COURSE_STUDENTS];
	private int enrolledStudents;
	
	
	public Course() {
		this.courseName="";
		this.teacherName="";
		this.courseId=0;
		this.enrolledStudents=0;
	}
	
	

	public Student[] getStudents() {
		return students;
	}

	public void enrollNewStudent(Student studentId) {
		this.students[this.enrolledStudents] = studentId;
		this.enrolledStudents++;
	}

	public void setEnrolled(int enrolled) {
		this.enrolledStudents = enrolled;
	}

	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getTeacherName() {
		return teacherName;
	}
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
	public int getCourseId() {
		return courseId;
	}
	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}
	

}
