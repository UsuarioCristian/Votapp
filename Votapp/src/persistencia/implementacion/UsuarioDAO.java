package persistencia.implementacion;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import persistencia.interfaces.IUsuarioDAO;
import dominio.Usuario;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class UsuarioDAO implements IUsuarioDAO {
	
	@PersistenceContext
	private EntityManager em;


	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Usuario findUsuarioById(int id) {
		try {
			return em.find(Usuario.class, id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public boolean crearUsuario(Usuario user) {
		try {
			em.persist(user);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Usuario findUsuario(String username) {
		try {
			return em.createNamedQuery("Usuario.findUserByName", Usuario.class)
					.setParameter("username", username)
					.getSingleResult();
		} catch (Exception e) {
			System.out.println("El usuario no existe");
		}
		return null;
	}

}
