package datas;

import java.io.Serializable;

import utiles.TipoFuente;

public class DataFuenteDatos implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String url;
	private TipoFuente tipo;

	public String getUrl() {
		return url;
	}

	public void setURL(String url) {
		this.url = url;
	}
	
	public TipoFuente getTipo() {
		return tipo;
	}

	public void setTipo(TipoFuente tipo) {
		this.tipo = tipo;
	}
	
}
