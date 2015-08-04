package negocio.interfaces;

import java.util.List;

import javax.ejb.Local;

import datas.DataEmergencia;
import datas.DataEncuesta;
import datas.DataRespuesta;

@Local
public interface IEncuestaHandler {
	
	boolean crearEncuesta(DataEncuesta dataEncuesta);

	public List<DataEncuesta> getEncuestaByIdConsultora(int idConsultora);

	public boolean crearRespuesta(DataRespuesta dataRespuesta);

	boolean crearEmergencia(DataEmergencia dataEmergencia);

	public List<DataEncuesta> getEncuestasFinalizadasByIdConsultora(int id);
}
