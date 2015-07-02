package services.implementacion;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.ws.rs.core.Response;

import negocio.interfaces.IEleccionHandler;
import services.interfaces.EleccionService;
import datas.DataEleccion;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class EleccionServiceImpl implements EleccionService {
	
	@EJB
	IEleccionHandler eleccionHdlr;

	@Override
	public Response crear(DataEleccion dataEleccion) {
				
		if(eleccionHdlr.crearEleccion(dataEleccion))
			return Response.status(200).build();
		return null;
	}

}
