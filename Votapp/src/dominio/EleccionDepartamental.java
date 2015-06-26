package dominio;

import dominio.Eleccion;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

@Entity
public class EleccionDepartamental extends Eleccion implements Serializable {

	private static final long serialVersionUID = 1L;

	public EleccionDepartamental() { }
	public EleccionDepartamental(String nombre, String descripcion){
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.fecha = new Date();
	}
	   
}
