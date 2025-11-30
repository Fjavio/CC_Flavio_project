package conservatory.api.dto;

import java.util.List;

public class CloseReportRequest {
    private List<StudentPinDto> studentPins;

    public List<StudentPinDto> getStudentPins() { return studentPins; }
    public void setStudentPins(List<StudentPinDto> studentPins) { this.studentPins = studentPins; }
}