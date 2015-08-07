package datas;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import utiles.TipoEleccion;

public class DataEleccion implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int id;
	private String nombre;
	private String descripcion;
	private Date fecha;
	private List<DataPartido> dataPartidos;
	private List<DataLista> dataListas;
	private List<DataCandidato> dataCandidatos;
	private TipoEleccion tipoEleccion;
	private DataImagen logo;
	private String css;
	private List<DataDepartamento> deptos;
	
	public DataEleccion(){
		this.dataPartidos = new ArrayList<DataPartido>();
		this.dataListas = new ArrayList<DataLista>();
		this.dataCandidatos = new ArrayList<DataCandidato>();
		this.deptos = new ArrayList<DataDepartamento>();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public List<DataPartido> getDataPartidos() {
		return dataPartidos;
	}

	public void setDataPartidos(List<DataPartido> dataPartidos) {
		this.dataPartidos = dataPartidos;
	}

	public List<DataLista> getDataListas() {
		return dataListas;
	}

	public void setDataListas(List<DataLista> dataListas) {
		this.dataListas = dataListas;
	}

	public List<DataCandidato> getDataCandidatos() {
		return dataCandidatos;
	}

	public void setDataCandidatos(List<DataCandidato> dataCandidatos) {
		this.dataCandidatos = dataCandidatos;
	}

	public TipoEleccion getTipoEleccion() {
		return tipoEleccion;
	}

	public void setTipoEleccion(TipoEleccion tipoEleccion) {
		this.tipoEleccion = tipoEleccion;
	}

	public DataImagen getLogo() {
		return logo;
	}

	public void setLogo(DataImagen logo) {
		this.logo = logo;
	}

	public String getCss() {
		return css;
	}

	public void setCss(String css) {
		this.css = css;
	}

	public List<DataDepartamento> getDeptos() {
		return deptos;
	}

	public void setDeptos(List<DataDepartamento> deptos) {
		this.deptos = deptos;
	}

}
