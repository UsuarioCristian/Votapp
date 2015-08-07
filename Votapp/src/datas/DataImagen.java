package datas;

import java.io.Serializable;

public class DataImagen implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;
	private String file;
	private String tipo;

	public DataImagen() {
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
