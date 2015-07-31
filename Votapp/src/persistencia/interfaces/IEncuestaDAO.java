package persistencia.interfaces;

import java.util.List;

import javax.ejb.Local;

import dominio.Emergencia;
import dominio.Encuesta;
import dominio.Respuesta;

@Local
public interface IEncuestaDAO {
	
	public boolean crearEncuesta(Encuesta encuesta);

	public List<Encuesta> getEncuestasByIdConsultora(int idConsultora);
	
	public boolean crearRespuesta(Respuesta respuesta);

	public Encuesta findEncuestaById(int idEncuesta);
	
	public Emergencia findEmergenciaById(int idEmergencia);

	public boolean crearEmergencia(Emergencia emergencia);
}
