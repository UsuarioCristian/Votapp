package dominio;

import java.io.Serializable;
import javax.persistence.*;

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
	private int cantidadRespuestas;
	private String nombreDepartamento;
	
	@ManyToOne(cascade = CascadeType.MERGE)
	private Consultora consultora;
	
	@ManyToOne(cascade = CascadeType.MERGE)
	private Eleccion eleccion;

	public Encuesta() {
		super();
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

}
