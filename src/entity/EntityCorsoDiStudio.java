package entity;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

public class EntityCorsoDiStudio {
	private String codiceCds;
	private String nomeCds;
	private Date annoAccademico;
	private int creditiTotali;
	private List<EntityCorso> corsiContenuti;
	
	public EntityCorsoDiStudio (String codiceCds, String nomeCds, Date annoAccademico, int creditiTotali){
		super();
		this.codiceCds = codiceCds;
		this.nomeCds = nomeCds;
		this.annoAccademico = annoAccademico;
		this.creditiTotali = creditiTotali;
		this.corsiContenuti = new ArrayList<>();
	}
	
	public String getCodiceCds() {
		return codiceCds;
	}

	public void setPIN(String codiceCds) {
		this.codiceCds = codiceCds;
	}
	
	public String getNomeCds() {
		return nomeCds;
	}

	public void setNomeCds(String nomeCds) {
		this.nomeCds = nomeCds;
	}
	
	public Date getAnnoAccademico() {
		return annoAccademico;
	}

	public void setAnnoAccademico(Date annoAccademico) {
		this.annoAccademico = annoAccademico;
	}
	
	public int getCreditiTotali() {
		return creditiTotali;
	}

	public void setCreditiTotali(int creditiTotali) {
		this.creditiTotali = creditiTotali;
	}
	
	public List<EntityCorso> getCorsiContenuti() {
        return corsiContenuti;
    }
	
	public void setCorsiContenuti(List<EntityCorso> corsiContenuti) {
		this.corsiContenuti = corsiContenuti;
	}
	
}

 // entitycanto (e le altre specializz) se le fai public le vuole su un file separato
// se ti serve a qualcosa fallo

class entitycanto extends EntityCorsoDiStudio{
	public entitycanto(String codiceCds, String nomeCds, Date annoAccademico, int creditiTotali) {
		super(codiceCds, nomeCds, annoAccademico, creditiTotali);
	}
}

class entitycomposizione extends EntityCorsoDiStudio{
	public entitycomposizione(String codiceCds, String nomeCds, Date annoAccademico, int creditiTotali) {
		super(codiceCds, nomeCds, annoAccademico, creditiTotali);
	}
}

class entitymusicadinsieme extends EntityCorsoDiStudio{
	public entitymusicadinsieme(String codiceCds, String nomeCds, Date annoAccademico, int creditiTotali) {
		super(codiceCds, nomeCds, annoAccademico, creditiTotali);
	}
}

class entitystrumento extends EntityCorsoDiStudio{
	public entitystrumento(String codiceCds, String nomeCds, Date annoAccademico, int creditiTotali) {
		super(codiceCds, nomeCds, annoAccademico, creditiTotali);
	}
}

class entitydidatticamusica extends EntityCorsoDiStudio{
	public entitydidatticamusica(String codiceCds, String nomeCds, Date annoAccademico, int creditiTotali) {
		super(codiceCds, nomeCds, annoAccademico, creditiTotali);
	}
}

class entitycantolirico extends entitycanto{
	public entitycantolirico(String codiceCds, String nomeCds, Date annoAccademico, int creditiTotali) {
		super(codiceCds, nomeCds, annoAccademico, creditiTotali);
	}
}

class entitycantojazz extends entitycanto{
	public entitycantojazz(String codiceCds, String nomeCds, Date annoAccademico, int creditiTotali) {
		super(codiceCds, nomeCds, annoAccademico, creditiTotali);
	}
}

class entitypianoforte extends entitystrumento{
	public entitypianoforte(String codiceCds, String nomeCds, Date annoAccademico, int creditiTotali) {
		super(codiceCds, nomeCds, annoAccademico, creditiTotali);
	}
}

class entityviolino extends entitystrumento{
	public entityviolino(String codiceCds, String nomeCds, Date annoAccademico, int creditiTotali) {
		super(codiceCds, nomeCds, annoAccademico, creditiTotali);
	}
}

class entitychitarra extends entitystrumento{
	public entitychitarra(String codiceCds, String nomeCds, Date annoAccademico, int creditiTotali) {
		super(codiceCds, nomeCds, annoAccademico, creditiTotali);
	}
}


