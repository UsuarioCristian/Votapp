package negocio.implementacion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import negocio.interfaces.IEncuestaHandler;
import persistencia.interfaces.ICandidatoDAO;
import persistencia.interfaces.IConsultoraDAO;
import persistencia.interfaces.IEleccionDAO;
import persistencia.interfaces.IEncuestaDAO;
import persistencia.interfaces.IListaDAO;
import persistencia.interfaces.IPartidoDAO;
import utiles.TipoCargo;
import datas.DataCandidato;
import datas.DataDepartamento;
import datas.DataEmergencia;
import datas.DataEncuesta;
import datas.DataLista;
import datas.DataPartido;
import datas.DataRespuesta;
import datas.DataResultado;
import dominio.Candidato;
import dominio.Consultora;
import dominio.Departamento;
import dominio.Eleccion;
import dominio.EleccionDepartamental;
import dominio.EleccionNacional;
import dominio.Emergencia;
import dominio.Encuesta;
import dominio.Encuestador;
import dominio.Lista;
import dominio.Partido;
import dominio.Respuesta;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class EncuestaHandler implements IEncuestaHandler {
	
	@EJB
	IEncuestaDAO encuestaDAO;
	@EJB
	IConsultoraDAO consultoraDAO;
	@EJB
	IEleccionDAO eleccionDAO;
	@EJB
	ICandidatoDAO candidatoDAO;
	@EJB
	IPartidoDAO partidoDAO;
	@EJB
	IListaDAO listaDAO;
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public boolean crearEncuesta(DataEncuesta dataEncuesta) {
		
		//Dependiendo de tipo de Eleccion se debe crear una (eleccion nacional) o varias (elecc departamental) encuestas
		
		Eleccion eleccion = eleccionDAO.findEleccionById(dataEncuesta.getIdEleccion());
		
		if(eleccion.getClass() == EleccionNacional.class){
			Encuesta encuesta = new Encuesta();
			encuesta.setNombre(dataEncuesta.getNombre());
			encuesta.setPorCandidato(dataEncuesta.isPorCandidato());
			encuesta.setPreguntarEdad(dataEncuesta.isPreguntarEdad());
			encuesta.setPreguntarLista(dataEncuesta.isPreguntarLista());
			encuesta.setPreguntarNivelEstudio(dataEncuesta.isPreguntarNivelEstudio());
			encuesta.setPreguntarSexo(dataEncuesta.isPreguntarSexo());
			encuesta.setCantidadRespuestas(dataEncuesta.getCantidadRespuestas());
			
			encuesta.setConsultora(consultoraDAO.findConsultoraById(dataEncuesta.getIdConsultora()));
			encuesta.setEleccion(eleccion);
			
			// Asignar los candidatos a la encuesta si es una encuesta es x candidatos, o partidos en caso contrario.
			
			if(dataEncuesta.isPorCandidato()){
				//Busco todos los canidatos de esa encuesta
				for (Candidato candidatoJustID : eleccion.getCandidatos()) {
					
					Candidato candidato = candidatoDAO.findCandidatoById(candidatoJustID.getId());					
					if(candidato.getCargo().equals(TipoCargo.PRESIDENTE)){
						candidato.getEncuestas().add(encuesta);
						encuesta.getCandidatos().add(candidato);
					}					
					/*no pude usar esto xq tenia q acceder a otro campo del candidato (getCargo)*/
//					encuesta.getCandidatos().add(candidatoJustID);
//					candidatoJustID.getEncuestas().add(encuesta);
				}
				
				
			}else{
				for (Partido partidoJustID : eleccion.getPartidos()) {
//					Partido partido = partidoDAO.findPartidoById(partidoJustID.getId());
//					partido.getEncuestas().add(encuesta);
//					encuesta.getPartidos().add(partido);
					encuesta.getPartidos().add(partidoJustID);
					partidoJustID.getEncuestas().add(encuesta);
				}
			}
			
			return encuestaDAO.crearEncuesta(encuesta);
		}else{
			if(eleccion.getClass() == EleccionDepartamental.class){
				boolean retorno = true;
				for (DataDepartamento data : dataEncuesta.getListaEncuestaDeptos()) {
					Encuesta encuesta = new Encuesta();
					encuesta.setNombre(dataEncuesta.getNombre());
					encuesta.setPorCandidato(dataEncuesta.isPorCandidato());
					encuesta.setPreguntarEdad(dataEncuesta.isPreguntarEdad());
					encuesta.setPreguntarLista(dataEncuesta.isPreguntarLista());
					encuesta.setPreguntarNivelEstudio(dataEncuesta.isPreguntarNivelEstudio());
					encuesta.setPreguntarSexo(dataEncuesta.isPreguntarSexo());
					
					encuesta.setCantidadRespuestas(data.getCantidadRespuestas());
					encuesta.setNombreDepartamento(data.getNombre());
					
					encuesta.setConsultora(consultoraDAO.findConsultoraById(dataEncuesta.getIdConsultora()));
					encuesta.setEleccion(eleccion);
					
					/*Se debe buscar todos los candidatos (o partidos) por el departamento data.getNombre
					 * y asignarlos a la encuesta*/
					List<Departamento> departamentos = eleccionDAO.getDeptosByEleccionID(eleccion.getId());
					
					if(dataEncuesta.isPorCandidato()){			
						boolean encontre = false;
						Iterator<Departamento> iter = departamentos.iterator();
						Departamento depto = null;
						while(!encontre && iter.hasNext()){
							depto = iter.next();
							if(depto.getNombre().equals(data.getNombre()))
								encontre = true;
						}
						
						//Aqui se asume q solo se contempla a el INTENDENTE, x eso el las listas solo tienen a 1 candidato
						Set<Lista> listas = depto.getListas();
						for (Lista lista : listas) {
							Set<Candidato>candidatos = lista.getCandidatos();
							for (Candidato candidato : candidatos) {
								/*Si ya se agrego el candidato a la encuesta en otra iteracion, entonces la sentencia .add() no 
								 * tiene ningun efecto*/
								candidato.getEncuestas().add(encuesta);
								encuesta.getCandidatos().add(candidato);
							}
						}
						
						
					}else{
						/* dataEncuesta.isPorCandidato() resulto false*/	
						boolean encontre = false;
						Iterator<Departamento> iter = departamentos.iterator();
						Departamento depto = null;
						while(!encontre && iter.hasNext()){
							depto = iter.next();
							if(depto.getNombre().equals(data.getNombre()))
								encontre = true;
						}
						
						Set<Partido> partidos = depto.getPartidos();
						for (Partido partido : partidos) {
							partido.getEncuestas().add(encuesta);
							encuesta.getPartidos().add(partido);
						}
						
					}
					
					retorno = retorno && encuestaDAO.crearEncuesta(encuesta);
				}
				
				return retorno;
			}else{
				//eleccion.getClass() == EleccionOtro.class				
				Encuesta encuesta = new Encuesta();
				encuesta.setNombre(dataEncuesta.getNombre());
				encuesta.setPorCandidato(dataEncuesta.isPorCandidato());
				encuesta.setPreguntarEdad(dataEncuesta.isPreguntarEdad());
				encuesta.setPreguntarLista(dataEncuesta.isPreguntarLista());
				encuesta.setPreguntarNivelEstudio(dataEncuesta.isPreguntarNivelEstudio());
				encuesta.setPreguntarSexo(dataEncuesta.isPreguntarSexo());
				encuesta.setCantidadRespuestas(dataEncuesta.getCantidadRespuestas());
				
				encuesta.setConsultora(consultoraDAO.findConsultoraById(dataEncuesta.getIdConsultora()));
				encuesta.setEleccion(eleccion);
				
				for (Candidato candidatoJustID : eleccion.getCandidatos()) {
					Candidato candidato = candidatoDAO.findCandidatoById(candidatoJustID.getId());
					candidato.getEncuestas().add(encuesta);
					encuesta.getCandidatos().add(candidato);					

				}
				
				return encuestaDAO.crearEncuesta(encuesta);
			}
			
		}
		
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<DataEncuesta> getEncuestaByIdConsultora(int idConsultora) {
		
		try {
			/*Obtengo las encuestas de dicha consultora*/
			List<Encuesta> encuestas = encuestaDAO.getEncuestasByIdConsultora(idConsultora);
			
			/*Transformo las encuestas en dataEncuestas*/
			List<DataEncuesta> dataEncuestasRetorno = createListDataEncuestas(encuestas);
									
			return dataEncuestasRetorno;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		
	}


	

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public boolean crearRespuesta(DataRespuesta dataRespuesta) {
		
		Respuesta respuesta = new Respuesta();
		respuesta.setEdad(dataRespuesta.getEdad());
		respuesta.setEducacion(dataRespuesta.getNivelEstudio());
		respuesta.setIdCandidato(dataRespuesta.getIdCandidato());
		respuesta.setIdPartido(dataRespuesta.getIdPartido());
		respuesta.setIdLista(dataRespuesta.getIdLista());
		respuesta.setSexo(dataRespuesta.getSexo());

		Encuesta encuesta = encuestaDAO.findEncuestaById(dataRespuesta.getIdEncuesta());
		if (!encuesta.isFinalizada()) {
			respuesta.setEncuesta(encuesta);
			encuesta.getRespuestas().add(respuesta);
			if (encuesta.getRespuestas().size() == encuesta.getCantidadRespuestas())
				encuesta.setFinalizada(true);

			return encuestaDAO.crearRespuesta(respuesta);
		} else
			return false;

	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public boolean crearEmergencia(DataEmergencia dataEmergencia) {
		
		try {
			Emergencia emergencia = new Emergencia();
			emergencia.setLatitud(dataEmergencia.getLatitud());
			emergencia.setLongitud(dataEmergencia.getLongitud());
			emergencia.setNotificada(false);
			
			Consultora consultora = consultoraDAO.findConsultoraById(dataEmergencia.getIdConsultora());
			emergencia.setConsultora(consultora);
			consultora.getEmergencias().add(emergencia);
			
			Encuestador encuestador = consultoraDAO.findEncuestadorById(dataEmergencia.getIdEncuestador());
			emergencia.setEncuestador(encuestador);
			encuestador.getEmergencias().add(emergencia);
			
			return encuestaDAO.crearEmergencia(emergencia);
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<DataEncuesta> getEncuestasFinalizadasByIdConsultora(int id) {
		try {
			/*Obtengo las encuestas de dicha consultora*/
			List<Encuesta> encuestasAll = encuestaDAO.getEncuestasByIdConsultora(id);
			
			/*Quito las que no estan finalizadas*/
			List<Encuesta> encuestas = new ArrayList<Encuesta>();
			for (Encuesta encuesta : encuestasAll) {
				if(encuesta.isFinalizada())
					encuestas.add(encuesta);
										
			}			
			
			/*Transformo las encuestas en dataEncuestas*/
			List<DataEncuesta> dataEncuestasRetorno = createListDataEncuestas(encuestas);
			
			setResultadoEncuesta(dataEncuestasRetorno);			
			return dataEncuestasRetorno;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	

	@Override
	public List<DataEncuesta> getEncuestasByIdEleccion(int id) {
		try {
			Eleccion eleccion = eleccionDAO.findEleccionById(id);
			List<Encuesta> encuestasFinalizadas = new ArrayList<Encuesta>();
			
			for (Encuesta encuestaOnlyId : eleccion.getEncuestas()) {
				Encuesta encuesta = encuestaDAO.findEncuestaById(encuestaOnlyId.getId());
				if(encuesta.isFinalizada())
					encuestasFinalizadas.add(encuesta);			
			}
			
			List<DataEncuesta> dataEncuestasRetorno = createListDataEncuestas(encuestasFinalizadas);
			setResultadoEncuesta(dataEncuestasRetorno);
			
			return dataEncuestasRetorno;
		} catch (Exception e) {
			return null;
		}		
		
	}
	
	

	/*********************************************************/
	/*********************************************************/
	/*****************                    ********************/
	/*****************FUNCIONES AUXILIARES********************/
	/*****************                    ********************/
	/*********************************************************/
	/*********************************************************/
	private void asignarListas(Encuesta encuesta, DataCandidato data) {
		
		List<Lista> listas = listaDAO.getListasByIdCandidato(data.getId());
		String nombrePartido = null;
		int idPartido = 0;
		if(encuesta.getEleccion().getClass() == EleccionNacional.class){
			nombrePartido = listas.get(0).getPartido().getNombre();
			idPartido = listas.get(0).getPartido().getId();
			data.setNombrePartido(nombrePartido);
		}
		
		for (Lista lista : listas) {
			DataLista dataLista = new DataLista();
			dataLista.setNombrePartido(nombrePartido);
			dataLista.setNumero(lista.getNumero());
			dataLista.setId(lista.getId());
			dataLista.setIdPartido(idPartido);
			
			data.getDataListas().add(dataLista);
		}
		
		
	}

	private void asignarListas(Encuesta encuesta, DataPartido data) {
		
		List<Lista> listas = listaDAO.getListasByIdPartido(data.getId());
		String nombrePartido = data.getNombre();
		//Crear dataLista
		for (Lista lista : listas) {
			DataLista dataLista = new DataLista();
			dataLista.setNombrePartido(data.getNombre());
			dataLista.setNumero(lista.getNumero());
			dataLista.setId(lista.getId());
			dataLista.setNombrePartido(nombrePartido);
			
			data.getListas().add(dataLista);
		}		
	}
	
	private List<DataEncuesta> createListDataEncuestas(List<Encuesta> encuestas) {
		
		List<DataEncuesta> dataEncuestasRetorno = new ArrayList<DataEncuesta>();
		for (Encuesta encuesta : encuestas) {
			DataEncuesta dataEncuesta = new DataEncuesta();
			dataEncuesta.setId(encuesta.getId());
			dataEncuesta.setIdEleccion(encuesta.getEleccion().getId());
			dataEncuesta.setCantidadRespuestas(encuesta.getCantidadRespuestas());

			dataEncuesta.setIdConsultora(encuesta.getConsultora().getId());
			
			dataEncuesta.setPorCandidato(encuesta.isPorCandidato());
			dataEncuesta.setPreguntarEdad(encuesta.isPreguntarEdad());
			dataEncuesta.setPreguntarLista(encuesta.isPreguntarLista());
			dataEncuesta.setPreguntarNivelEstudio(encuesta.isPreguntarNivelEstudio());
			dataEncuesta.setPreguntarSexo(encuesta.isPreguntarSexo());
			
			//Aca se podria cambiar el nombre para que no sea necesario cambiarlo en la vista
			if(encuesta.getEleccion().getClass() == EleccionDepartamental.class)
				dataEncuesta.setNombre(encuesta.getNombre()+" de "+encuesta.getNombreDepartamento());
			else{
				dataEncuesta.setNombre(encuesta.getNombre());
				if(encuesta.getEleccion().getClass() == EleccionNacional.class){
					/*Buscar las listas que tengan cierto idPartido, ya q los candidatos estan en todas las listas (xq es nacional)*/
					
					
				}
			}
			/*Ahora hay q obtener la lista de candidatos (o de partidos) dependiendo
			 *  si es una eleccion nacional o departamental*/ 
			/*Correccion: no es necesario conocer si es una eleccion nacional o no, ya que obtengo los candidatos y
			 * partidos de la encuesta y no de la eleccion, x ejemplo "encuesta.getCandidatos();" */
			
			if(encuesta.isPorCandidato()){
				//Busco los candidatos de la encuesta 
				Set<Candidato>candidatosOnlyID = encuesta.getCandidatos();
				for (Candidato candidatoIter : candidatosOnlyID) {
					Candidato candidato = candidatoDAO.findCandidatoById(candidatoIter.getId());
					DataCandidato data = new DataCandidato();
					data.setCargo(candidato.getCargo());
					data.setNombre(candidato.getNombre());
					
					//data.setNombrePartido("El nombre del partido solo se obtiene x la lista"); 
					/*ver funcion asignarListas()........ */
					
					data.setId(candidato.getId());
					
					if(encuesta.isPreguntarLista())
						asignarListas(encuesta, data);
					
					dataEncuesta.getDataCandidatos().add(data);
				}							
				
			}else{
				//Busco los partidos de la eleccion 
				Set<Partido>partidosOnlyID = encuesta.getPartidos();
				for (Partido partidoIter : partidosOnlyID) {
					Partido partido = partidoDAO.findPartidoById(partidoIter.getId());
					DataPartido data = new DataPartido();
					data.setNombre(partido.getNombre());
					data.setPresidente(partido.getPresidente());
					data.setId(partido.getId());						
					/*...*/
					
					if(encuesta.isPreguntarLista())
						asignarListas(encuesta, data);
					
					dataEncuesta.getDataPartidos().add(data);
				}
			}
			
			/*Finalmente agrego el dataEncuesta a la lista de retorno*/
			dataEncuestasRetorno.add(dataEncuesta);
		}
		return dataEncuestasRetorno;
	}
	
	private void setResultadoEncuesta(List<DataEncuesta> dataEncuestasRetorno) {

		for (DataEncuesta dataEncuesta : dataEncuestasRetorno) {
			
			DataResultado dataResultado = new DataResultado();
			dataEncuesta.setResultado(dataResultado);
			List<Respuesta> respuestas = encuestaDAO.getRespuestas(dataEncuesta.getId());
			
			if (dataEncuesta.isPorCandidato()) {
				for (DataCandidato candidato : dataEncuesta.getDataCandidatos()) {
					int cantidad = 0;
					for (Respuesta respuesta : respuestas) {
						if (respuesta.getIdCandidato() == candidato.getId())
							cantidad++;
					}
					dataResultado.getMapCandidatos().put(candidato.getId(), cantidad);
					
					if(dataEncuesta.isPreguntarLista())
						dataResultado.getMapListas().putAll(setListasResultado(respuestas, candidato.getDataListas()));
					
					if(dataEncuesta.isPreguntarNivelEstudio()){
						setNivelEstudioResultado(dataEncuesta.isPorCandidato() , dataResultado, respuestas, candidato.getId());
					}
					
					if (dataEncuesta.isPreguntarEdad()){
						setRangoEdadResultado(dataEncuesta.isPorCandidato() , dataResultado, respuestas, candidato.getId());
					}
					
					if(dataEncuesta.isPreguntarSexo()){
						setGeneroResultado(dataEncuesta.isPorCandidato() , dataResultado, respuestas, candidato.getId());
					}
				}
				
			} else {/*Es x Partido*/
				for (DataPartido partido : dataEncuesta.getDataPartidos()) {
					int cantidad = 0;
					for (Respuesta respuesta : respuestas) {
						if(respuesta.getIdPartido() == partido.getId())
							cantidad++;
					}
					dataResultado.getMapPartidos().put(partido.getId(), cantidad);
					
					if(dataEncuesta.isPreguntarLista())
						dataResultado.getMapListas().putAll(setListasResultado(respuestas, partido.getListas()));
					
					if(dataEncuesta.isPreguntarNivelEstudio()){
						setNivelEstudioResultado(dataEncuesta.isPorCandidato() , dataResultado, respuestas, partido.getId());
					}
					
					if (dataEncuesta.isPreguntarEdad()){
						setRangoEdadResultado(dataEncuesta.isPorCandidato() , dataResultado, respuestas, partido.getId());
					}
					
					if(dataEncuesta.isPreguntarSexo()){
						setGeneroResultado(dataEncuesta.isPorCandidato() , dataResultado, respuestas, partido.getId());
					}
				}
			}			

		}
		
	}

	private void setGeneroResultado(boolean porCandidato, DataResultado dataResultado, List<Respuesta> respuestas, int id) {
		
		int cantidadFem = 0;
		int cantidadMas = 0;
		int cantidadOtro = 0;
		
		if(porCandidato){
			for (Respuesta respuesta : respuestas) {
				if(respuesta.getIdCandidato() == id){
					switch (respuesta.getSexo()) {
						case Femenino:
							cantidadFem++;
							break;
						case Masculino:
							cantidadMas++;
							break;
						case Otro:
							cantidadOtro++;
							break;

						default:
							break;
					}
						
				}
			}
		}else{
			for (Respuesta respuesta : respuestas) {
				if(respuesta.getIdPartido() == id){
					switch (respuesta.getSexo()) {
						case Femenino:
							cantidadFem++;
							break;
						case Masculino:
							cantidadMas++;
							break;
						case Otro:
							cantidadOtro++;
							break;

						default:
							break;
					}
						
				}
			}
		}
		
		dataResultado.getMapGeneroFem().put(id, cantidadFem);
		dataResultado.getMapGeneroMas().put(id, cantidadMas);
		dataResultado.getMapGeneroOtro().put(id, cantidadOtro);
		
	}

	private void setRangoEdadResultado(boolean porCandidato, DataResultado dataResultado, List<Respuesta> respuestas, int idSeleccionado) {
		
		int cantidad18a23 = 0;
		int cantidad24a30 = 0;
		int cantidad31a50 = 0;
		int cantidad51omas = 0;
		
		if (porCandidato){
			for (Respuesta respuesta : respuestas) {
				if(respuesta.getIdCandidato() == idSeleccionado){
					int edad = respuesta.getEdad();
					if(edad < 24)
						cantidad18a23++;
					else
						if(edad < 31)
							cantidad24a30++;
						else
							if(edad < 51)
								cantidad31a50++;
							else
								cantidad51omas++;
				}
			}
		}else{
			
			for (Respuesta respuesta : respuestas) {
				if(respuesta.getIdPartido() == idSeleccionado){
					int edad = respuesta.getEdad();
					if(edad < 24)
						cantidad18a23++;
					else
						if(edad < 31)
							cantidad24a30++;
						else
							if(edad < 51)
								cantidad31a50++;
							else
								cantidad51omas++;
				}
			}			
		}
		
		dataResultado.getMapEdad18a23().put(idSeleccionado, cantidad18a23);
		dataResultado.getMapEdad24a30().put(idSeleccionado, cantidad24a30);
		dataResultado.getMapEdad31a50().put(idSeleccionado, cantidad31a50);
		dataResultado.getMapEdad51omas().put(idSeleccionado, cantidad51omas);		
	}

	private void setNivelEstudioResultado(boolean porCandidato, DataResultado dataResultado, List<Respuesta> respuestas, int idSeleccionado) {
		int cantidadPrimaria = 0;
		int cantidadSecundaria = 0;
		int cantidadTerciario = 0;
		int cantidadNoSabe = 0;		
		
		if (porCandidato) {
			for (Respuesta respuesta : respuestas) {
				
				if (respuesta.getIdCandidato() == idSeleccionado) {
					switch (respuesta.getEducacion()) {
						case Primaria:
							cantidadPrimaria++;
							break;
						case Secundaria:
							cantidadSecundaria++;
							break;
						case Terciaria:
							cantidadTerciario++;
							break;

						default:
							cantidadNoSabe++;
							break;
					}

				}				

			}
		} else { /* es por partido */
			
			for (Respuesta respuesta : respuestas) {

				if (respuesta.getIdPartido() == idSeleccionado) {
					switch (respuesta.getEducacion()) {
						case Primaria:
							cantidadPrimaria++;
							break;
						case Secundaria:
							cantidadSecundaria++;
							break;
						case Terciaria:
							cantidadTerciario++;
							break;

						default:
							cantidadNoSabe++;
							break;
					}

				}

			}
		}
		
		dataResultado.getMapNivelEstudioPrimaria().put(idSeleccionado, cantidadPrimaria);
		dataResultado.getMapNivelEstudioSecundaria().put(idSeleccionado, cantidadSecundaria);
		dataResultado.getMapNivelEstudioTerciario().put(idSeleccionado, cantidadTerciario);
		dataResultado.getMapNivelEstudioNoSabe().put(idSeleccionado, cantidadNoSabe);
		
	}

	private Map<Integer,Integer> setListasResultado(List<Respuesta> respuestas, List<DataLista> dataListas) {
		Map<Integer, Integer> mapResultado = new HashMap<Integer, Integer>();
		
		for (DataLista lista : dataListas) {
			int cantidad = 0;
			for (Respuesta respuesta : respuestas) {
				if(respuesta.getIdPartido() == lista.getId())
					cantidad++;
			}
			mapResultado.put(lista.getId(), cantidad);
		}
		
		return mapResultado;
	}


}
