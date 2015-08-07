package dominio;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Encuestador extends Usuario {
	
	private static final long serialVersionUID = 1L;
	
	@ManyToOne(cascade = CascadeType.REFRESH)
	private Consultora consultora;
	
	@OneToMany(mappedBy = "encuestador")
	private Set<Emergencia> emergencias;
	
	public Encuestador() {
		this.emergencias = new LinkedHashSet<Emergencia>();
	}	

	public Consultora getConsultora() {
		return consultora;
	}

	public void setConsultora(Consultora consultora) {
		this.consultora = consultora;
	}

	public Set<Emergencia> getEmergencias() {
		return emergencias;
	}

	public void setEmergencias(Set<Emergencia> emergencias) {
		this.emergencias = emergencias;
	}
	
}
