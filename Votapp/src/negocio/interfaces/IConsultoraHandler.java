package negocio.interfaces;

import java.util.List;

import javax.ejb.Local;

import datas.DataConsultora;
import datas.DataEmergencia;
import datas.DataUsuario;

@Local
public interface IConsultoraHandler {

	boolean altaConsultora(DataConsultora dataConsultora);

	boolean altaEncuestador(DataUsuario dataUsuario);
	public DataConsultora getDataConsultoraByUsername(String username);

	List<DataEmergencia> getAllEmergencias(int idConsultora);

	public boolean notificarEmergencia(DataEmergencia data);

}
