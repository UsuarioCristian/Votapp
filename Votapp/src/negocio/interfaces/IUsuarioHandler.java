package negocio.interfaces;

import javax.ejb.Local;
import javax.ws.rs.NotFoundException;

import utiles.UnauthorizedException;
import datas.DataUsuario;

@Local
public interface IUsuarioHandler {
	
	public String LoginAdmin(DataUsuario dataUsuario) throws NotFoundException, UnauthorizedException;
}
