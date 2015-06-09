package services.interfaces;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import datas.DataConsultora;
import datas.DataUsuario;

@RequestScoped
@Path("/consultoras")
public interface ConsultoraService {
	
	@GET
	@Path("/{id}")
	@Produces({"application/json"})
	public Response findById(@PathParam("id") final int id);
	
	@POST
	@Path("/protected/crear")
	@Consumes("application/json")
	public Response crear(DataConsultora dataConsultora);
	
	@POST
	@Path("/protected/crearEncuestador")
	@Consumes("application/json")
	public Response crearEncuestador(DataUsuario dataUsuario);
	
}
