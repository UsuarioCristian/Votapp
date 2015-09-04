package services.implementacion;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import negocio.interfaces.IConsultoraHandler;
import datas.DataConsultora;
import datas.DataEmergencia;
import datas.DataUsuario;
import dominio.Consultora;
import persistencia.interfaces.IConsultoraDAO;
import services.interfaces.ConsultoraService;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ConsultoraServiceImpl implements ConsultoraService{
	
	@EJB
	IConsultoraDAO consultoraDAO;
	
	@EJB
	IConsultoraHandler consultoraHandler;

	@Transactional
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	@Override
	public Response findById(int id) {
		Consultora consultora = consultoraDAO.findConsultoraById(id);
		if (consultora==null){
			return Response.status(Status.NOT_FOUND).build();
		}
		
		return Response.ok(consultora).build();
	}

	@Override
	public Response crear(DataConsultora dataConsultora) {
		
		if(consultoraHandler.altaConsultora(dataConsultora)){
			return Response.status(200).build();
		}
		
		return null;
	}

	@Override
	public Response crearEncuestador(DataUsuario dataUsuario) {
		
		if(consultoraHandler.altaEncuestador(dataUsuario)){
			return Response.status(200).build();
		}
		return null;
	}

	@Override
	public Response getAllEmergencias(int id) {
		List<DataEmergencia> emergencias = consultoraHandler.getAllEmergencias(id);		
		return Response.ok(emergencias).build(); 
	}

	@Override
	public Response notificarEmergencia(DataEmergencia data) {
		
		if(consultoraHandler.notificarEmergencia(data)){
			return Response.status(200).build();
		}
		return null;
	}

}
