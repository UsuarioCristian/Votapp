package persistencia.interfaces;

import javax.ejb.Local;

import dominio.Encuesta;

@Local
public interface IEncuestaDAO {
	
	public boolean crearEncuesta(Encuesta encuesta);
}
