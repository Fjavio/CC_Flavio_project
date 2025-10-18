package entity;

import java.util.Date;

public class EntityEsame {
	private int vote;
	private boolean honors;
	private String teacherNotes;
	private Date passingDate;
	private String reportCode;
	private String courseCode;
	private String username;
	
	public EntityEsame(int vote, boolean honors, String teacherNotes, Date passingDate, String reportCode, String courseCode, String username) {
		super();
		this.vote = vote;
		this.honors = honors;
		this.teacherNotes = teacherNotes;
		this.passingDate = passingDate;
		this.reportCode = reportCode;
		this.courseCode = courseCode;
		this.username = username;
	}
	public int getvote() {
		return vote;
	}
	public void setvote(int vote) {
		this.vote = vote;
	}
	public boolean gethonors() {
		return honors;
	}
	public void sethonors(boolean honors) {
		this.honors = honors;
	}
	public String getteacherNotes() {
		return teacherNotes;
	}
	public void setteacherNotes(String teacherNotes) {
		this.teacherNotes = teacherNotes;
	}
	public Date getpassingDate() {
	return passingDate;
    }
    public void setpassingDate(Date passingDate) {
	  this.passingDate = passingDate;
    }
	public String getreportCode() {
		return reportCode;
	}
	public void setreportCode(String reportCode) {
		this.reportCode = reportCode;
	}
	public String getcourseCode() {
		return courseCode;
	}
	public void setcourseCode(String courseCode) {
		this.courseCode = courseCode;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
    
}
