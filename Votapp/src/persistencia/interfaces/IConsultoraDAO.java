package persistencia.interfaces;

import javax.ejb.Local;

import dominio.Consultora;

@Local
public interface IConsultoraDAO {
	public Consultora findConsultoraById(int id);
}
