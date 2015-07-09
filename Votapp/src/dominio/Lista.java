package dominio;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
public class Lista implements Serializable {
	   
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private static final long serialVersionUID = 1L;
	
	private int numero;
	
	@ManyToOne
	private Partido partido;
	
	@ManyToOne
	private Eleccion eleccion;
	
	@ManyToMany
	private Set<Candidato> candidatos;
	
	@ManyToOne
	private Departamento departamento;

	public Lista() {
		super();
		this.candidatos = new LinkedHashSet<Candidato>();
	}   
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public Partido getPartido() {
		return partido;
	}

	public void setPartido(Partido partido) {
		this.partido = partido;
	}

	public Eleccion getEleccion() {
		return eleccion;
	}

	public void setEleccion(Eleccion eleccion) {
		this.eleccion = eleccion;
	}

	public Set<Candidato> getCandidatos() {
		return candidatos;
	}

	public void setCandidatos(Set<Candidato> candidatos) {
		this.candidatos = candidatos;
	}

	public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

}
