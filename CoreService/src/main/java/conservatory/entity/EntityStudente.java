package conservatory.entity;

public class EntityStudente {
	private String username;
	private String password;
	private int PIN;
	private int idCDS;
		
	public EntityStudente (String username, String password, int PIN, int idCDS){
			super();
			this.username = username;
			this.password = password;
			this.PIN = PIN;
			this.idCDS = idCDS;
		}
	
	public int getPIN() {
		return PIN;
	}

	public void setPIN(int PIN) {
		this.PIN = PIN;
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
