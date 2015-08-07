package dominio;

import java.io.Serializable;

import javax.persistence.*;


@Entity
public class Emergencia implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne(cascade = CascadeType.MERGE)
	private Encuestador encuestador;
	
	@ManyToOne(cascade = CascadeType.MERGE)
	private Consultora consultora;
	
	private int longitud;
	private int latitud;
	
	public Emergencia() { }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Encuestador getEncuestador() {
		return encuestador;
	}

	public void setEncuestador(Encuestador encuestador) {
		this.encuestador = encuestador;
	}

	public Consultora getConsultora() {
		return consultora;
	}

	public void setConsultora(Consultora consultora) {
		this.consultora = consultora;
	}

	public int getLongitud() {
		return longitud;
	}

	public void setLongitud(int longitud) {
		this.longitud = longitud;
	}

	public int getLatitud() {
		return latitud;
	}

	public void setLatitud(int latitud) {
		this.latitud = latitud;
	}
	
}
