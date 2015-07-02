package services.interfaces;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import datas.DataEleccion;

@RequestScoped
@Path("/eleccion")
public interface EleccionService {
	
	@POST
	@Path("/protected/crear")
	@Consumes("application/json")
	public Response crear(DataEleccion dataEleccion);
	
}
