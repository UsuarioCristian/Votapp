package dominio;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@NamedQueries({
	@NamedQuery(name = "Consultora.getConsultoraByUsername", 
			query = "SELECT c FROM Consultora c WHERE c.adminConsultora.username = :username")
})
@Entity
public class Consultora implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String nombre;
	private Date fechaFundacion;
	private String descripcion;
	
	@OneToOne(cascade = CascadeType.ALL)
	private AdminConsultora adminConsultora;
	
	@OneToMany(mappedBy = "consultora")
	private Set<Encuestador> encuestadores;
	
	@OneToMany(mappedBy = "consultora")
	private Set<Encuesta> encuestas;

	public Consultora(int id, String nombre, Date fechaFundacion, String descripcion, AdminConsultora adminConsultora) {
		this.id = id;
		this.nombre = nombre;
		this.fechaFundacion = fechaFundacion;
		this.descripcion = descripcion;
		this.adminConsultora = adminConsultora;
		this.encuestadores = new LinkedHashSet<Encuestador>();
		this.encuestas = new LinkedHashSet<Encuesta>();
	}
	
	public Consultora(){
		super();
		this.encuestadores = new LinkedHashSet<Encuestador>();		
		this.encuestas = new LinkedHashSet<Encuesta>();
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Date getFechaFundacion() {
		return fechaFundacion;
	}
	public void setFechaFundacion(Date fechaFundacion) {
		this.fechaFundacion = fechaFundacion;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public AdminConsultora getAdminConsultora() {
		return adminConsultora;
	}

	public void setAdminConsultora(AdminConsultora adminConsultora) {
		this.adminConsultora = adminConsultora;
	}	

	public Set<Encuestador> getEncuestadores() {
		return encuestadores;
	}

	public void setEncuestadores(Set<Encuestador> encuestadores) {
		this.encuestadores = encuestadores;
	}

	public Set<Encuesta> getEncuestas() {
		return encuestas;
	}

	public void setEncuestas(Set<Encuesta> encuestas) {
		this.encuestas = encuestas;
	}

	@Override
	public String toString() {
		return "Consultora[" + id + "]";
	}

}
