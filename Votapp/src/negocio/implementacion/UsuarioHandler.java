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
import dominio.AdminConsultora;
import dominio.Encuestador;
import dominio.Usuario;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class UsuarioHandler implements IUsuarioHandler {

	@EJB
	IUsuarioDAO usuarioDAO;
	
	@EJB
	ISecurityService securityService;

	@Override
	public String loginAdmin(DataUsuario dataUsuario) throws NotFoundException, UnauthorizedException {

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

	@Override
	public String loginConsultora(DataUsuario dataUsuario) throws NotFoundException, UnauthorizedException {
		Usuario usuario = usuarioDAO.findUsuario(dataUsuario.getUsername());
		if (usuario == null || !(usuario.getPassword().equalsIgnoreCase(dataUsuario.getPassword()))) {
			throw new NotFoundException();
		} else {
			// REFLECTION para saber el nombre de la clase del objeto usuario			
			if (usuario.getClass() == AdminConsultora.class) {
				// armar el token y enviarselo
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("username", usuario.getUsername());
				map.put("Admin", false);
				map.put("AdminConsultora", true);

				String token = securityService.crearToken(map);

				return token;
			} else {
				throw new UnauthorizedException();
			}

		}
	}
	
	@Override
	public String loginEncuestador(DataUsuario dataUsuario) throws NotFoundException, UnauthorizedException {
		Usuario usuario = usuarioDAO.findUsuario(dataUsuario.getUsername());
		if (usuario == null || !(usuario.getPassword().equalsIgnoreCase(dataUsuario.getPassword()))) {
			throw new NotFoundException();
		} else {
			// REFLECTION para saber el nombre de la clase del objeto usuario			
			if (usuario.getClass() == Encuestador.class) {
				// armar el token y enviarselo
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("username", usuario.getUsername());
				map.put("Admin", false);
				map.put("Encuestador", true);

				String token = securityService.crearToken(map);

				return token;
			} else {
				throw new UnauthorizedException();
			}

		}
	}

}
