package dominio;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
public class Departamento implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String nombre;
	private int numHabitantes;
	private int numHabilitadosVotar;
	
	@OneToMany(mappedBy = "departamento")
	private Set<FuenteDatos> fuenteDatos;
	
	@ManyToOne
	private Eleccion eleccion;
	
	@ManyToMany
	private Set<Partido> partidos;
	
	@OneToMany(mappedBy = "departamento")
	private Set<Lista> listas;
	
	public Departamento() {
		super();
		this.fuenteDatos = new LinkedHashSet<FuenteDatos>();
		this.partidos = new LinkedHashSet<Partido>();
		this.listas = new LinkedHashSet<Lista>();
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

	public int getNumHabitantes() {
		return numHabitantes;
	}

	public void setNumHabitantes(int numHabitantes) {
		this.numHabitantes = numHabitantes;
	}

	public int getNumHabilitadosVotar() {
		return numHabilitadosVotar;
	}

	public void setNumHabilitadosVotar(int numHabilitadosVotar) {
		this.numHabilitadosVotar = numHabilitadosVotar;
	}

	public Set<FuenteDatos> getFuenteDatos() {
		return fuenteDatos;
	}

	public void setFuenteDatos(Set<FuenteDatos> fuenteDatos) {
		this.fuenteDatos = fuenteDatos;
	}

	public Eleccion getEleccion() {
		return eleccion;
	}

	public void setEleccion(Eleccion eleccion) {
		this.eleccion = eleccion;
	}

	public Set<Partido> getPartidos() {
		return partidos;
	}

	public void setPartidos(Set<Partido> partidos) {
		this.partidos = partidos;
	}

	public Set<Lista> getListas() {
		return listas;
	}

	public void setListas(Set<Lista> listas) {
		this.listas = listas;
	}

}
