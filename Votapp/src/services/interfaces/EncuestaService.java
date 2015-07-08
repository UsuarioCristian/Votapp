package services.interfaces;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import datas.DataEncuesta;

@RequestScoped
@Path("/encuesta")
public interface EncuestaService {
	
	@POST
	@Path("/protected/crear")
	@Consumes("application/json")
	public Response crearEncuesta(DataEncuesta dataEncuesta);
	
}
