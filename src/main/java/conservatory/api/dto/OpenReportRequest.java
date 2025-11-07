package conservatory.api.dto;
import java.sql.Date;

public class OpenReportRequest {
    private String reportCode;
    private Date reportDate;
    private String teacherID;

    public String getReportCode() { return reportCode; }
    public void setReportCode(String reportCode) { this.reportCode = reportCode; }
    public Date getReportDate() { return reportDate; }
    public void setReportDate(Date reportDate) { this.reportDate = reportDate; }
    public String getTeacherID() { return teacherID; }
    public void setTeacherID(String teacherID) { this.teacherID = teacherID; }
}