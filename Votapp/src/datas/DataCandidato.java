package datas;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import utiles.TipoCargo;

public class DataCandidato implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String nombre;
	private String biografia;
	private String nombrePartido;
	private int idPartido;
	private int edad;
	private List<DataFuenteDatos> dataFuenteDatos;
	private List<DataLista> dataListas;
	private TipoCargo cargo;
	private int id;
	private int idDepto;
	private String nombreDepto;

	public DataCandidato(){
		this.dataFuenteDatos = new ArrayList<DataFuenteDatos>();
		this.dataListas = new ArrayList<DataLista>();
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
	
	
	public String getNombrePartido() {
		return nombrePartido;
	}

	public void setNombrePartido(String nombrePartido) {
		this.nombrePartido = nombrePartido;
	}

	public TipoCargo getCargo() {
		return cargo;
	}

	public void setCargo(TipoCargo cargo) {
		this.cargo = cargo;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdPartido() {
		return idPartido;
	}

	public void setIdPartido(int idPartido) {
		this.idPartido = idPartido;
	}

	public int getIdDepto() {
		return idDepto;
	}

	public void setIdDepto(int idDepto) {
		this.idDepto = idDepto;
	}

	public String getNombreDepto() {
		return nombreDepto;
	}

	public void setNombreDepto(String nombreDepto) {
		this.nombreDepto = nombreDepto;
	}
	
	public String getBiografia() {
		return biografia;
	}

	public void setBiografia(String biografia) {
		this.biografia = biografia;
	}

}
