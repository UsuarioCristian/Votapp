package dominio;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Encuestador extends Usuario {
	
	private static final long serialVersionUID = 1L;
	
	@ManyToOne(cascade = CascadeType.REFRESH)
	private Consultora consultora;

	public Consultora getConsultora() {
		return consultora;
	}

	public void setConsultora(Consultora consultora) {
		this.consultora = consultora;
	}

}
