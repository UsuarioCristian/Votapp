package datas;

import java.io.Serializable;

public class DataLista implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int numero;
	private String nombrePartido;
	
	public DataLista() { }

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public String getNombrePartido() {
		return nombrePartido;
	}

	public void setNombrePartido(String nombrePartido) {
		this.nombrePartido = nombrePartido;
	};

}
