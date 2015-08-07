package persistencia.implementacion;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import dominio.Lista;
import persistencia.interfaces.IListaDAO;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ListaDAO implements IListaDAO {
	
	@PersistenceContext
	private EntityManager em;

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<Lista> getListasByIdPartido(int idPartido) {
		try {
			return em.createNamedQuery("Lista.getListasByIdPartido", Lista.class)
					.setParameter("idPartido", idPartido)
					.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}

	@Override
	public List<Lista> getListasByIdCandidato(int idCandidato) {
		try {
			return em.createNamedQuery("Lista.getListasByIdCandidato", Lista.class)
					.setParameter("idCandidato", idCandidato)
					.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
