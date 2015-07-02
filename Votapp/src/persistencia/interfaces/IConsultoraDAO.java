package persistencia.interfaces;

import javax.ejb.Local;

import dominio.Consultora;
import dominio.Encuestador;

@Local
public interface IConsultoraDAO {
	public Consultora findConsultoraById(int id);
	public boolean crearConsultora(Consultora consultora);
	public boolean crearEncuestador(Encuestador encuestador);
}
