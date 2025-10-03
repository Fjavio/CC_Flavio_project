package entity;

public class EntityDocente {
	private String nomeDocente;
	private String cognomeDocente;
	private String matricola;
	
	public EntityDocente(String nomeDocente, String cognomeDocente, String matricola) {
		super();
		this.nomeDocente = nomeDocente;
		this.cognomeDocente = cognomeDocente;
		this.matricola = matricola;
	}
	
	public String getNomeDocente() {
		return nomeDocente;
	}
	public void setNomeDocente(String nomeDocente) {
		this.nomeDocente = nomeDocente;
	}
	public String getCognomeDocente() {
		return cognomeDocente;
	}
	public void setCognomeDocente(String cognomeDocente) {
		this.cognomeDocente = cognomeDocente;
	}
	public String getMatricola() {
		return matricola;
	}
	public void setMatricola(String matricola) {
		this.matricola = matricola;
	}
}
