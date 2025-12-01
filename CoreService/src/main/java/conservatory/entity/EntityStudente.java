package conservatory.entity;

public class EntityStudente {
	private String username;
	private String password;
	private int pin;
	private int idCDS;
		
	public EntityStudente (String username, String password, int pin, int idCDS){
			super();
			this.username = username;
			this.password = password;
			this.pin = pin;
			this.idCDS = idCDS;
		}
	
	public int getPin() {
		return pin;
	}

	public void setPin(int pin) {
		this.pin = pin;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public int getidCDS() {
		return idCDS;
	}

	public void setidCDS(int idCDS) {
		this.idCDS = idCDS;
	}
	
}
