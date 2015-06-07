package negocio.interfaces;

import javax.ejb.Local;
import javax.ws.rs.NotFoundException;

import utiles.UnauthorizedException;
import datas.DataUsuario;

@Local
public interface IUsuarioHandler {
	
	public String loginAdmin(DataUsuario dataUsuario) throws NotFoundException, UnauthorizedException;
	public String loginConsultora(DataUsuario dataUsuario) throws NotFoundException, UnauthorizedException;
}
