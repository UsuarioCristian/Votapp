package dominio;

import datas.DataCandidato;
import datas.DataFuenteDatos;
import datas.DataLista;
import datas.DataPartido;
import dominio.Eleccion;

import java.io.Serializable;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;

import javax.persistence.*;

@Entity
public class EleccionOtro extends Eleccion implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private boolean simple;

	public EleccionOtro() {
		this.encuestas = new LinkedHashSet<Encuesta>();
		this.listas = new LinkedHashSet<Lista>();
		this.noticias = new LinkedHashSet<FuenteDatos>();
		this.candidatos = new LinkedHashSet<Candidato>();
		this.simple = false;
	}
	
	public EleccionOtro(String nombre, String descripcion, Date fecha, boolean simple){
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.fecha = fecha;
		this.encuestas = new LinkedHashSet<Encuesta>();
		this.listas = new LinkedHashSet<Lista>();
		this.noticias = new LinkedHashSet<FuenteDatos>();
		this.candidatos = new LinkedHashSet<Candidato>();
		this.simple = simple;
	}

	public boolean isSimple() {
		return simple;
	}

	public void setSimple(boolean simple) {
		this.simple = simple;
	}

	@Override
	public boolean asignarPartidos(List<DataPartido> dataPartidos) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean asignarListas(List<DataLista> dataListas) {
		
		try {			
			Lista lista;
			for (DataLista data : dataListas) {
				lista = new Lista();
				lista.setNumero(data.getNumero());				
				lista.setEleccion(this);
				this.getListas().add(lista);
			}			
			return true;
		} catch (Exception e) {
			return false;
		}
		
	}
	
	@Override
	public boolean asignarNoticias(List<DataFuenteDatos> dataNoticias) {
		
		try {			
			FuenteDatos fuente;
			for (DataFuenteDatos data : dataNoticias) {
				fuente = new FuenteDatos();
				fuente.setTipo(data.getTipo());				
				fuente.setUrl(data.getUrl());
				fuente.setEleccion(this);
				this.getNoticias().add(fuente);
			}			
			return true;
		} catch (Exception e) {
			return false;
		}
		
	}

	@Override
	public boolean asignarCandidatos(List<DataCandidato> dataCandidatos) {
		
		try {
			Candidato candidato;
			for (DataCandidato data : dataCandidatos) {
				candidato = new Candidato();
				candidato.setNombre(data.getNombre());
				candidato.setCargo(data.getCargo());
				candidato.setEdad(data.getEdad());
				candidato.setBiografia(data.getBiografia());
				
				Imagen imagen = new Imagen();
				if (data.getImagen()!=null){
					imagen.setName(data.getImagen().getName());
					imagen.setFile(data.getImagen().getFile());
					imagen.setTipo(data.getImagen().getTipo());
				}
				candidato.setImagen(imagen);

				this.getCandidatos().add(candidato);
				candidato.setEleccion(this);

				// Transformar el DataFuenteDatos a FuenteDatos y asignarle al candidato
				for (DataFuenteDatos dataFuenteDatos : data.getDataFuenteDatos()) {
					FuenteDatos fuenteDatos = new FuenteDatos();
					fuenteDatos.setUrl(dataFuenteDatos.getUrl());
					fuenteDatos.setTipo(dataFuenteDatos.getTipo());
					candidato.getFuenteDatos().add(fuenteDatos);
				}
				
				//Busco la listas en las cuales esta el candidato
				// Si no existen listas entonces este for no se ejecuta
				for (DataLista dataLista : data.getDataListas()) {
					boolean encontre = false;
					Iterator<Lista> iter = this.listas.iterator();
					Lista lista = null;
					while(iter.hasNext() && !encontre){
						lista = iter.next();
						if(lista.getNumero() == dataLista.getNumero()){
							encontre = true;
						}
					}
					
					lista.getCandidatos().add(candidato);
					candidato.getListas().add(lista);
					
				}
			
			}
			
			return true;
		} catch (Exception e) {
			return false;
		}
		
	}
   
}
