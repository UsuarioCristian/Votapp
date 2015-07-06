package services.implementacion;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import negocio.interfaces.IEleccionHandler;
import services.interfaces.EleccionService;
import datas.DataEleccion;
import dominio.Eleccion;

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

	@Override
	public Response findById(int id) {
		Eleccion eleccion = eleccionHdlr.findEleccionById(id);
		if (eleccion==null){
			return Response.status(Status.NOT_FOUND).build();
		}
		
		return Response.ok(eleccion).build();
	}

	@Override
	public Response getEleccionesActuales() {
		List<DataEleccion> elecciones = eleccionHdlr.getEleccionesActuales();
		return Response.ok(elecciones).build();
	}
	
	@Override
	public Response getElecciones() {
		List<DataEleccion> elecciones = eleccionHdlr.getElecciones();
		return Response.ok(elecciones).build();
	}

}
