package services.implementacion;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;

import com.auth0.jwt.JWTSigner;
import com.auth0.jwt.JWTVerifier;

public class SecurityService {
	
	private static final String SECRET;
    static {
        SECRET = "my secret";
    }
	
	private static JWTSigner signer = new JWTSigner(SECRET);
	private static JWTVerifier verifier = new JWTVerifier(SECRET);
	
	HashMap<String, Object> claims = new HashMap<String, Object>();
	
	public void prueba(){
		claims.put("foo", "bar");
		
		//OPCIONES del cifrado
		JWTSigner.Options options = new JWTSigner.Options();
		options.setIssuedAt(true);
		options.setExpirySeconds(60);
		
		
		String token = signer.sign(claims,options);
		
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
	
}
