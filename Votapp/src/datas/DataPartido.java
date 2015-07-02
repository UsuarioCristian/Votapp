package datas;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DataPartido implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String nombre;
	private String descripcion;
	private String presidente;
	private Date fechaFundacion;
	private List<DataFuenteDatos> dataFuenteDatos;
	
	public DataPartido() {
		this.dataFuenteDatos = new ArrayList<DataFuenteDatos>();
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getPresidente() {
		return presidente;
	}

	public void setPresidente(String presidente) {
		this.presidente = presidente;
	}

	public Date getFechaFundacion() {
		return fechaFundacion;
	}

	public void setFechaFundacion(Date fechaFundacion) {
		this.fechaFundacion = fechaFundacion;
	}

	public List<DataFuenteDatos> getDataFuenteDatos() {
		return dataFuenteDatos;
	}

	public void setDataFuenteDatos(List<DataFuenteDatos> dataFuenteDatos) {
		this.dataFuenteDatos = dataFuenteDatos;
	}

}
