package dominio;

import datas.DataCandidato;
import datas.DataDepartamento;
import datas.DataFuenteDatos;
import datas.DataLista;
import datas.DataPartido;
import dominio.Eleccion;

import java.io.Serializable;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

@Entity
public class EleccionDepartamental extends Eleccion implements Serializable {

	private static final long serialVersionUID = 1L;

	public EleccionDepartamental() { 
		this.partidos = new LinkedHashSet<Partido>();
		this.listas = new LinkedHashSet<Lista>();
		this.candidatos = new LinkedHashSet<Candidato>();
		this.encuestas = new LinkedHashSet<Encuesta>();
		this.departamentos = new LinkedHashSet<Departamento>();
	}
	public EleccionDepartamental(String nombre, String descripcion, Date fecha){
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.fecha = fecha;
		this.partidos = new LinkedHashSet<Partido>();
		this.listas = new LinkedHashSet<Lista>();
		this.candidatos = new LinkedHashSet<Candidato>();
		this.encuestas = new LinkedHashSet<Encuesta>();
		this.departamentos = new LinkedHashSet<Departamento>();
	}
	@Override
	public boolean asignarPartidos(List<DataPartido> dataPartidos) {
		
		//Paso 0: crear los departamentos //Tal vez se mejor llamar a otra clase para q lo haga "helper"
		Set<String> deptos = new LinkedHashSet<String>();
		deptos.add("Artigas");
		deptos.add("Cerro Largo");
		deptos.add("Durazno");
		deptos.add("Florida");
		deptos.add("Maldonado");
		deptos.add("Paysandú");
		deptos.add("Rivera");
		deptos.add("Salto");
		deptos.add("Soriano");
		deptos.add("Treinta y Tres");
		deptos.add("Canelones");
		deptos.add("Colonia");
		deptos.add("Flores");
		deptos.add("Lavalleja");
		deptos.add("Montevideo");
		deptos.add("Río Negro");
		deptos.add("Rocha");
		deptos.add("San José");
		deptos.add("Tacuarembó");
		
		for (String nombreDepto : deptos) {
			Departamento departamento = new Departamento();
			departamento.setNombre(nombreDepto);
			
			departamento.setEleccion(this);
			this.getDepartamentos().add(departamento);
		}
		
		
		try {
			Partido partido;
			for (DataPartido data : dataPartidos) {
				partido = new Partido();
				partido.setNombre(data.getNombre());
				partido.setDescripcion(data.getDescripcion());
				partido.setFechaFundacion(data.getFechaFundacion());
				partido.setPresidente(data.getPresidente());
				
				Imagen imagen = new Imagen();
				if (data.getImagen()!=null){
					imagen.setName(data.getImagen().getName());
					imagen.setFile(data.getImagen().getFile());
					imagen.setTipo(data.getImagen().getTipo());
				}
				partido.setImagen(imagen);

				partido.setEleccion(this);
				this.getPartidos().add(partido);
				
				//Por cada departamento (que tiene el partido), obtener lista de fuente d datos, crearla y asignarle el partido y el departamento.
				
				for (DataDepartamento dataDepartamento : data.getDataDeptos()) {
					
					List<DataFuenteDatos> dataFuenteDatos = dataDepartamento.getListaFuenteDatos();
					for (DataFuenteDatos dataFuente : dataFuenteDatos) {
						FuenteDatos fuenteDatos = new FuenteDatos();
						fuenteDatos.setUrl(dataFuente.getUrl());
						fuenteDatos.setTipo(dataFuente.getTipo());
						
						partido.getFuenteDatos().add(fuenteDatos);//No se si es necesario
						
						/*Obtener el departamento con el mismo nombre
						 * y asignarlo a la fuente de datos*/						
						boolean encontre = false;
						Iterator<Departamento> iter = this.departamentos.iterator();
						Departamento departamento = null;
						while(iter.hasNext() && !encontre){
							departamento = iter.next();
							if(departamento.getNombre().equals((dataDepartamento.getNombre())))
								encontre = true;
						}
						fuenteDatos.setDepartamento(departamento);
						departamento.getFuenteDatos().add(fuenteDatos);
						departamento.getPartidos().add(partido);
						partido.getDepartamentos().add(departamento);
						
					}
					
				}
				
			}
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}
	@Override
	public boolean asignarListas(List<DataLista> dataListas) {
		try {
			Lista lista;
			for (DataLista data : dataListas) {
				lista = new Lista();
				lista.setNumero(data.getNumero());

				// buscar partido (a esta altura el partido fue agregado en el paso1... Ver EleccionHandler)
				boolean encontre = false;
				Partido partido = null;
				Iterator<Partido> iter = this.getPartidos().iterator();
				while (!encontre && iter.hasNext()) {
					partido = iter.next();
					if (partido.getNombre().equals(data.getNombrePartido()))
						encontre = true;
				}
				lista.setPartido(partido);
				partido.getListas().add(lista);
				
				//Ahora obtengo la lista de departamentos del partido y asigno el departamento a la lista
				Set<Departamento> departamentos = partido.getDepartamentos();
				encontre = false;
				Iterator<Departamento> iterador = departamentos.iterator();
				Departamento departamento = null;
				while(iterador.hasNext() && !encontre){
					departamento = iterador.next();
					if(departamento.getNombre().equals(data.getDataDepartamento().getNombre()))
						encontre = true;
				}
								
				departamento.getListas().add(lista);
				lista.setDepartamento(departamento);

				lista.setEleccion(this);
				this.getListas().add(lista);
			}

			return true;
		} catch (Exception e) {
			e.printStackTrace();
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

				/* Buscar listas (a esta altura la lista fue agregado en el paso2... Ver EleccionHandler)
				 * primero debo encontrar el partido, y asi acceder a la coleccion de listas
				 * y como la lista pertenece a un solo partido y departamento (x mas q un numero d lista exista en mas de un departamento estas 
				 * listas son distinas) es facil obtener el partido y el departamento del candidato por medio d la lista*/				
				String nombrePartido = data.getNombrePartido();
				Partido partido = null;
				boolean encontre = false;
				Iterator<Partido> iter = this.getPartidos().iterator();

				while (!encontre && iter.hasNext()) {
					partido = iter.next();
					if (partido.getNombre().equals(nombrePartido))
						encontre = true;
				}
				/*Luego de salir del while ya obtuve el Partido, ahora necesito las listas y comparar para ver si estan dentro
				 * del data candidato*/

				for (Lista lista : partido.getListas()) {
					//"perteneceLista" es una funcion q esta definida mas abajo
					if(perteneceLista(lista.getNumero(), data.getDataListas())){
						lista.getCandidatos().add(candidato);
						candidato.getListas().add(lista);
					}					
				}

			}

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private boolean perteneceLista(int numero, List<DataLista> dataListas) {
		/*Retorna true cuando el numero de lista esta dentro de la coleccion dataListas, false en caso contrario*/
		boolean encontre = false;
		Iterator<DataLista> iter = dataListas.iterator();
		while (iter.hasNext() && !encontre) {
			if (iter.next().getNumero() == numero)
				encontre = true;
		}

		return encontre;
	}
	   
}
