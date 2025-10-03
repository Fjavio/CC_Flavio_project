package entity;
import java.util.Date;

public class EntityVerbale {
	private Date dataVerbale;
	private String codiceVerbale;
	private String matricolaDocente;
	
	public EntityVerbale(Date dataVerbale, String codiceVerbale, String matricolaDocente) {
		super();
		this.dataVerbale = dataVerbale;
		this.codiceVerbale = codiceVerbale;
		this.matricolaDocente = matricolaDocente;
	}
	public Date getDataVerbale() {
		return dataVerbale;
	}
	public void setDataVerbale(Date dataVerbale) {
		this.dataVerbale = dataVerbale;
	}
	public String getCodiceVerbale() {
		return codiceVerbale;
	}
	public void setCodiceVerbale(String codiceVerbale) {
		this.codiceVerbale = codiceVerbale;
	}
	public String getMatricolaDocente() {
		return matricolaDocente;
	}
	public void setMatricolaDocente(String matricolaDocente) {
		this.matricolaDocente = matricolaDocente;
	}
}
