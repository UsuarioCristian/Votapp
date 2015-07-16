package negocio.implementacion;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import persistencia.interfaces.IConsultoraDAO;
import persistencia.interfaces.IEleccionDAO;
import persistencia.interfaces.IEncuestaDAO;
import datas.DataDepartamento;
import datas.DataEncuesta;
import dominio.Eleccion;
import dominio.EleccionNacional;
import dominio.Encuesta;
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
			
			return encuestaDAO.crearEncuesta(encuesta);
		}else{
			boolean retorno = false;
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
				
				retorno = encuestaDAO.crearEncuesta(encuesta);
			}
			
			return retorno;
		}
		
		
		
		
		
	}

}
