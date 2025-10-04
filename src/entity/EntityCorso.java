package entity;

public class EntityCorso {
	private String courseCode;
	private String courseName;
	private int CFU;
	public String teacherID;
	public String preOf;
	public String preFor;
	
	public EntityCorso(String courseCode, String courseName, int CFU, String teacherID, String preOf, String preFor) {
		super();
		this.courseCode = courseCode;
		this.courseName = courseName;
		this.CFU = CFU;
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
	
	public int getCFU() {
		return CFU;
	}
	
	public void setCFU(int CFU) {
		this.CFU = CFU;
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
