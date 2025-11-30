package conservatory.entity;
import java.util.Date;

public class EntityVerbale {
	private Date reportDate;
	private String reportCode;
	private String teacherID;
	
	public EntityVerbale(String reportCode, Date reportDate, String teacherID) {
		super();
		this.reportCode = reportCode;
		this.reportDate = reportDate;
		this.teacherID = teacherID;
	}
	public Date getreportDate() {
		return reportDate;
	}
	public void setreportDate(Date reportDate) {
		this.reportDate = reportDate;
	}
	public String getreportCode() {
		return reportCode;
	}
	public void setreportCode(String reportCode) {
		this.reportCode = reportCode;
	}
	public String getteacherID() {
		return teacherID;
	}
	public void setteacherID(String teacherID) {
		this.teacherID = teacherID;
	}
}
