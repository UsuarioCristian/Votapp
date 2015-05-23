package negocio.implementacion;

import java.util.Map;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import negocio.interfaces.ISecurityService;

import com.auth0.jwt.JWTSigner;
import com.auth0.jwt.JWTVerifier;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class SecurityService implements ISecurityService {
					
		
	private static final String SECRET;
    static {
        SECRET = "my secret";
    }
	
	private static JWTSigner signer = new JWTSigner(SECRET);
	private static JWTVerifier verifier = new JWTVerifier(SECRET);	
	
	@Override
	public String crearToken(Map<String, Object> mapToToken) {

		// OPCIONES del cifrado
		JWTSigner.Options options = new JWTSigner.Options();
		options.setIssuedAt(true);
		options.setExpirySeconds(60);

		String token = signer.sign(mapToToken, options);

		return token;
	}

	@Override
	public String actualizarToken(String token) {

		try {
			Map<String, Object> decoded = verifier.verify(token);
			token = crearToken(decoded);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return token;
	}

	@Override
	public Map<String, Object> getMapFromToken(String token) {
		try {
			return verifier.verify(token);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

/*	
	HashMap<String, Object> claims = new HashMap<String, Object>();

	public void prueba() {
		claims.put("foo", "bar");

		// OPCIONES del cifrado
		JWTSigner.Options options = new JWTSigner.Options();
		options.setIssuedAt(true);
		options.setExpirySeconds(60);

		String token = signer.sign(claims, options);

		try {
			Map<String, Object> decoded = verifier.verify(token);

			System.out.println(decoded.get("foo"));
			long now = System.currentTimeMillis() / 1000l;
			System.out.println(now);
			System.out.println(System.currentTimeMillis());
			System.out.println(token);
			long iat = ((Number) decoded.get("iat")).longValue();
			System.out.println(iat);
		} catch (Exception e) {
			System.out.println("Ocurrio un error en decodificacion");
		}

	}
	*/
}
