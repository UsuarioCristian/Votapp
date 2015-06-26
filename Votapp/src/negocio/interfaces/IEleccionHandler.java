package negocio.interfaces;

import javax.ejb.Local;

import utiles.TipoEleccion;

@Local
public interface IEleccionHandler {
	
	public boolean crearEleccion(TipoEleccion tipoEleccion, String nombre, String descripcion);
	
}
