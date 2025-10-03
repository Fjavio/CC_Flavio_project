package entity;

import java.util.Date;

public class EntityEsame {
	private int voto;
	private boolean lode;
	private String noteDocente;
	private Date dataSuperamento;
	private String codiceVerbale;
	private String codiceCorso;
	private String username;
	
	public EntityEsame(int voto, boolean lode, String noteDocente, Date dataSuperamento, String codiceVerbale, String codiceCorso, String username) {
		super();
		this.voto = voto;
		this.lode = lode;
		this.noteDocente = noteDocente;
		this.dataSuperamento = dataSuperamento;
		this.codiceVerbale = codiceVerbale;
		this.codiceCorso = codiceCorso;
		this.username = username;
	}
	public int getVoto() {
		return voto;
	}
	public void setVoto(int voto) {
		this.voto = voto;
	}
	public boolean getLode() {
		return lode;
	}
	public void setLode(boolean lode) {
		this.lode = lode;
	}
	public String getNoteDocente() {
		return noteDocente;
	}
	public void setNoteDocente(String noteDocente) {
		this.noteDocente = noteDocente;
	}
	public Date getDataSuperamento() {
	return dataSuperamento;
    }
    public void setDataSuperamento(Date dataSuperamento) {
	  this.dataSuperamento = dataSuperamento;
    }
	public String getCodiceVerbale() {
		return codiceVerbale;
	}
	public void setCodiceVerbale(String codiceVerbale) {
		this.codiceVerbale = codiceVerbale;
	}
	public String getCodiceCorso() {
		return codiceCorso;
	}
	public void setCodiceCorso(String codiceCorso) {
		this.codiceCorso = codiceCorso;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
    
}
