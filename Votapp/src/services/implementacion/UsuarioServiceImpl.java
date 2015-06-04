package services.implementacion;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import negocio.interfaces.ISecurityService;
import negocio.interfaces.IUsuarioHandler;
import services.interfaces.UsuarioService;
import utiles.UnauthorizedException;
import datas.DataUsuario;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class UsuarioServiceImpl implements UsuarioService {

	@EJB
	IUsuarioHandler usuarioHandler;

	@EJB
	ISecurityService securityService;

	@Override
	@Transactional
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Response loginAdmin(DataUsuario user) {

		try {
			String token = usuarioHandler.LoginAdmin(user);
			return Response.ok(token).build();

		} catch (NotFoundException e) {
			return Response.status(Status.NOT_FOUND).build();
		} catch (UnauthorizedException e) {
			return Response.status(Status.UNAUTHORIZED).build();
		}

	}

}
