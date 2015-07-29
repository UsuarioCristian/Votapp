package persistencia.interfaces;

import java.util.List;

import javax.ejb.Local;

import dominio.Encuesta;
import dominio.Respuesta;

@Local
public interface IEncuestaDAO {
	
	public boolean crearEncuesta(Encuesta encuesta);

	public List<Encuesta> getEncuestasByIdConsultora(int idConsultora);
	
	public boolean crearRespuesta(Respuesta respuesta);

	public Encuesta findEncuestaById(int idEncuesta);
}
