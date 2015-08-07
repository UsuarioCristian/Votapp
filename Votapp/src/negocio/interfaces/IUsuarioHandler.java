package negocio.interfaces;

import javax.ejb.Local;

import utiles.UnauthorizedException;
import utiles.UserNotFoundException;
import datas.DataUsuario;

@Local
public interface IUsuarioHandler {
	
	public String loginAdmin(DataUsuario dataUsuario) throws UserNotFoundException, UnauthorizedException;
	public String loginConsultora(DataUsuario dataUsuario) throws UserNotFoundException, UnauthorizedException;
	public String loginEncuestador(DataUsuario dataUsuario) throws UserNotFoundException, UnauthorizedException;
}
