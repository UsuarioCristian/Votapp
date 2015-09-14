package dominio;

import java.io.Serializable;

import javax.persistence.*;

import utiles.TipoFuente;

@NamedQueries({
	@NamedQuery(name = "FuenteDatos.getFuentesByIdEleccion",
			query = "SELECT f from FuenteDatos f WHERE f.eleccion.id = :idEleccion")
})

@Entity
public class FuenteDatos implements Serializable {
	   
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private static final long serialVersionUID = 1L;
	
	private String url;
	
	@Enumerated(EnumType.STRING)
	private TipoFuente tipo;
	
	@ManyToOne
	private Departamento departamento;

	@ManyToOne
	private Eleccion eleccion;
	
	public FuenteDatos() {
		super();
	}   
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public TipoFuente getTipo() {
		return tipo;
	}

	public void setTipo(TipoFuente tipo) {
		this.tipo = tipo;
	}

	public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}
	
	public Eleccion getEleccion() {
		return eleccion;
	}

	public void setEleccion(Eleccion eleccion) {
		this.eleccion = eleccion;
	}

}
