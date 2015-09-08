package services.interfaces;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import datas.DataEleccion;

@RequestScoped
@Path("/eleccion")
public interface EleccionService {
	
	@POST
	@Path("/protected/crear")
	@Consumes("application/json")
	public Response crear(DataEleccion dataEleccion);
	
	@GET
	@Path("/{id}")
	@Produces({"application/json"})
	public Response findById(@PathParam("id") final int id);
	
	@GET
	@Path("/protected/getEleccionesActuales")
	@Produces({"application/json"})
	public Response getEleccionesActuales();
	
	@GET
	@Path("/getElecciones")
	@Produces({"application/json"})
	public Response getElecciones();
	
	@POST
	@Path("/protected/borrar/{id}")
	@Produces({"application/json"})
	public Response borrarEleccion(@PathParam("id") final int id);
}
