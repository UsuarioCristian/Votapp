package persistencia.implementacion;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import dominio.Encuesta;
import persistencia.interfaces.IEncuestaDAO;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class EncuestaDAO implements IEncuestaDAO {
	
	@PersistenceContext
	private EntityManager em;
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public boolean crearEncuesta(Encuesta encuesta) {
		try {
			em.persist(encuesta);
			return true;
		} catch (Exception e) {
			System.out.println("Ocurrio un error en EncuestaDAO.crearEncuesta");
			return false;
		}
		
	}

}
