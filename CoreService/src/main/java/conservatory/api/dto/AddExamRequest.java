package conservatory.api.dto;

public class AddExamRequest {
    private int vote;
    private boolean honors;
    private String notes;
    private String courseCode;
    private String username;

    public int getVote() { return vote; }
    public void setVote(int vote) { this.vote = vote; }
    public boolean isHonors() { return honors; }
    public void setHonors(boolean honors) { this.honors = honors; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    public String getCourseCode() { return courseCode; }
    public void setCourseCode(String courseCode) { this.courseCode = courseCode; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
}
