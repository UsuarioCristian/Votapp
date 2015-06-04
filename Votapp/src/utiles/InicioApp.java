package utiles;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.ejb.EJB;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.commons.codec.binary.Hex;

import persistencia.interfaces.IUsuarioDAO;
import dominio.Usuario;

@WebListener
@TransactionManagement(TransactionManagementType.CONTAINER)
public class InicioApp implements ServletContextListener {
	
	@EJB
	IUsuarioDAO usuarioDAO;

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		
		System.out.println("Cerrando app...");

	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {

		System.out.println("Iniciando app... creando usuario admin");
		String passHex = null;

		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			String pass = "Admin";
			md.update(pass.getBytes("UTF-8"));
			byte[] digest = md.digest();

			passHex = Hex.encodeHexString(digest);

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Usuario user = new Usuario();
		user.setUsername("Admin");
		user.setPassword(passHex);
		usuarioDAO.crearUsuario(user);

	}

}
