package datas;

import java.io.Serializable;
import java.util.Date;

public class DataConsultora implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private int id;
	private String nombre;
	private Date fechaFundacion;
	private String descripcion;
	
	private String nombreAdminConsultora;
	private String passAdminConsultora;
	private String email;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Date getFechaFundacion() {
		return fechaFundacion;
	}
	public void setFechaFundacion(Date fechaFundacion) {
		this.fechaFundacion = fechaFundacion;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getNombreAdminConsultora() {
		return nombreAdminConsultora;
	}
	public void setNombreAdminConsultora(String nombreAdminConsultora) {
		this.nombreAdminConsultora = nombreAdminConsultora;
	}

	public String getPassAdminConsultora() {
		return passAdminConsultora;
	}

	public void setPassAdminConsultora(String passAdminConsultora) {
		this.passAdminConsultora = passAdminConsultora;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}

}