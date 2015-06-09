package negocio.interfaces;

import javax.ejb.Local;

import datas.DataConsultora;
import datas.DataUsuario;

@Local
public interface IConsultoraHandler {

	boolean altaConsultora(DataConsultora dataConsultora);

	boolean altaEncuestador(DataUsuario dataUsuario);

}
