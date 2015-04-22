package services.interfaces;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@RequestScoped
@Path("/consultoras")
public interface ConsultoraService {
	
	@GET
	@Path("/{id}")
	@Produces({"application/json"})
	public Response findById(@PathParam("id") final int id);
}
