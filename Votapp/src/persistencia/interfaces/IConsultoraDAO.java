package persistencia.interfaces;

import java.util.List;

import javax.ejb.Local;

import dominio.Consultora;
import dominio.Emergencia;
import dominio.Encuestador;

@Local
public interface IConsultoraDAO {
	public Consultora findConsultoraById(int id);
	public boolean crearConsultora(Consultora consultora);
	public boolean crearEncuestador(Encuestador encuestador);
	public Consultora getConsultoraByUsername(String username);
	public Encuestador findEncuestadorById(int idEncuestador);
	public List<Emergencia> getAllEmergencias(int idConsultora);
	public Emergencia findEmergenciaById(int id);
	public void actualizarEmergencia(Emergencia emergencia);
}
