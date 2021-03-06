package negocio.implementacion;

import java.util.HashMap;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import negocio.interfaces.IConsultoraHandler;
import negocio.interfaces.ISecurityService;
import negocio.interfaces.IUsuarioHandler;
import persistencia.interfaces.IUsuarioDAO;
import utiles.UnauthorizedException;
import utiles.UserNotFoundException;
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
	
	@EJB
	IConsultoraHandler consultoraHdlr;

	@Override
	public String loginAdmin(DataUsuario dataUsuario) throws UserNotFoundException, UnauthorizedException {

		Usuario usuario = usuarioDAO.findUsuario(dataUsuario.getUsername());

		if (usuario == null || !(usuario.getPassword().equalsIgnoreCase(dataUsuario.getPassword()))) {
			throw new UserNotFoundException();
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
	public String loginConsultora(DataUsuario dataUsuario) throws UserNotFoundException, UnauthorizedException {
		Usuario usuario = usuarioDAO.findUsuario(dataUsuario.getUsername());
		if (usuario == null || !(usuario.getPassword().equalsIgnoreCase(dataUsuario.getPassword()))) {
			throw new UserNotFoundException();
		} else {
			// REFLECTION para saber el nombre de la clase del objeto usuario
			if (usuario.getClass() == AdminConsultora.class) {
				// armar el token y enviarselo
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("username", usuario.getUsername());
				map.put("Admin", false);
				map.put("AdminConsultora", true);

				// Castear usuario a AdminConsultora para acceder a datos q solo pertenecen al hijo (AdminConsultora)
				// Si bien, el id se podria haber obtenido del padre, igual dejo esto x las dudas q se necesiten mas datos 
				AdminConsultora adminConsultora = (AdminConsultora) usuario;
				map.put("consultoraID", adminConsultora.getConsultora().getId());

				String token = securityService.crearToken(map);

				return token;
			} else {
				throw new UnauthorizedException();
			}

		}
	}

	@Override
	public String loginEncuestador(DataUsuario dataUsuario) throws UserNotFoundException, UnauthorizedException {
		Usuario usuario = usuarioDAO.findUsuario(dataUsuario.getUsername());
		if (usuario == null || !(usuario.getPassword().equalsIgnoreCase(dataUsuario.getPassword()))) {
			throw new UserNotFoundException();
		} else {
			// REFLECTION para saber el nombre de la clase del objeto usuario			
			if (usuario.getClass() == Encuestador.class) {
				// armar el token y enviarselo
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("username", usuario.getUsername());
				map.put("Admin", false);
				map.put("Encuestador", true);
				
				Encuestador encuestador = (Encuestador) usuario;
				map.put("idUsuario", encuestador.getId());
				map.put("idConsultora", encuestador.getConsultora().getId());
				AdminConsultora admin = encuestador.getConsultora().getAdminConsultora();
				if (admin != null){
					map.put("numConsultora", admin.getCelular());
				}
				
				String token = securityService.crearToken(map);

				return token;
			} else {
				throw new UnauthorizedException();
			}

		}
	}

}
