package dominio;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.*;

@NamedQueries({
	@NamedQuery(name = "Encuesta.getEncuestasByIdConsultora",
			query = "SELECT e FROM Encuesta e WHERE e.consultora.id = :idConsultora"),
	@NamedQuery(name = "Encuesta.getRespuestas",
			query = "SELECT r FROM Respuesta r WHERE r.encuesta.id = :idEncuesta")
})

@Entity
public class Encuesta implements Serializable {
	   
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private static final long serialVersionUID = 1L;
	
	private String nombre;
	private boolean porCandidato; //si no es x candidato entonces es por partido
	private boolean preguntarLista;
	private boolean preguntarEdad;
	private boolean preguntarSexo;
	private boolean preguntarNivelEstudio;
	private boolean preguntarSiTrabaja;
	private boolean preguntarIngresos;
	private int cantidadRespuestas;
	private String nombreDepartamento;
	private boolean finalizada;
	
	@ManyToOne(cascade = CascadeType.MERGE)
	private Consultora consultora;
	
	@ManyToOne(cascade = CascadeType.MERGE)
	private Eleccion eleccion;
	
	@ManyToMany(cascade = CascadeType.MERGE)
	private Set<Candidato> candidatos;
	
	@ManyToMany(cascade = CascadeType.MERGE)
	private Set<Partido> partidos;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "encuesta", orphanRemoval = true)
	private Set<Respuesta> respuestas;

	public Encuesta() {
		super();
		this.candidatos = new LinkedHashSet<Candidato>();
		this.partidos = new LinkedHashSet<Partido>();
		this.respuestas = new LinkedHashSet<Respuesta>();
		this.setFinalizada(false);
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

	public boolean isPorCandidato() {
		return porCandidato;
	}

	public void setPorCandidato(boolean porCandidato) {
		this.porCandidato = porCandidato;
	}

	public boolean isPreguntarLista() {
		return preguntarLista;
	}

	public void setPreguntarLista(boolean preguntarLista) {
		this.preguntarLista = preguntarLista;
	}

	public boolean isPreguntarEdad() {
		return preguntarEdad;
	}

	public void setPreguntarEdad(boolean preguntarEdad) {
		this.preguntarEdad = preguntarEdad;
	}

	public boolean isPreguntarSexo() {
		return preguntarSexo;
	}

	public void setPreguntarSexo(boolean preguntarSexo) {
		this.preguntarSexo = preguntarSexo;
	}

	public boolean isPreguntarNivelEstudio() {
		return preguntarNivelEstudio;
	}

	public void setPreguntarNivelEstudio(boolean preguntarNivelEstudio) {
		this.preguntarNivelEstudio = preguntarNivelEstudio;
	}

	public boolean isPreguntarSiTrabaja() {
		return preguntarSiTrabaja;
	}

	public void setPreguntarSiTrabaja(boolean preguntarSiTrabaja) {
		this.preguntarSiTrabaja = preguntarSiTrabaja;
	}

	public boolean isPreguntarIngresos() {
		return preguntarIngresos;
	}

	public void setPreguntarIngresos(boolean preguntarIngresos) {
		this.preguntarIngresos = preguntarIngresos;
	}

	public Consultora getConsultora() {
		return consultora;
	}

	public void setConsultora(Consultora consultora) {
		this.consultora = consultora;
	}

	public Eleccion getEleccion() {
		return eleccion;
	}

	public void setEleccion(Eleccion eleccion) {
		this.eleccion = eleccion;
	}

	public int getCantidadRespuestas() {
		return cantidadRespuestas;
	}

	public void setCantidadRespuestas(int cantidadRespuestas) {
		this.cantidadRespuestas = cantidadRespuestas;
	}

	public String getNombreDepartamento() {
		return nombreDepartamento;
	}

	public void setNombreDepartamento(String nombreDepartamento) {
		this.nombreDepartamento = nombreDepartamento;
	}

	public Set<Candidato> getCandidatos() {
		return candidatos;
	}

	public void setCandidatos(Set<Candidato> candidatos) {
		this.candidatos = candidatos;
	}

	public Set<Partido> getPartidos() {
		return partidos;
	}

	public void setPartidos(Set<Partido> partidos) {
		this.partidos = partidos;
	}

	public Set<Respuesta> getRespuestas() {
		return respuestas;
	}

	public void setRespuestas(Set<Respuesta> respuestas) {
		this.respuestas = respuestas;
	}

	public boolean isFinalizada() {
		return finalizada;
	}

	public void setFinalizada(boolean finalizada) {
		this.finalizada = finalizada;
	}

}
