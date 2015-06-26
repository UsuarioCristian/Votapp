package dominio;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;

@Entity
public class EleccionNacional extends Eleccion implements Serializable{

	private static final long serialVersionUID = 1L;
	
	public EleccionNacional() { }
	
	public EleccionNacional(String nombre, String descripcion, Date fecha){
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.fecha = fecha;
	}
	

}
