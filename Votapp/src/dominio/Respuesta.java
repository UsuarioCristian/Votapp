package dominio;

import java.io.Serializable;

import javax.persistence.*;

import utiles.RespuestaEducacion;
import utiles.RespuestaGenero;

@Entity
public class Respuesta implements Serializable {

	   
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private static final long serialVersionUID = 1L;
	
	@ManyToOne(cascade  = CascadeType.MERGE)
	private Encuesta encuesta;
	
	private int idCandidato;
	private int idPartido;
	private int idLista;
	private int edad;
	
	@Enumerated(EnumType.STRING)
	private RespuestaGenero sexo;
	
	@Enumerated(EnumType.STRING)
	private RespuestaEducacion educacion;
	

	public Respuesta() {
		super();
	}   
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Encuesta getEncuesta() {
		return encuesta;
	}

	public void setEncuesta(Encuesta encuesta) {
		this.encuesta = encuesta;
	}

	public int getIdCandidato() {
		return idCandidato;
	}

	public void setIdCandidato(int idCandidato) {
		this.idCandidato = idCandidato;
	}

	public int getIdPartido() {
		return idPartido;
	}

	public void setIdPartido(int idPartido) {
		this.idPartido = idPartido;
	}

	public int getIdLista() {
		return idLista;
	}

	public void setIdLista(int idLista) {
		this.idLista = idLista;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public RespuestaGenero getSexo() {
		return sexo;
	}

	public void setSexo(RespuestaGenero sexo) {
		this.sexo = sexo;
	}

	public RespuestaEducacion getEducacion() {
		return educacion;
	}

	public void setEducacion(RespuestaEducacion educacion) {
		this.educacion = educacion;
	}
   
}
