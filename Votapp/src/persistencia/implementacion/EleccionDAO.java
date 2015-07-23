package persistencia.implementacion;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import persistencia.interfaces.IEleccionDAO;
import dominio.Departamento;
import dominio.Eleccion;

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
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Eleccion findEleccionById(int id) {
		try {
			return em.find(Eleccion.class, id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<Eleccion> getEleccionesActuales() {
		Date today = new Date();
		try {
			return em.createNamedQuery("Eleccion.getEleccionesActuales", Eleccion.class)
					.setParameter("today", today)
					.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}		
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<Eleccion> getElecciones() {
		try {
			return em.createNamedQuery("Eleccion.getElecciones", Eleccion.class)
					.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}		
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<Departamento> getDeptosByEleccionID(int id) {
		try {
			return em.createNamedQuery("Eleccion.getDeptosByEleccionID", Departamento.class)
					.setParameter("idEleccion", id)
					.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}	
	}

	
}
