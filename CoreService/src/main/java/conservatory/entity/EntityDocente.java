package conservatory.entity;

public class EntityDocente {
	private String teacherName;
	private String teacherSurname;
	private String teacherID;
	
	public EntityDocente(String teacherID, String teacherName, String teacherSurname) {
		super();
		this.teacherID = teacherID;
		this.teacherName = teacherName;
		this.teacherSurname = teacherSurname;
	}
	
	public String getTeacherName() {
		return teacherName;
	}
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
	public String getTeacherSurname() {
		return teacherSurname;
	}
	public void setTeacherSurname(String teacherSurname) {
		this.teacherSurname = teacherSurname;
	}
	public String getTeacherID() {
		return teacherID;
	}
	public void setTeacherID(String teacherID) {
		this.teacherID = teacherID;
	}
}
