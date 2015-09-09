package datas;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DataEncuesta implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int id;
	private int idEleccion;
	private int idConsultora;
	private String nombre;
	private int cantidadRespuestas;
	private boolean porCandidato;
	//private boolean porPartido; // no es necesario, xq o es x candidato o es x partido (son excluyentes)
	private boolean preguntarLista;
	private boolean preguntarEdad;
	private boolean preguntarSexo;
	private boolean preguntarNivelEstudio;
	private List<DataDepartamento> listaEncuestaDeptos;
	private List<DataCandidato> dataCandidatos;
	private List<DataPartido> dataPartidos;
	private DataResultado resultado;
	private DataConsultora dataConsultora;
	
	public DataEncuesta(){
		this.listaEncuestaDeptos = new ArrayList<DataDepartamento>();
		this.dataCandidatos = new ArrayList<DataCandidato>();
		this.dataPartidos = new ArrayList<DataPartido>();
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

	public boolean isPorCandidato() {
		return porCandidato;
	}

	public void setPorCandidato(boolean porCandidato) {
		this.porCandidato = porCandidato;
	}

	public boolean isPreguntarLista() {
		return preguntarLista;
	}

	public void setPreguntarLista(boolean preguntarLista) {
		this.preguntarLista = preguntarLista;
	}

	public boolean isPreguntarEdad() {
		return preguntarEdad;
	}

	public void setPreguntarEdad(boolean preguntarEdad) {
		this.preguntarEdad = preguntarEdad;
	}

	public boolean isPreguntarSexo() {
		return preguntarSexo;
	}

	public void setPreguntarSexo(boolean preguntarSexo) {
		this.preguntarSexo = preguntarSexo;
	}

	public int getCantidadRespuestas() {
		return cantidadRespuestas;
	}

	public void setCantidadRespuestas(int cantidadRespuestas) {
		this.cantidadRespuestas = cantidadRespuestas;
	}

	public int getIdEleccion() {
		return idEleccion;
	}

	public void setIdEleccion(int idEleccion) {
		this.idEleccion = idEleccion;
	}

	public boolean isPreguntarNivelEstudio() {
		return preguntarNivelEstudio;
	}

	public void setPreguntarNivelEstudio(boolean preguntarNivelEstudio) {
		this.preguntarNivelEstudio = preguntarNivelEstudio;
	}

	public int getIdConsultora() {
		return idConsultora;
	}

	public void setIdConsultora(int idConsultora) {
		this.idConsultora = idConsultora;
	}

	public List<DataDepartamento> getListaEncuestaDeptos() {
		return listaEncuestaDeptos;
	}

	public void setListaEncuestaDeptos(List<DataDepartamento> listaEncuestaDeptos) {
		this.listaEncuestaDeptos = listaEncuestaDeptos;
	}

	public List<DataCandidato> getDataCandidatos() {
		return dataCandidatos;
	}

	public void setDataCandidatos(List<DataCandidato> dataCandidatos) {
		this.dataCandidatos = dataCandidatos;
	}

	public List<DataPartido> getDataPartidos() {
		return dataPartidos;
	}

	public void setDataPartidos(List<DataPartido> dataPartidos) {
		this.dataPartidos = dataPartidos;
	}

	public DataResultado getResultado() {
		return resultado;
	}

	public void setResultado(DataResultado resultado) {
		this.resultado = resultado;
	}

	public DataConsultora getDataConsultora() {
		return dataConsultora;
	}

	public void setDataConsultora(DataConsultora dataConsultora) {
		this.dataConsultora = dataConsultora;
	};

}
