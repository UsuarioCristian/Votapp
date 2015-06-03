package negocio.implementacion;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import persistencia.interfaces.IConsultoraDAO;
import negocio.interfaces.IConsultoraHandler;
import datas.DataConsultora;
import dominio.AdminConsultora;
import dominio.Consultora;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ConsultoraHandler implements IConsultoraHandler {

	@EJB
	IConsultoraDAO consultoraDAO;
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public boolean altaConsultora(DataConsultora dataConsultora) {
		
		Consultora consultora = new Consultora();
		AdminConsultora adminConsultora = new AdminConsultora();
		
		consultora.setNombre(dataConsultora.getNombre());
		consultora.setDescripcion(dataConsultora.getDescripcion());
		
		adminConsultora.setUsername(dataConsultora.getNombreAdminConsultora());
		adminConsultora.setPassword(dataConsultora.getPassAdminConsultora());
		adminConsultora.setConsultora(consultora);
		
		consultora.setAdminConsultora(adminConsultora);
				
		return consultoraDAO.crearConsultora(consultora);
	}

}
