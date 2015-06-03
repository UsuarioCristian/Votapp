package persistencia.implementacion;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import dominio.Consultora;
import persistencia.interfaces.IConsultoraDAO;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ConsultoraDAO implements IConsultoraDAO {
	
	@PersistenceContext
	private EntityManager em;

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Consultora findConsultoraById(int id) {
		try {
			return em.find(Consultora.class, id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean crearConsultora(Consultora consultora) {
		try {
			em.persist(consultora);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

}
