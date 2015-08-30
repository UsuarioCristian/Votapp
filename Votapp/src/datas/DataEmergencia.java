package datas;

import java.io.Serializable;

public class DataEmergencia implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int id;
	private int idEncuestador;
	private int idConsultora;	
	private double latitud;
	private double longitud;
	
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

	public double getLatitud() {
		return latitud;
	}

	public void setLatitud(double latitud) {
		this.latitud = latitud;
	}

	public double getLongitud() {
		return longitud;
	}

	public void setLongitud(double longitud) {
		this.longitud = longitud;
	}

}
