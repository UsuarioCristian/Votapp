package datas;

import java.io.Serializable;

public class DataUsuario implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
	private String username;
	private String password;
	private String email;
	private String celular;
	private String nombreEncuestador;
	
	
	public String getNombreEncuestador() {
		return nombreEncuestador;
	}

	public void setNombreEncuestador(String nombreEncuestador) {
		this.nombreEncuestador = nombreEncuestador;
	}

	//datos para el usuario Encuestador
	private int consultoraID;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public int getConsultoraID() {
		return consultoraID;
	}

	public void setConsultoraID(int consultoraID) {
		this.consultoraID = consultoraID;
	}

	@Override
	public String toString() {
		return "Usuario[" + this.id + "]";
	}
	
	
	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}

}
