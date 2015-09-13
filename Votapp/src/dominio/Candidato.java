package dominio;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.*;

import utiles.TipoCargo;

@Entity
public class Candidato implements Serializable {
	   
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private static final long serialVersionUID = 1L;
	
	private String nombre;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="idImagen") // Es unidireccional
	protected Imagen imagen;
	
	@Column(length=100000)
	private String biografia;

	private int edad;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name="idCandidato") // Es unidireccional
	private Set<FuenteDatos> fuenteDatos;
	
	@ManyToMany(mappedBy = "candidatos")	
	private Set<Lista> listas;
	
	@Enumerated(EnumType.STRING)
	private TipoCargo cargo;
	
	@ManyToOne
	private Eleccion eleccion;
	
	@ManyToMany(mappedBy = "candidatos")
	private Set<Encuesta> encuestas;

	public Candidato() {
		super();
		this.fuenteDatos = new LinkedHashSet<FuenteDatos>();
		this.listas = new LinkedHashSet<Lista>();
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

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
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

	public TipoCargo getCargo() {
		return cargo;
	}

	public void setCargo(TipoCargo cargo) {
		this.cargo = cargo;
	}

	public Eleccion getEleccion() {
		return eleccion;
	}

	public void setEleccion(Eleccion eleccion) {
		this.eleccion = eleccion;
	}

	public Set<Encuesta> getEncuestas() {
		return encuestas;
	}

	public void setEncuestas(Set<Encuesta> encuestas) {
		this.encuestas = encuestas;
	}
	
	public Imagen getImagen() {
		return imagen;
	}

	public void setImagen(Imagen imagen) {
		this.imagen = imagen;
	}

	public String getBiografia() {
		return biografia;
	}

	public void setBiografia(String biografia) {
		this.biografia = biografia;
	}

}
