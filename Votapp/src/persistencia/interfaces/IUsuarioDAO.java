package persistencia.interfaces;

import javax.ejb.Local;

import dominio.Usuario;

@Local
public interface IUsuarioDAO {
	public Usuario findUsuarioById(int id);
	public boolean crearUsuario(Usuario user);
	public Usuario findUsuario(String username);
	
}
