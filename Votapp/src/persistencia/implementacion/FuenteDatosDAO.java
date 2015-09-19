package persistencia.implementacion;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import dominio.FuenteDatos;
import dominio.Lista;
import persistencia.interfaces.IFuenteDatosDAO;;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class FuenteDatosDAO implements IFuenteDatosDAO {
	
	@PersistenceContext
	private EntityManager em;
	
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public FuenteDatos findFuenteDatosById(int id) {
		try {
			return em.find(FuenteDatos.class, id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<FuenteDatos> getFuentesByIdEleccion(int idEleccion) {
		try {
			return em.createNamedQuery("Lista.getFuentesByIdEleccion", FuenteDatos.class)
					.setParameter("idEleccion", idEleccion)
					.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	



}
