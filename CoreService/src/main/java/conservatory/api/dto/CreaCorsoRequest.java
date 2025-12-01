package conservatory.api.dto;

public class CreaCorsoRequest {
    private String courseCode;
    private String courseName;
    private int cfu;
    private String preOf;
    private String preFor;

    public String getCourseCode() { return courseCode; }
    public void setCourseCode(String courseCode) { this.courseCode = courseCode; }
    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }
    public int getCfu() { return cfu; }
    public void setCfu(int cfu) { this.cfu = cfu; }
    public String getPreOf() { return preOf; }
    public void setPreOf(String preOf) { this.preOf = preOf; }
    public String getPreFor() { return preFor; }
    public void setPreFor(String preFor) { this.preFor = preFor; }
}