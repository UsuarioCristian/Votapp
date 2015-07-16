package datas;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DataDepartamento implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String nombre;
	private int numHabitantes;
	private int numHabilitadosVotar;
	private List<DataFuenteDatos> listaFuenteDatos;
	private int cantidadRespuestas;// para no crear otro data se agrego este campo (se aplica cuando se crea una eleccion departamental)
	
	public DataDepartamento(){
		this.listaFuenteDatos = new ArrayList<DataFuenteDatos>();
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getNumHabitantes() {
		return numHabitantes;
	}

	public void setNumHabitantes(int numHabitantes) {
		this.numHabitantes = numHabitantes;
	}

	public int getNumHabilitadosVotar() {
		return numHabilitadosVotar;
	}

	public void setNumHabilitadosVotar(int numHabilitadosVotar) {
		this.numHabilitadosVotar = numHabilitadosVotar;
	}

	public List<DataFuenteDatos> getListaFuenteDatos() {
		return listaFuenteDatos;
	}

	public void setListaFuenteDatos(List<DataFuenteDatos> listaFuenteDatos) {
		this.listaFuenteDatos = listaFuenteDatos;
	}

	public int getCantidadRespuestas() {
		return cantidadRespuestas;
	}

	public void setCantidadRespuestas(int cantidadRespuestas) {
		this.cantidadRespuestas = cantidadRespuestas;
	}

}
