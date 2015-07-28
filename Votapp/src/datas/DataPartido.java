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
	private List<DataDepartamento> dataDeptos;
	private int id;
	private List<DataLista> listas;
	
	public DataPartido() {
		this.dataFuenteDatos = new ArrayList<DataFuenteDatos>();
		this.dataDeptos = new ArrayList<DataDepartamento>();
		this.listas = new ArrayList<DataLista>();
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

	public List<DataDepartamento> getDataDeptos() {
		return dataDeptos;
	}

	public void setDataDeptos(List<DataDepartamento> dataDeptos) {
		this.dataDeptos = dataDeptos;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<DataLista> getListas() {
		return listas;
	}

	public void setListas(List<DataLista> listas) {
		this.listas = listas;
	}

}
