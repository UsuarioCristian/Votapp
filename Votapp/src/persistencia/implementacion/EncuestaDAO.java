package persistencia.implementacion;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import persistencia.interfaces.IEncuestaDAO;
import dominio.Emergencia;
import dominio.Encuesta;
import dominio.Respuesta;

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

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<Encuesta> getEncuestasByIdConsultora(int idConsultora) {
		try {
			return em.createNamedQuery("Encuesta.getEncuestasByIdConsultora", Encuesta.class)
					.setParameter("idConsultora", idConsultora)
					.getResultList();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public boolean crearRespuesta(Respuesta respuesta) {
		try {
			em.persist(respuesta);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Encuesta findEncuestaById(int idEncuesta) {
		try {
			return em.find(Encuesta.class, idEncuesta);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Emergencia findEmergenciaById(int idEmergencia) {
		try {
			return em.find(Emergencia.class, idEmergencia);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public boolean crearEmergencia(Emergencia emergencia) {
		try {
			em.persist(emergencia);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<Respuesta> getRespuestas(int idEncuesta) {
		try {
			return em.createNamedQuery("Encuesta.getRespuestas", Respuesta.class)
					.setParameter("idEncuesta", idEncuesta)
					.getResultList();
		} catch (Exception e) {
			return null;
		}
	}

}
