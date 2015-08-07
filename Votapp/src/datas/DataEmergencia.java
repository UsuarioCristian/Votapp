package datas;

import java.io.Serializable;

public class DataEmergencia implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int id;
	private int idEncuestador;
	private int idConsultora;	
	private int latitud;
	private int longitud;
	
	public DataEmergencia() { }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdEncuestador() {
		return idEncuestador;
	}

	public void setIdEncuestador(int idEncuestador) {
		this.idEncuestador = idEncuestador;
	}

	public int getIdConsultora() {
		return idConsultora;
	}

	public void setIdConsultora(int idConsultora) {
		this.idConsultora = idConsultora;
	}

	public int getLatitud() {
		return latitud;
	}

	public void setLatitud(int latitud) {
		this.latitud = latitud;
	}

	public int getLongitud() {
		return longitud;
	}

	public void setLongitud(int longitud) {
		this.longitud = longitud;
	}

}
