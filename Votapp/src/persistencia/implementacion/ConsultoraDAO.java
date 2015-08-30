package persistencia.implementacion;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import persistencia.interfaces.IConsultoraDAO;
import dominio.Consultora;
import dominio.Emergencia;
import dominio.Encuestador;

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
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public boolean crearConsultora(Consultora consultora) {
		try {
			em.persist(consultora);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public boolean crearEncuestador(Encuestador encuestador) {
		try {
			em.persist(encuestador);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Consultora getConsultoraByUsername(String username) {
		try {
			return em.createNamedQuery("Consultora.getConsultoraByUsername", Consultora.class).
					setParameter("username", username).
					getSingleResult();
		} catch (Exception e) {
			System.out.println("Ocurrio un error en ConsultoraDAO.getConsultoraByIdUsuario");
			return null;
		}
		
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Encuestador findEncuestadorById(int idEncuestador) {
		try {
			return em.find(Encuestador.class, idEncuestador);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Emergencia> getAllEmergencias(int idConsultora) {
		try {
			return em.createNamedQuery("Consultora.getAllEmergencias", Emergencia.class).
					setParameter("idConsultora", idConsultora).
					getResultList();
		} catch (Exception e) {
			System.out.println("Ocurrio un error en ConsultoraDAO.getAllEmergencias");
			return null;
		}
	}

}
