package dominio;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import datas.DataCandidato;
import datas.DataLista;
import datas.DataPartido;

@NamedQueries({
	@NamedQuery (name = "Eleccion.getEleccionesActuales",
			query = "SELECT e FROM Eleccion e WHERE e.fecha > :today"),
})

@Entity
public abstract class Eleccion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected int id;
	protected Date fecha;
	protected String nombre;
	protected String descripcion;
	
	@OneToMany(mappedBy = "eleccion", cascade = CascadeType.ALL)
	protected Set<Partido> partidos;
	
	@OneToMany(mappedBy = "eleccion", cascade = CascadeType.ALL)
	protected Set<Lista> listas;
	
	@OneToMany(mappedBy = "eleccion", cascade = CascadeType.ALL)
	protected Set<Candidato> candidatos;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
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

	public Set<Candidato> getCandidatos() {
		return candidatos;
	}

	public void setCandidatos(Set<Candidato> candidatos) {
		this.candidatos = candidatos;
	}

	public abstract boolean asignarPartidos(List<DataPartido> dataPartidos);

	public abstract boolean asignarListas(List<DataLista> dataListas);

	public abstract boolean asignarCandidatos(List<DataCandidato> dataCandidatos);
	
	

}
