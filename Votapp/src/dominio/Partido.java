package dominio;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
public class Partido implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private static final long serialVersionUID = 1L;

	private String nombre;
	private String descripcion;
	private String presidente;
	private Date fechaFundacion;
		
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name="idPartido") // Es unidireccional
	private Set<FuenteDatos> fuenteDatos;
	
	@OneToMany(mappedBy = "partido")
	private Set<Lista> listas;
	
	@ManyToOne
	private Eleccion eleccion;
	
	@ManyToMany(mappedBy = "partidos")
	private Set<Departamento> departamentos;
	
	@ManyToMany(mappedBy = "partidos")
	private Set<Encuesta> encuestas;

	public Partido() {
		super();
		this.fuenteDatos = new LinkedHashSet<FuenteDatos>();
		this.listas = new LinkedHashSet<Lista>();
		this.departamentos = new LinkedHashSet<Departamento>();
		this.encuestas = new LinkedHashSet<Encuesta>();
	}   
	public int getId() {
		return this.id;
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

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getPresidente() {
		return presidente;
	}

	public void setPresidente(String presidente) {
		this.presidente = presidente;
	}

	public Date getFechaFundacion() {
		return fechaFundacion;
	}

	public void setFechaFundacion(Date fechaFundacion) {
		this.fechaFundacion = fechaFundacion;
	}

	public Set<FuenteDatos> getFuenteDatos() {
		return fuenteDatos;
	}

	public void setFuenteDatos(Set<FuenteDatos> fuenteDatos) {
		this.fuenteDatos = fuenteDatos;
	}

	public Set<Lista> getListas() {
		return listas;
	}

	public void setListas(Set<Lista> listas) {
		this.listas = listas;
	}

	public Eleccion getEleccion() {
		return eleccion;
	}

	public void setEleccion(Eleccion eleccion) {
		this.eleccion = eleccion;
	}

	public Set<Departamento> getDepartamentos() {
		return departamentos;
	}

	public void setDepartamentos(Set<Departamento> departamentos) {
		this.departamentos = departamentos;
	}

	public Set<Encuesta> getEncuestas() {
		return encuestas;
	}

	public void setEncuestas(Set<Encuesta> encuestas) {
		this.encuestas = encuestas;
	}

	@Override
	public String toString() {
		return "Partido[" + id + "]";
	}

}
