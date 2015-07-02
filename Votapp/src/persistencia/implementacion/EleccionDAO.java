package persistencia.implementacion;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import dominio.Eleccion;
import persistencia.interfaces.IEleccionDAO;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class EleccionDAO implements IEleccionDAO {
	
	@PersistenceContext
	private EntityManager em;

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public boolean crearEleccion(Eleccion eleccion) {
		try {
			em.persist(eleccion);
			return true;
		} catch (Exception e) {
			return false;
		}		
	}

}
