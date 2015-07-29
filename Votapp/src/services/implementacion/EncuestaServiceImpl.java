package services.implementacion;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.ws.rs.core.Response;

import negocio.interfaces.IEncuestaHandler;
import datas.DataEncuesta;
import datas.DataRespuesta;
import services.interfaces.EncuestaService;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class EncuestaServiceImpl implements EncuestaService {
	
	@EJB
	IEncuestaHandler encuestaHdlr;
	
	@Override
	public Response crearEncuesta(DataEncuesta dataEncuesta) {
		
		if(encuestaHdlr.crearEncuesta(dataEncuesta))
			return Response.status(200).build();
		return null;
		
	}

	@Override
	public Response getEncuestasByIdConsultora(int id) {
		
		List<DataEncuesta> dataEncuestas = encuestaHdlr.getEncuestaByIdConsultora(id);
		
		return Response.ok(dataEncuestas).build();
	}

	@Override
	public Response crearRespuesta(DataRespuesta dataRespuesta) {

		if(encuestaHdlr.crearRespuesta(dataRespuesta))
			return Response.status(200).build();
		return null;
	}

}
