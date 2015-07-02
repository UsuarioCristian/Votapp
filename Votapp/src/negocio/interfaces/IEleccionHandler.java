package negocio.interfaces;

import javax.ejb.Local;

import datas.DataEleccion;

@Local
public interface IEleccionHandler {
	
	public boolean crearEleccion(DataEleccion dataEleccion);
	
}
