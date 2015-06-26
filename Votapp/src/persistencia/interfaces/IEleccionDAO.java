package persistencia.interfaces;

import javax.ejb.Local;

import dominio.Eleccion;

@Local
public interface IEleccionDAO {
	
	public boolean crearEleccion(Eleccion eleccion);
	
}
