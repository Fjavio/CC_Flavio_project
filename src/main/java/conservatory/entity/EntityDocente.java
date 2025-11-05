package conservatory.entity;

public class EntityDocente {
	private String teacherName;
	private String teacherSurname;
	private String ID;
	
	public EntityDocente(String teacherName, String teacherSurname, String ID) {
		super();
		this.teacherName = teacherName;
		this.teacherSurname = teacherSurname;
		this.ID = ID;
	}
	
	public String getteacherName() {
		return teacherName;
	}
	public void setteacherName(String teacherName) {
		this.teacherName = teacherName;
	}
	public String getteacherSurname() {
		return teacherSurname;
	}
	public void setteacherSurname(String teacherSurname) {
		this.teacherSurname = teacherSurname;
	}
	public String getID() {
		return ID;
	}
	public void setID(String ID) {
		this.ID = ID;
	}
}
