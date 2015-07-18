package datas;

import java.io.Serializable;

public class DataImagen implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;
	private String file;

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

}
