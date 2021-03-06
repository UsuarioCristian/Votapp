package datas;

import java.io.Serializable;

public class DataEmergencia implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int id;
	private int idEncuestador;
	private int idConsultora;	
	private double latitud;
	private double longitud;
	private String nombreEncuestador;
	private String nombreRealEncuestador;
	private boolean notificada;
	
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

	public String getNombreEncuestador() {
		return nombreEncuestador;
	}

	public void setNombreEncuestador(String nombreEncuestador) {
		this.nombreEncuestador = nombreEncuestador;
	}

	public boolean isNotificada() {
		return notificada;
	}

	public void setNotificada(boolean notificada) {
		this.notificada = notificada;
	}

	public String getNombreRealEncuestador() {
		return nombreRealEncuestador;
	}

	public void setNombreRealEncuestador(String nombreRealEncuestador) {
		this.nombreRealEncuestador = nombreRealEncuestador;
	}

}
