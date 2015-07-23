package persistencia.interfaces;

import javax.ejb.Local;

import dominio.Candidato;

@Local
public interface ICandidatoDAO {
	
	public Candidato findCandidatoById(int id);
	
}
