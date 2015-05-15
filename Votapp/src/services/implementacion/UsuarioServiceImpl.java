package services.implementacion;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import persistencia.interfaces.IUsuarioDAO;
import services.interfaces.UsuarioService;
import dominio.Usuario;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class UsuarioServiceImpl implements UsuarioService{
	
	@EJB
	IUsuarioDAO usuarioDAO;

	@Transactional
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	@Override
	public Response findUserById(int id) {
		Usuario usuario = usuarioDAO.findUsuarioById(id);
		if (usuario==null){
			return Response.status(Status.NOT_FOUND).build();
		}
		
		return Response.ok(usuario).build();
	}

	@Override
	public Response crearUsuario(Usuario user) {
		
		boolean ok = usuarioDAO.crearUsuario(user);
		if (ok){
			return Response.ok("El usuario : "+user.getUsername()+" se dio de alta correctamente.").build();
		}else{
			return Response.status(Status.NOT_FOUND).build();
		}
	}

}
