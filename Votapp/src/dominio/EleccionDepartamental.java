package dominio;

import datas.DataCandidato;
import datas.DataLista;
import datas.DataPartido;
import dominio.Eleccion;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;

import javax.persistence.*;

@Entity
public class EleccionDepartamental extends Eleccion implements Serializable {

	private static final long serialVersionUID = 1L;

	public EleccionDepartamental() { 
		this.encuestas = new LinkedHashSet<Encuesta>();
	}
	public EleccionDepartamental(String nombre, String descripcion, Date fecha){
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.fecha = fecha;
		this.encuestas = new LinkedHashSet<Encuesta>();
	}
	@Override
	public boolean asignarPartidos(List<DataPartido> dataPartidos) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean asignarListas(List<DataLista> dataListas) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean asignarCandidatos(List<DataCandidato> dataCandidatos) {
		// TODO Auto-generated method stub
		return false;
	}
	   
}
