package datas;

import java.io.Serializable;

public class DataEncuesta implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int id;
	private int idEleccion;
	private int idConsultora;
	private String nombre;
	private int cantidadRespuestas;
	private boolean porCandidato;
	//private boolean porPartido; // no es necesario, xq o es x candidato o es x partido (son excluyentes)
	private boolean preguntarLista;
	private boolean preguntarEdad;
	private boolean preguntarSexo;
	private boolean preguntarNivelEstudio;
	
	public DataEncuesta(){ }

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

	public int getCantidadRespuestas() {
		return cantidadRespuestas;
	}

	public void setCantidadRespuestas(int cantidadRespuestas) {
		this.cantidadRespuestas = cantidadRespuestas;
	}

	public int getIdEleccion() {
		return idEleccion;
	}

	public void setIdEleccion(int idEleccion) {
		this.idEleccion = idEleccion;
	}

	public boolean isPreguntarNivelEstudio() {
		return preguntarNivelEstudio;
	}

	public void setPreguntarNivelEstudio(boolean preguntarNivelEstudio) {
		this.preguntarNivelEstudio = preguntarNivelEstudio;
	}

	public int getIdConsultora() {
		return idConsultora;
	}

	public void setIdConsultora(int idConsultora) {
		this.idConsultora = idConsultora;
	};

}
