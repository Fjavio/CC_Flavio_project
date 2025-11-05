package conservatory.api.dto;

import java.sql.Date;

public class OpenReportRequest {
    private String reportCode;
    private Date reportDate;
    private String teacherId;

    public String getReportCode() { return reportCode; }
    public void setReportCode(String reportCode) { this.reportCode = reportCode; }
    public Date getReportDate() { return reportDate; }
    public void setReportDate(Date reportDate) { this.reportDate = reportDate; }
    public String getTeacherId() { return teacherId; }
    public void setTeacherId(String teacherId) { this.teacherId = teacherId; }
}