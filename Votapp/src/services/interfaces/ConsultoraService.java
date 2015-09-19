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
import datas.DataEmergencia;
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
	
	@GET
	@Path("/protected/getAllEmergencias/{id}")
	@Consumes("application/json")
	@Produces({"application/json"})
	public Response getAllEmergencias(@PathParam("id") final int id);
	
	@POST
	@Path("/protected/notificarEmergencia")
	@Consumes("application/json")
	public Response notificarEmergencia(DataEmergencia data);
	
	@GET
	@Path("/protected/thereANewEmergency/{id}")
	@Consumes("application/json")
	@Produces({"application/json"})
	public Response thereANewEmergency(@PathParam("id") final int id);
	
}
