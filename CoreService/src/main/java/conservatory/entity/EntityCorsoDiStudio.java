package conservatory.entity;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

public class EntityCorsoDiStudio {
	private String codeCds;
	private String nameCds;
	private Date academicYear;
	private int totalCredits;
	private List<EntityCorso> coursesOffered;
	
	public EntityCorsoDiStudio (String codeCds, String nameCds, Date academicYear, int totalCredits){
		super();
		this.codeCds = codeCds;
		this.nameCds = nameCds;
		this.academicYear = academicYear;
		this.totalCredits = totalCredits;
		this.coursesOffered = new ArrayList<>();
	}
	
	public String getcodeCds() {
		return codeCds;
	}

	public void setPIN(String codeCds) {
		this.codeCds = codeCds;
	}
	
	public String getnameCds() {
		return nameCds;
	}

	public void setnameCds(String nameCds) {
		this.nameCds = nameCds;
	}
	
	public Date getacademicYear() {
		return academicYear;
	}

	public void setacademicYear(Date academicYear) {
		this.academicYear = academicYear;
	}
	
	public int gettotalCredits() {
		return totalCredits;
	}

	public void settotalCredits(int totalCredits) {
		this.totalCredits = totalCredits;
	}
	
	public List<EntityCorso> getcoursesOffered() {
        return coursesOffered;
    }
	
	public void setcoursesOffered(List<EntityCorso> coursesOffered) {
		this.coursesOffered = coursesOffered;
	}
	
}

class entitysinging extends EntityCorsoDiStudio{
	public entitysinging(String codeCds, String nameCds, Date academicYear, int totalCredits) {
		super(codeCds, nameCds, academicYear, totalCredits);
	}
}

class entitycomposition extends EntityCorsoDiStudio{
	public entitycomposition(String codeCds, String nameCds, Date academicYear, int totalCredits) {
		super(codeCds, nameCds, academicYear, totalCredits);
	}
}

class entityensemblemusic extends EntityCorsoDiStudio{
	public entityensemblemusic(String codeCds, String nameCds, Date academicYear, int totalCredits) {
		super(codeCds, nameCds, academicYear, totalCredits);
	}
}

class entityinstruments extends EntityCorsoDiStudio{
	public entityinstruments(String codeCds, String nameCds, Date academicYear, int totalCredits) {
		super(codeCds, nameCds, academicYear, totalCredits);
	}
}

class entitymusiceducation extends EntityCorsoDiStudio{
	public entitymusiceducation(String codeCds, String nameCds, Date academicYear, int totalCredits) {
		super(codeCds, nameCds, academicYear, totalCredits);
	}
}

class entitysinginglyrical extends entitysinging{
	public entitysinginglyrical(String codeCds, String nameCds, Date academicYear, int totalCredits) {
		super(codeCds, nameCds, academicYear, totalCredits);
	}
}

class entitysingingjazz extends entitysinging{
	public entitysingingjazz(String codeCds, String nameCds, Date academicYear, int totalCredits) {
		super(codeCds, nameCds, academicYear, totalCredits);
	}
}

class entitypiano extends entityinstruments{
	public entitypiano(String codeCds, String nameCds, Date academicYear, int totalCredits) {
		super(codeCds, nameCds, academicYear, totalCredits);
	}
}

class entityviolin extends entityinstruments{
	public entityviolin(String codeCds, String nameCds, Date academicYear, int totalCredits) {
		super(codeCds, nameCds, academicYear, totalCredits);
	}
}

class entityguitar extends entityinstruments{
	public entityguitar(String codeCds, String nameCds, Date academicYear, int totalCredits) {
		super(codeCds, nameCds, academicYear, totalCredits);
	}
}


