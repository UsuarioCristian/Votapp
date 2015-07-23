package persistencia.interfaces;

import java.util.List;

import javax.ejb.Local;

import dominio.Departamento;
import dominio.Eleccion;

@Local
public interface IEleccionDAO {
	
	public boolean crearEleccion(Eleccion eleccion);

	Eleccion findEleccionById(int id);

	public List<Eleccion> getEleccionesActuales();

	public List<Eleccion> getElecciones();
	
	public List<Departamento> getDeptosByEleccionID(int id);
	
}
