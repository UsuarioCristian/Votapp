package dominio;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import datas.DataCandidato;
import datas.DataLista;
import datas.DataPartido;

@NamedQueries({
	@NamedQuery (name = "Eleccion.getEleccionesActuales",
			query = "SELECT e FROM Eleccion e WHERE e.fecha > :today"),
	@NamedQuery (name = "Eleccion.getElecciones",
			query = "SELECT e FROM Eleccion e"),		
})

@Entity
public abstract class Eleccion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected int id;
	protected Date fecha;
	protected String nombre;
	protected String descripcion;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="idEleccion") // Es unidireccional
	protected Imagen imagen;
	
	@OneToMany(mappedBy = "eleccion", cascade = CascadeType.ALL)
	protected Set<Partido> partidos;
	
	@OneToMany(mappedBy = "eleccion", cascade = CascadeType.ALL)
	protected Set<Lista> listas;
	
	@OneToMany(mappedBy = "eleccion", cascade = CascadeType.ALL)
	protected Set<Candidato> candidatos;
	
	@OneToMany(mappedBy = "eleccion")
	protected Set<Encuesta> encuestas;
	
	@OneToMany(mappedBy = "eleccion", cascade = CascadeType.ALL)
	protected Set<Departamento> departamentos;

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

	public Set<Encuesta> getEncuestas() {
		return encuestas;
	}

	public void setEncuestas(Set<Encuesta> encuestas) {
		this.encuestas = encuestas;
	}

	public Set<Departamento> getDepartamentos() {
		return departamentos;
	}

	public void setDepartamentos(Set<Departamento> departamentos) {
		this.departamentos = departamentos;
	}	

	public Imagen getImagen() {
		return imagen;
	}

	public void setImagen(Imagen imagen) {
		this.imagen = imagen;
	}

	public abstract boolean asignarPartidos(List<DataPartido> dataPartidos);

	public abstract boolean asignarListas(List<DataLista> dataListas);

	public abstract boolean asignarCandidatos(List<DataCandidato> dataCandidatos);
	
	

}
