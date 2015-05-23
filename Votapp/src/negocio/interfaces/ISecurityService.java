package negocio.interfaces;

import java.util.Map;

import javax.ejb.Local;

@Local
public interface ISecurityService {
	
	public String crearToken(Map<String, Object> mapToToken);
	public String actualizarToken(String token);
	public Map<String, Object> getMapFromToken(String token);
}
