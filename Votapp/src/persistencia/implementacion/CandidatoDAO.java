package persistencia.implementacion;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import dominio.Candidato;
import persistencia.interfaces.ICandidatoDAO;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class CandidatoDAO implements ICandidatoDAO {
	
	@PersistenceContext
	private EntityManager em;
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Candidato findCandidatoById(int id) {
		
		try {
			return em.find(Candidato.class, id);
		} catch (Exception e) {
			return null;
		}
		
	}

}
