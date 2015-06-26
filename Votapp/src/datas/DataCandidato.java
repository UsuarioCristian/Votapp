package datas;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DataCandidato implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String nombre;
	private int edad;
	private List<DataFuenteDatos> dataFuenteDatos;
	private List<DataLista> dataListas;
	
	public DataCandidato(){
		this.dataFuenteDatos = new ArrayList<DataFuenteDatos>();
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public List<DataFuenteDatos> getDataFuenteDatos() {
		return dataFuenteDatos;
	}

	public void setDataFuenteDatos(List<DataFuenteDatos> dataFuenteDatos) {
		this.dataFuenteDatos = dataFuenteDatos;
	}

	public List<DataLista> getDataListas() {
		return dataListas;
	}

	public void setDataListas(List<DataLista> dataListas) {
		this.dataListas = dataListas;
	}

}
