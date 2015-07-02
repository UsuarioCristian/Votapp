package negocio.implementacion;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import negocio.interfaces.IEleccionHandler;
import persistencia.interfaces.IEleccionDAO;
import datas.DataEleccion;
import dominio.Eleccion;
import dominio.EleccionNacional;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class EleccionHandler implements IEleccionHandler {
	
	@EJB
	IEleccionDAO eleccionDAO;

	@Override
	public boolean crearEleccion(DataEleccion data) {

		boolean exito = false;

		switch (data.getTipoEleccion()) {
			case Nacional:
				Eleccion eleccion = new EleccionNacional(data.getNombre(), data.getDescripcion(), data.getFecha());
				boolean paso1 = eleccion.asignarPartidos(data.getDataPartidos());
				boolean paso2 = eleccion.asignarListas(data.getDataListas());
				boolean paso3 = eleccion.asignarCandidatos(data.getDataCandidatos());
				
				if(paso1 && paso2 && paso3)
					exito = eleccionDAO.crearEleccion(eleccion);
				else
					System.out.println("ERROR...paso1= "+paso1+" paso2= "+paso2+ " paso3= "+paso3);
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

	@Override
	public Eleccion findEleccionById(int id) {
		Eleccion eleccion = eleccionDAO.findEleccionById(id);
		if (eleccion == null){
			return null;
		}
		return eleccion;
	}

}
