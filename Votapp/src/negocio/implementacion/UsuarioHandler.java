package negocio.implementacion;

import java.util.HashMap;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.ws.rs.NotFoundException;

import negocio.interfaces.ISecurityService;
import negocio.interfaces.IUsuarioHandler;
import persistencia.interfaces.IUsuarioDAO;
import utiles.UnauthorizedException;
import datas.DataUsuario;
import dominio.Usuario;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class UsuarioHandler implements IUsuarioHandler {

	@EJB
	IUsuarioDAO usuarioDAO;
	
	@EJB
	ISecurityService securityService;

	@Override
	public String LoginAdmin(DataUsuario dataUsuario) throws NotFoundException, UnauthorizedException {

		Usuario usuario = usuarioDAO.findUsuario(dataUsuario.getUsername());

		if (usuario == null || !(usuario.getPassword().equalsIgnoreCase(dataUsuario.getPassword()))) {
			throw new NotFoundException();
		} else {
			if (usuario.getUsername().equals("Admin")) {
				// armar el token y enviarselo
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("username", usuario.getUsername());
				map.put("Admin", true);

				String token = securityService.crearToken(map);

				return token;
			} else {
				throw new UnauthorizedException();
			}

		}

	}

}
