package conservatory.entity;

public class EntityCorso {
	private String courseCode;
	private String courseName;
	private int cfu;
	public String teacherID;
	public String preOf;
	public String preFor;
	
	public EntityCorso(String courseCode, String courseName, int cfu, String teacherID, String preOf, String preFor) {
		super();
		this.courseCode = courseCode;
		this.courseName = courseName;
		this.cfu = cfu;
		this.teacherID = teacherID;
		this.preOf = preOf;
		this.preFor = preFor;
	}
	
	public String getcourseCode() {
		return courseCode;
	}
	
	public void setcourseCode(String courseCode) {
		this.courseCode = courseCode;
	}
	
	public String getcourseName() {
		return courseName;
	}
	
	public void setcourseName(String courseName) {
		this.courseName = courseName;
	}
	
	public int getCfu() {
		return cfu;
	}
	
	public void setCfu(int cfu) {
		this.cfu = cfu;
	}
	
	public String getteacherID() {
		return teacherID;
	}
	
	public void setteacherID(String teacherID) {
		this.teacherID = teacherID;
	}
	
	public String getpreOf() {
		return preOf;
	}
	
	public void setpreOf(String preOf) {
		this.preOf = preOf;
	}
	
	public String getpreFor() {
		return preFor;
	}
	
	public void setpreFor(String preFor) {
		this.preFor = preFor;
	}
}
