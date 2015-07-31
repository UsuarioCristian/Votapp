package negocio.implementacion;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

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
import dominio.Candidato;
import dominio.Departamento;
import dominio.Eleccion;
import dominio.EleccionDepartamental;
import dominio.EleccionNacional;
import dominio.Emergencia;
import dominio.Encuesta;
import dominio.Lista;
import dominio.Partido;
import dominio.Respuesta;
import negocio.interfaces.IEncuestaHandler;

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
			}
			
		}
		return false;//FALTA CONTEMPLAR EL CASO DE 	eleccion.getClass() == EleccionOtro.class	
		
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<DataEncuesta> getEncuestaByIdConsultora(int idConsultora) {
		
		try {
			/*Obtengo las encuestas de dicha consultora*/
			List<Encuesta> encuestas = encuestaDAO.getEncuestasByIdConsultora(idConsultora);
			List<DataEncuesta> dataEncuestasRetorno = new ArrayList<DataEncuesta>();
			/*Transformo las encuestas en dataEncuestas*/
			for (Encuesta encuesta : encuestas) {
				DataEncuesta dataEncuesta = new DataEncuesta();
				dataEncuesta.setId(encuesta.getId());
				dataEncuesta.setIdConsultora(idConsultora);
				dataEncuesta.setIdEleccion(encuesta.getEleccion().getId());
				dataEncuesta.setCantidadRespuestas(encuesta.getCantidadRespuestas());
				
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
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		
	}

	private void asignarListas(Encuesta encuesta, DataCandidato data) {
		
		List<Lista> listas = listaDAO.getListasByIdCandidato(data.getId());
		String nombrePartido = null;
		if(encuesta.getEleccion().getClass() == EleccionNacional.class){
			nombrePartido = listas.get(0).getPartido().getNombre();
			data.setNombrePartido(nombrePartido);
		}
		
		for (Lista lista : listas) {
			DataLista dataLista = new DataLista();
			dataLista.setNombrePartido(nombrePartido);
			dataLista.setNumero(lista.getNumero());
			dataLista.setId(lista.getId());
			
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
	public boolean crearEmergencia(DataEmergencia dataEmergencia) {
		
		Emergencia emergencia = new Emergencia();
		return false;
	}

}
