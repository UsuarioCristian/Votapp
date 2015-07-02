package datas;

import java.io.Serializable;

public class DataFuenteDatos implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String url;
	private String tipo;

	public String getUrl() {
		return url;
	}

	public void setURL(String url) {
		this.url = url;
	}
	
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
}
