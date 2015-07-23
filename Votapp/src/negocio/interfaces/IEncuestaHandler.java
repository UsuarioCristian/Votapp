package negocio.interfaces;

import java.util.List;

import javax.ejb.Local;

import datas.DataEncuesta;

@Local
public interface IEncuestaHandler {
	
	boolean crearEncuesta(DataEncuesta dataEncuesta);

	public List<DataEncuesta> getEncuestaByIdConsultora(int idConsultora);
}
