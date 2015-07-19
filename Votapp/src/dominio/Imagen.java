package dominio;

import java.io.Serializable;

import javax.persistence.*;

@Entity
public class Imagen implements Serializable {
	   
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private static final long serialVersionUID = 1L;
	
	private String name;
	private String tipo;

	@Column(length=100000)
	private String file;

	public Imagen() {
		super();
	}   
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

}
