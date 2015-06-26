package services.implementacion;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.ws.rs.core.Response;

import negocio.interfaces.IEleccionHandler;
import datas.DataEleccion;
import services.interfaces.EleccionService;
import utiles.TipoEleccion;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class EleccionServiceImpl implements EleccionService {
	
	@EJB
	IEleccionHandler eleccionHdlr;

	@Override
	public Response crear(DataEleccion dataEleccion) {
		
		//Hay q modificar la funcion del handler para que maneje el dataEleccion
		if(eleccionHdlr.crearEleccion(TipoEleccion.Nacional, dataEleccion.getNombre(), dataEleccion.getDescripcion()))
			return Response.status(200).build();
		return null;
	}

}
