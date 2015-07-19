package negocio.implementacion;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import negocio.interfaces.IEleccionHandler;
import persistencia.interfaces.IEleccionDAO;
import utiles.TipoEleccion;
import datas.DataEleccion;
import dominio.Eleccion;
import dominio.EleccionDepartamental;
import dominio.EleccionNacional;
import dominio.Imagen;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class EleccionHandler implements IEleccionHandler {
	
	@EJB
	IEleccionDAO eleccionDAO;

	@Override
	public boolean crearEleccion(DataEleccion data) {

		boolean exito = false;
		Eleccion eleccion = null;
		Imagen imagen = null;
		boolean paso1, paso2, paso3;

		switch (data.getTipoEleccion()) {
			case Nacional:
				eleccion = new EleccionNacional(data.getNombre(), data.getDescripcion(), data.getFecha());
				
				imagen = new Imagen();
				imagen.setName(data.getLogo().getName());
				imagen.setFile(data.getLogo().getFile());
				imagen.setTipo(data.getLogo().getTipo());
				eleccion.setImagen(imagen);
				
				paso1 = eleccion.asignarPartidos(data.getDataPartidos());
				paso2 = eleccion.asignarListas(data.getDataListas());
				paso3 = eleccion.asignarCandidatos(data.getDataCandidatos());
				
				if(paso1 && paso2 && paso3)
					exito = eleccionDAO.crearEleccion(eleccion);
				else
					System.out.println("ERROR...paso1= "+paso1+" paso2= "+paso2+ " paso3= "+paso3);
			break;
			
			case Departamental:
				eleccion = new EleccionDepartamental(data.getNombre(), data.getDescripcion(), data.getFecha());
				
				imagen = new Imagen();
				imagen.setName(data.getLogo().getName());
				imagen.setFile(data.getLogo().getFile());
				imagen.setTipo(data.getLogo().getTipo());
				eleccion.setImagen(imagen);
				
				paso1 = eleccion.asignarPartidos(data.getDataPartidos());
				paso2 = eleccion.asignarListas(data.getDataListas());
				paso3 = eleccion.asignarCandidatos(data.getDataCandidatos());
				
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

	@Override
	public List<DataEleccion> getEleccionesActuales() {
		
		List<Eleccion> elecciones = eleccionDAO.getEleccionesActuales();
		List<DataEleccion> dataElecciones = new ArrayList<DataEleccion>();
		
		for (Eleccion eleccion : elecciones) {
			DataEleccion dataEleccion = new DataEleccion();
			dataEleccion.setNombre(eleccion.getNombre());
			dataEleccion.setFecha(eleccion.getFecha());
			dataEleccion.setId(eleccion.getId());
			switch (eleccion.getClass().getName()) {
				case "dominio.EleccionNacional":
					dataEleccion.setTipoEleccion(TipoEleccion.Nacional);
					break;
				case "dominio.EleccionDepartamental":
					dataEleccion.setTipoEleccion(TipoEleccion.Departamental);
					break;
				case "dominio.EleccionOtro":
					dataEleccion.setTipoEleccion(TipoEleccion.Otro);
					break;
				default:
					break;
			}
			
			dataElecciones.add(dataEleccion);
		}
		
		return dataElecciones;
	}

	@Override
	public List<DataEleccion> getElecciones() {
		List<Eleccion> elecciones = eleccionDAO.getElecciones();
		List<DataEleccion> dataElecciones = new ArrayList<DataEleccion>();
		
		for (Eleccion eleccion : elecciones) {
			DataEleccion dataEleccion = new DataEleccion();
			dataEleccion.setNombre(eleccion.getNombre());
			dataEleccion.setDescripcion(eleccion.getDescripcion());
			dataEleccion.setFecha(eleccion.getFecha());
			dataEleccion.setId(eleccion.getId());
			switch (eleccion.getClass().getName()) {
				case "dominio.EleccionNacional":
					dataEleccion.setTipoEleccion(TipoEleccion.Nacional);
					break;
				case "dominio.EleccionDepartamental":
					dataEleccion.setTipoEleccion(TipoEleccion.Departamental);
					break;
				case "dominio.EleccionOtro":
					dataEleccion.setTipoEleccion(TipoEleccion.Otro);
					break;
				default:
					break;
			}
			
			dataElecciones.add(dataEleccion);
		}
		
		return dataElecciones;
	}

}
