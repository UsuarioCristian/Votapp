package services.interfaces;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import datas.DataEmergencia;
import datas.DataEncuesta;
import datas.DataRespuesta;

@RequestScoped
@Path("/encuesta")
public interface EncuestaService {
	
	@POST
	@Path("/protected/crear")
	@Consumes("application/json")
	public Response crearEncuesta(DataEncuesta dataEncuesta);
	
	@GET
	@Path("/getEncuestasByIdConsultora/{id}")
	@Produces({"application/json"})
	public Response getEncuestasByIdConsultora(@PathParam("id") final int id);
	
	@POST
	@Path("/protected/crearRespuesta")
	@Consumes("application/json")
	public Response crearRespuesta(DataRespuesta dataRespuesta);
	
	@POST
	@Path("/protected/alertarEmergencia")
	@Consumes("application/json")
	public Response crearEmergencia(DataEmergencia dataEmergencia);
	
	@GET
	@Path("/protected/getEncuestasFinalizadasByIdConsultora/{id}")
	@Produces({"application/json"})
	public Response getEncuestasFinalizadasByIdConsultora(@PathParam("id") final int id);
	
}
