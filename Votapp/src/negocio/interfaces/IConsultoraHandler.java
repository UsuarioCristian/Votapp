package negocio.interfaces;

import javax.ejb.Local;

import datas.DataConsultora;

@Local
public interface IConsultoraHandler {

	boolean altaConsultora(DataConsultora dataConsultora);

}
