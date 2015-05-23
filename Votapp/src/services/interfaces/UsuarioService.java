package services.interfaces;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import dominio.Usuario;

@RequestScoped
@Path("/usuario")
public interface UsuarioService {
	
	@GET
	@Path("/{id}")
	@Produces({"application/json"})
	public Response findUserById(@PathParam("id") final int id);
	
	@POST
	@Path("/protected/crear")
	@Consumes("application/json")
	public Response crearUsuario(Usuario user);
	
	@POST
	@Path("/loginAdmin")
	@Consumes("application/json")
	@Produces({"application/json"})
	public Response loginAdmin(Usuario user);
	
}
