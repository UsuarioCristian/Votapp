package negocio.interfaces;

import javax.ejb.Local;

import datas.DataEleccion;
import dominio.Eleccion;

@Local
public interface IEleccionHandler {
	
	public boolean crearEleccion(DataEleccion dataEleccion);

	public Eleccion findEleccionById(int id);
	
}
