package negocio.implementacion;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import persistencia.interfaces.IEleccionDAO;
import dominio.Eleccion;
import dominio.EleccionNacional;
import utiles.TipoEleccion;
import negocio.interfaces.IEleccionHandler;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class EleccionHandler implements IEleccionHandler {
	
	@EJB
	IEleccionDAO eleccionDAO;

	@Override
	public boolean crearEleccion(TipoEleccion tipoEleccion, String nombre, String descripcion) {

		boolean exito = false;

		switch (tipoEleccion) {
			case Nacional:
				Eleccion eleccion = new EleccionNacional(nombre, descripcion);
				exito = eleccionDAO.crearEleccion(eleccion);
				break;

			default:
				break;
		}

		if (exito)
			System.out.println("Se creo la Eleccion correctamente");
		else
			System.out.println("ocurrio un error en la creacion de la Eleccion");
		
		return exito;

	}

}
