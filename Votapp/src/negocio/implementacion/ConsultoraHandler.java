package negocio.implementacion;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import persistencia.interfaces.IConsultoraDAO;
import persistencia.interfaces.IUsuarioDAO;
import negocio.interfaces.IConsultoraHandler;
import datas.DataConsultora;
import datas.DataEmergencia;
import datas.DataUsuario;
import dominio.AdminConsultora;
import dominio.Consultora;
import dominio.Emergencia;
import dominio.Encuestador;
import dominio.Usuario;
import utiles.EnviarMail;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ConsultoraHandler implements IConsultoraHandler {

	@EJB
	IConsultoraDAO consultoraDAO;
	@EJB
	IUsuarioDAO usuarioDAO;
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public boolean altaConsultora(DataConsultora dataConsultora) {
		if(usuarioDAO.findUsuario(dataConsultora.getNombre()) == null){
			Consultora consultora = new Consultora();
			
			AdminConsultora adminConsultora = new AdminConsultora();
			
			consultora.setNombre(dataConsultora.getNombre());
			consultora.setDescripcion(dataConsultora.getDescripcion());
				
			adminConsultora.setEmail(dataConsultora.getEmail());		
			adminConsultora.setUsername(dataConsultora.getNombreAdminConsultora());
			adminConsultora.setPassword(dataConsultora.getPassAdminConsultora());
			adminConsultora.setConsultora(consultora);
			
			consultora.setAdminConsultora(adminConsultora);
					
			return consultoraDAO.crearConsultora(consultora);
		}else{
			System.out.println("El usuario ya existe (ConsultoraHandler.altaConsultora)");
			return false;
		}
		
	}

	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public boolean altaEncuestador(DataUsuario dataUsuario) {
		
		if(usuarioDAO.findUsuario(dataUsuario.getUsername()) == null){
			Encuestador encuestador = new Encuestador();
			Consultora consultora = consultoraDAO.findConsultoraById(dataUsuario.getConsultoraID());
			
			encuestador.setUsername(dataUsuario.getUsername());
			encuestador.setPassword(dataUsuario.getPassword());
			encuestador.setNombreEncuestador(dataUsuario.getNombreEncuestador());
			encuestador.setConsultora(consultora);
			
			consultora.getEncuestadores().add(encuestador);
			
			return consultoraDAO.crearEncuestador(encuestador);
		}else{
			System.out.println("El usuario ya existe (ConsultoraHandler.altaEncuestador)");
			return false;
		}
		
	}


	@Override
	public DataConsultora getDataConsultoraByUsername(String username) {
		
		DataConsultora data = new DataConsultora();
		Consultora consultora = consultoraDAO.getConsultoraByUsername(username);
		
		data.setNombre(consultora.getNombre());
		data.setId(consultora.getId());
		data.setNombreAdminConsultora(username);
		data.setFechaFundacion(consultora.getFechaFundacion());
		consultora = null;
		
		return data;
	}


	@Override
	public List<DataEmergencia> getAllEmergencias(int idConsultora) {
	
		List<Emergencia> emergencias = consultoraDAO.getAllEmergencias(idConsultora);
		List<DataEmergencia> listaRetorno = new ArrayList<DataEmergencia>();
		for (Emergencia emergencia : emergencias) {
			DataEmergencia data = new DataEmergencia();
			data.setId(emergencia.getId());
			data.setIdConsultora(idConsultora);
			
			data.setIdEncuestador(emergencia.getEncuestador().getId());
			Encuestador encuestador = consultoraDAO.findEncuestadorById(emergencia.getEncuestador().getId());
			data.setNombreEncuestador(encuestador.getUsername());
			data.setNombreRealEncuestador(encuestador.getNombreEncuestador());
			
			data.setLatitud(emergencia.getLatitud());
			data.setLongitud(emergencia.getLongitud());
			data.setNotificada(emergencia.isNotificada());
			
			listaRetorno.add(data);
		}
		
		return listaRetorno;
	}


	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public boolean notificarEmergencia(DataEmergencia data) {
		
		Emergencia emergencia = consultoraDAO.findEmergenciaById(data.getId());
		emergencia.setNotificada(true);
		consultoraDAO.actualizarEmergencia(emergencia);
		
		return false;
	}


	@Override
	public boolean thereANewEmergency(int idConsultora) {
		
		List<Emergencia> emergencias = consultoraDAO.getAllEmergencias(idConsultora);
		boolean nueva = false;
		
		for (Emergencia emergencia : emergencias) {
			if(!emergencia.isNotificada())
				nueva = true;
		}
		
		return nueva;
	}


	@Override
	public boolean enviarMailConsultora(DataConsultora dataConsultora) {
		EnviarMail mail = new EnviarMail();
		boolean retorno = false;
		
		try {
			retorno = mail.enviarMensajeConAuth("smtp.gmail.com", 587, "votapp15@gmail.com", dataConsultora.getEmail(), "Votapp2015",
		    		"¡Bienvenido a VotappConsultoras!", 
		    		"¡Bienvenido a VotappConsultoras!"+"\n"+"\n"+
		    		"Aqui tiene sus credenciales para iniciar sesión en la plataforma: "+"\n"+"\n"+
		    		"Nombre: "+ dataConsultora.getNombreAdminConsultora()+ "\n"+
					"Contraseña:" + dataConsultora.getPassAdminConsultora());
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return retorno;
		

	}


	@Override
	public void actuaizarCelular(DataUsuario dataUsuario) {
		Usuario user = usuarioDAO.findUsuario(dataUsuario.getUsername());
		if (user!=null){
			user.setCelular(dataUsuario.getCelular());
		}
		usuarioDAO.actualizarUsuario(user);
	}


	@Override
	public boolean existeUsuario(String username) {
		Usuario user = usuarioDAO.findUsuario(username);
		if(user != null)
			return true;
		return false;
	}
}
