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
import datas.DataEncuesta;
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
		
		Encuesta encuesta = new Encuesta();
		encuesta.setNombre(dataEncuesta.getNombre());
		encuesta.setPorCandidato(dataEncuesta.isPorCandidato());
		encuesta.setPreguntarEdad(dataEncuesta.isPreguntarEdad());
		encuesta.setPreguntarLista(dataEncuesta.isPreguntarLista());
		encuesta.setPreguntarNivelEstudio(dataEncuesta.isPreguntarNivelEstudio());
		encuesta.setPreguntarSexo(dataEncuesta.isPreguntarSexo());
		encuesta.setCantidadRespuestas(dataEncuesta.getCantidadRespuestas());
		
		encuesta.setConsultora(consultoraDAO.findConsultoraById(dataEncuesta.getIdConsultora()));
		encuesta.setEleccion(eleccionDAO.findEleccionById(dataEncuesta.getIdEleccion()));
		
		return encuestaDAO.crearEncuesta(encuesta);
		
	}

}
