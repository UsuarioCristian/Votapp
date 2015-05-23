package services.implementacion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import negocio.interfaces.ISecurityService;
import persistencia.interfaces.IUsuarioDAO;
import services.interfaces.UsuarioService;
import dominio.Usuario;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class UsuarioServiceImpl implements UsuarioService{
	
	@EJB
	IUsuarioDAO usuarioDAO;
	
	@EJB
	ISecurityService securityService;

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
	@Transactional
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Response crearUsuario(Usuario user) {
		
		boolean ok = usuarioDAO.crearUsuario(user);
		if (ok){
			return Response.ok(user).build();
		}else{
			return Response.status(Status.NOT_FOUND).build();
		}
	}

	@Override
	@Transactional
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Response loginAdmin(Usuario user) {

		Usuario usuario = usuarioDAO.findUsuario(user.getUsername());
		if (usuario == null) {
			return Response.status(Status.NOT_FOUND).build();
		} else {
			// armar el token y enviarselo
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("username", usuario.getUsername());
			map.put("Admin", true);

			String token = securityService.crearToken(map);

			List<String> lista = new ArrayList<String>();
			lista.add(token);

			return Response.ok(lista).build();
		}

	}

}
