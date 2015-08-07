package persistencia.interfaces;

import java.util.List;

import javax.ejb.Local;

import dominio.Lista;

@Local
public interface IListaDAO {
	
		public List<Lista> getListasByIdPartido(int idPartido);
		public List<Lista> getListasByIdCandidato(int idCandidato);
}
