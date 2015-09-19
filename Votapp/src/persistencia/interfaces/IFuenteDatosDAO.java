package persistencia.interfaces;

import java.util.List;

import javax.ejb.Local;

import dominio.FuenteDatos;

@Local
public interface IFuenteDatosDAO {
	
		public  List<FuenteDatos> getFuentesByIdEleccion(int idEleccion);
		
		public FuenteDatos findFuenteDatosById(int id);
}
