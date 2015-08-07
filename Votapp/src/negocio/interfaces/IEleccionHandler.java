package negocio.interfaces;

import java.util.List;

import javax.ejb.Local;

import datas.DataEleccion;
import dominio.Eleccion;

@Local
public interface IEleccionHandler {
	
	public boolean crearEleccion(DataEleccion dataEleccion);

	public Eleccion findEleccionById(int id);

	public List<DataEleccion> getEleccionesActuales();

	public List<DataEleccion> getElecciones();
	
}
