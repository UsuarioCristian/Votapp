package services.implementacion;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.ws.rs.core.Response;

import negocio.interfaces.IConsultoraHandler;
import services.interfaces.ConsultoraService;
import datas.DataConsultora;
import datas.DataEmergencia;
import datas.DataUsuario;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ConsultoraServiceImpl implements ConsultoraService{
	
		
	@EJB
	IConsultoraHandler consultoraHandler;

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

	@Override
	public Response thereANewEmergency(int id) {
		
		if(consultoraHandler.thereANewEmergency(id))
			return Response.ok(true).build();
		else
			return Response.ok(false).build();
	}

	@Override
	public Response enviarMailConsultora(DataConsultora dataConsultora) {		
		consultoraHandler.enviarMailConsultora(dataConsultora);
		return null;
	}

	@Override
	public Response actualizarCelular(DataUsuario dataUsuario) {		
		consultoraHandler.actuaizarCelular(dataUsuario);
		return null;
	}

	@Override
	public Response existeUsuario(String username) {
		if(consultoraHandler.existeUsuario(username))
			return Response.ok(true).build();
		else
			return Response.ok(false).build();
	}
	
}
