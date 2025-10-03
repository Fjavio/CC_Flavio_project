package entity;

public class EntityCorso {
	private String codiceCorso;
	private String denominazione;
	private int CFU;
	public String matricolaDocente;
	public String propDi;
	public String propA;
	
	public EntityCorso(String codiceCorso, String denominazione, int CFU, String matricolaDocente, String propDi, String propA) {
		super();
		this.codiceCorso = codiceCorso;
		this.denominazione = denominazione;
		this.CFU = CFU;
		this.matricolaDocente = matricolaDocente;
		this.propDi = propDi;
		this.propA = propA;
	}
	
	public String getCodiceCorso() {
		return codiceCorso;
	}
	
	public void setCodiceCorso(String codiceCorso) {
		this.codiceCorso = codiceCorso;
	}
	
	public String getDenominazione() {
		return denominazione;
	}
	
	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}
	
	public int getCFU() {
		return CFU;
	}
	
	public void setCFU(int CFU) {
		this.CFU = CFU;
	}
	
	public String getMatricolaDocente() {
		return matricolaDocente;
	}
	
	public void setMatricolaDocente(String matricolaDocente) {
		this.matricolaDocente = matricolaDocente;
	}
	
	public String getPropDi() {
		return propDi;
	}
	
	public void setPropDi(String propDi) {
		this.propDi = propDi;
	}
	
	public String getPropA() {
		return propA;
	}
	
	public void setPropA(String propA) {
		this.propA = propA;
	}
}
