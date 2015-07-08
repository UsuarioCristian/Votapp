package negocio.interfaces;

import javax.ejb.Local;

import datas.DataEncuesta;

@Local
public interface IEncuestaHandler {
	
	boolean crearEncuesta(DataEncuesta dataEncuesta);
}
