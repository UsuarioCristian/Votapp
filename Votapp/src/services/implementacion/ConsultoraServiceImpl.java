package services.implementacion;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import dominio.Consultora;
import persistencia.interfaces.IConsultoraDAO;
import services.interfaces.ConsultoraService;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ConsultoraServiceImpl implements ConsultoraService{
	
	@EJB
	IConsultoraDAO consultoraDAO;

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

}
