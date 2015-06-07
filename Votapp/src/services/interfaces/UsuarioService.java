package services.interfaces;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import datas.DataUsuario;

@RequestScoped
@Path("/usuario")
public interface UsuarioService {

		
	@POST
	@Path("/loginAdmin")
	@Consumes("application/json")
	@Produces({"text/plain"})
	public Response loginAdmin(DataUsuario user);
	
	@POST
	@Path("/loginConsultora")
	@Consumes("application/json")
	@Produces({"text/plain"})
	public Response loginConsultora(DataUsuario user);
	
}
