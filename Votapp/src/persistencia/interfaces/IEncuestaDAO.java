package persistencia.interfaces;

import java.util.List;

import javax.ejb.Local;

import dominio.Encuesta;

@Local
public interface IEncuestaDAO {
	
	public boolean crearEncuesta(Encuesta encuesta);

	public List<Encuesta> getEncuestasByIdConsultora(int idConsultora);
}
