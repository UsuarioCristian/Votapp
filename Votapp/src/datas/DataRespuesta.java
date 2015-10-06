package datas;

import java.io.Serializable;

import utiles.RespuestaEducacion;
import utiles.RespuestaGenero;

public class DataRespuesta implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int id;
	private int idEncuesta;
	private int idCandidato;
	private int idPartido;
	private int idLista;
	private int edad;
	private RespuestaGenero sexo;
	private RespuestaEducacion nivelEstudio;
	private boolean trabaja;
	private int ingresos;
	
	public DataRespuesta() { }
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdEncuesta() {
		return idEncuesta;
	}

	public void setIdEncuesta(int idEncuesta) {
		this.idEncuesta = idEncuesta;
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

	public RespuestaEducacion getNivelEstudio() {
		return nivelEstudio;
	}

	public void setNivelEstudio(RespuestaEducacion nivelEstudio) {
		this.nivelEstudio = nivelEstudio;
	}

	public boolean isTrabaja() {
		return trabaja;
	}

	public void setTrabaja(boolean trabaja) {
		this.trabaja = trabaja;
	}

	public int getIngresos() {
		return ingresos;
	}

	public void setIngresos(int ingresos) {
		this.ingresos = ingresos;
	}

}
