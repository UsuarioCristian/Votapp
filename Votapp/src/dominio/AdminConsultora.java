package dominio;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class AdminConsultora extends Usuario {

	private static final long serialVersionUID = 1L;
		
	@OneToOne(mappedBy = "adminConsultora")
	private Consultora consultora;
	
	public Consultora getConsultora() {
		return consultora;
	}

	public void setConsultora(Consultora consultora) {
		this.consultora = consultora;
	}
	
	@Override
	public String toString() {		
		return super.toString();
	}

}
