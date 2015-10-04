package utiles;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EnviarMail {

	public boolean enviarMail(String mail, String nombre, String pass) {
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		Session session = Session.getDefaultInstance(props,
			new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("votapp15@gmail.com","Votapp2015");
				}
			});

		try {

			//Message message = new MimeMessage(session);
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress("Votapp"));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(mail));
			message.setSubject("¡Bienvenido a VotappConsutltoras!");
			message.setText(
					"¡Bienvenido a VotappConsultoras!,"+
					"\n\n Aqui tiene sus credenciales para iniciar sesión en la plataforma:"+
					"\n\n Nombre: "+ nombre + 
					"\n\n" + "Contraseña:" + pass, "utf-8", "html" );
			
			Transport.send(message);

			return true;

		} catch (MessagingException e) {
			e.printStackTrace(); 
			return false;
			
		}
	
	}

}
