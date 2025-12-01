package conservatory.api.dto;

public class CreateStudentRequest {
    private String username;
    private String password;
    private int pin;
    private int idCds;

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public int getPin() { return pin; }
    public void setPin(int pin) { this.pin = pin; }
    public int getIdCds() { return idCds; }
    public void setIdCds(int idCds) { this.idCds = idCds; }
}