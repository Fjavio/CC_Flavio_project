package conservatory.api.dto;

public class CreaDocenteRequest {
    private String teacherName;
    private String teacherSurname;
    private String teacherID;

    public String getTeacherName() { return teacherName; }
    public void setTeacherName(String teacherName) { this.teacherName = teacherName; }
    public String getTeacherSurname() { return teacherSurname; }
    public void setTeacherSurname(String teacherSurname) { this.teacherSurname = teacherSurname; }
    public String getTeacherID() { return teacherID; }
    public void setTeacherID(String teacherID) { this.teacherID = teacherID; }
}