package persistencia.implementacion;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import dominio.Partido;
import persistencia.interfaces.IPartidoDAO;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class PartidoDAO implements IPartidoDAO {
	
	@PersistenceContext
	private EntityManager em;
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Partido findPartidoById(int id) {
		try {
			return em.find(Partido.class, id);
		} catch (Exception e) {
			return null;
		}
	}

}
