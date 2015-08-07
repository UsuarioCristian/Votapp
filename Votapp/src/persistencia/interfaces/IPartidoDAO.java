package persistencia.interfaces;

import javax.ejb.Local;

import dominio.Partido;

@Local
public interface IPartidoDAO {
	
	public Partido findPartidoById(int id);
	
}
