package negocio.implementacion;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import negocio.interfaces.IEleccionHandler;
import persistencia.implementacion.EleccionDAO;
import persistencia.interfaces.ICandidatoDAO;
import persistencia.interfaces.IEleccionDAO;
import persistencia.interfaces.IFuenteDatosDAO;
import persistencia.interfaces.IPartidoDAO;
import utiles.TipoEleccion;
import datas.DataCandidato;
import datas.DataDepartamento;
import datas.DataEleccion;
import datas.DataFuenteDatos;
import datas.DataImagen;
import datas.DataLista;
import datas.DataPartido;
import dominio.Candidato;
import dominio.Departamento;
import dominio.Eleccion;
import dominio.EleccionDepartamental;
import dominio.EleccionNacional;
import dominio.EleccionOtro;
import dominio.FuenteDatos;
import dominio.Imagen;
import dominio.Lista;
import dominio.Partido;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class EleccionHandler implements IEleccionHandler {
	
	@EJB
	IEleccionDAO eleccionDAO;
	@EJB
	IPartidoDAO partidoDAO;
	@EJB
	ICandidatoDAO candidatoDAO;
	@EJB
	IFuenteDatosDAO fuenteDatosDAO;

	@Override
	public boolean crearEleccion(DataEleccion data) {

		boolean exito = false;
		Eleccion eleccion = null;
		Imagen imagen = null;
		boolean paso1, paso2, paso3;
		String css = data.getCss();
		if (css == null) {
			css = "";
		}

		switch (data.getTipoEleccion()) {
			case Nacional:
				eleccion = new EleccionNacional(data.getNombre(), data.getDescripcion(), data.getFecha());
				eleccion.setCss(simplificarCss(css));
				
				imagen = new Imagen();
				imagen.setName(data.getLogo().getName());
				imagen.setFile(data.getLogo().getFile());
				imagen.setTipo(data.getLogo().getTipo());
				eleccion.setImagen(imagen);
				
				paso1 = eleccion.asignarPartidos(data.getDataPartidos());
				paso2 = eleccion.asignarListas(data.getDataListas());
				paso3 = eleccion.asignarCandidatos(data.getDataCandidatos());
				eleccion.asignarNoticias(data.getDataNoticias());
				
				if(paso1 && paso2 && paso3)
					exito = eleccionDAO.crearEleccion(eleccion);
				else
					System.out.println("ERROR...paso1= "+paso1+" paso2= "+paso2+ " paso3= "+paso3);
			break;
			
			case Departamental:
				eleccion = new EleccionDepartamental(data.getNombre(), data.getDescripcion(), data.getFecha());
				eleccion.setCss(simplificarCss(css));
				
				imagen = new Imagen();
				imagen.setName(data.getLogo().getName());
				imagen.setFile(data.getLogo().getFile());
				imagen.setTipo(data.getLogo().getTipo());
				eleccion.setImagen(imagen);
				
				paso1 = eleccion.asignarPartidos(data.getDataPartidos());
				paso2 = eleccion.asignarListas(data.getDataListas());
				paso3 = eleccion.asignarCandidatos(data.getDataCandidatos());
				eleccion.asignarNoticias(data.getDataNoticias());
				
				if(paso1 && paso2 && paso3)
					exito = eleccionDAO.crearEleccion(eleccion);
				else
					System.out.println("ERROR...paso1= "+paso1+" paso2= "+paso2+ " paso3= "+paso3);
			break;
			
			case Otra:
				eleccion = new EleccionOtro(data.getNombre(), data.getDescripcion(), data.getFecha(), false);
				eleccion.setCss(simplificarCss(css));
				
				imagen = new Imagen();
				imagen.setName(data.getLogo().getName());
				imagen.setFile(data.getLogo().getFile());
				imagen.setTipo(data.getLogo().getTipo());
				eleccion.setImagen(imagen);
				
				paso2 = eleccion.asignarListas(data.getDataListas());
				paso3 = eleccion.asignarCandidatos(data.getDataCandidatos());
				eleccion.asignarNoticias(data.getDataNoticias());
				
				if(paso2 && paso3)
					exito = eleccionDAO.crearEleccion(eleccion);
				else
					System.out.println("ERROR...paso2= "+paso2+ " paso3= "+paso3);
			break;
			case Simple:
				eleccion = new EleccionOtro(data.getNombre(), data.getDescripcion(), data.getFecha(), true);
				eleccion.setCss(simplificarCss(css));
				
				imagen = new Imagen();
				imagen.setName(data.getLogo().getName());
				imagen.setFile(data.getLogo().getFile());
				imagen.setTipo(data.getLogo().getTipo());
				eleccion.setImagen(imagen);
				
				paso3 = eleccion.asignarCandidatos(data.getDataCandidatos());
				eleccion.asignarNoticias(data.getDataNoticias());
				
				if(paso3)
					exito = eleccionDAO.crearEleccion(eleccion);
				else
					System.out.println("ERROR... paso3= "+paso3);
			break;

			default:
				break;
		}

		if (exito)
			System.out.println("Se creo la Eleccion correctamente");
		else
			System.out.println("ocurrio un error en la creacion de la Eleccion");
		
		return exito;

	}

	private String simplificarCss(String css) {
		
		String[] parts = css.split("/n");
		String retorno = "";
		int tam = parts.length;
		for (int i = 0; i < tam; i++) {
			retorno += parts[i];
		}
		return retorno;
	}

	@Override
	public Eleccion findEleccionById(int id) {
		Eleccion eleccion = eleccionDAO.findEleccionById(id);
		if (eleccion == null){
			return null;
		}
		return eleccion;
	}

	@Override
	public List<DataEleccion> getEleccionesActuales() {
		
		List<Eleccion> elecciones = eleccionDAO.getEleccionesActuales();
		List<DataEleccion> dataElecciones = new ArrayList<DataEleccion>();
		
		for (Eleccion eleccion : elecciones) {
			DataEleccion dataEleccion = new DataEleccion();
			dataEleccion.setNombre(eleccion.getNombre());
			dataEleccion.setFecha(eleccion.getFecha());
			dataEleccion.setId(eleccion.getId());
			switch (eleccion.getClass().getName()) {
				case "dominio.EleccionNacional":
					dataEleccion.setTipoEleccion(TipoEleccion.Nacional);
					break;
				case "dominio.EleccionDepartamental":
					dataEleccion.setTipoEleccion(TipoEleccion.Departamental);
					break;
				case "dominio.EleccionOtro":
					EleccionOtro eleccionOtro = (EleccionOtro) eleccion;
					if(eleccionOtro.isSimple())
						dataEleccion.setTipoEleccion(TipoEleccion.Simple);
					else
						dataEleccion.setTipoEleccion(TipoEleccion.Otra);
					break;
				default:
					break;
			}
			
			dataElecciones.add(dataEleccion);
		}
		
		return dataElecciones;
	}

	@Override
	public List<DataEleccion> getElecciones() {
		List<Eleccion> elecciones = eleccionDAO.getElecciones();
		List<DataEleccion> dataElecciones = new ArrayList<DataEleccion>();
		
		for (Eleccion eleccion : elecciones) {
			DataEleccion dataEleccion = new DataEleccion();
			dataEleccion.setNombre(eleccion.getNombre());
			dataEleccion.setDescripcion(eleccion.getDescripcion());
			dataEleccion.setFecha(eleccion.getFecha());
			dataEleccion.setCss(eleccion.getCss());
			dataEleccion.setId(eleccion.getId());
			List<DataPartido> partidos = getPartidosFromEleccion(eleccion);
			List<DataCandidato> candidatos = getCandidatosFromEleccion(eleccion);
			List<DataFuenteDatos> noticias = getNoticiasFromEleccion(eleccion);
			List<DataDepartamento> deptos = getDeptosFromEleccion(eleccion);
			switch (eleccion.getClass().getName()) {
				case "dominio.EleccionNacional":
					dataEleccion.setTipoEleccion(TipoEleccion.Nacional);					
					
					dataEleccion.setDataPartidos(partidos);
					dataEleccion.setDataCandidatos(candidatos);	
					dataEleccion.setDeptos(deptos);/*Esto aqui no se si tiene sentido... tal vez a futuro*/
					dataEleccion.setDataNoticias(noticias);
					break;
				case "dominio.EleccionDepartamental":
					dataEleccion.setTipoEleccion(TipoEleccion.Departamental);
					
					dataEleccion.setDataPartidos(partidos);
					dataEleccion.setDataCandidatos(candidatos);
					dataEleccion.setDeptos(deptos);
					dataEleccion.setDataNoticias(noticias);
					break;
				case "dominio.EleccionOtro":
					EleccionOtro eleccionOtro = (EleccionOtro) eleccion;
					if(eleccionOtro.isSimple()){
						dataEleccion.setTipoEleccion(TipoEleccion.Simple);
					}else{
						dataEleccion.setTipoEleccion(TipoEleccion.Otra);
					}
					dataEleccion.setDataPartidos(partidos);
					dataEleccion.setDataCandidatos(candidatos);
					dataEleccion.setDataNoticias(noticias);
					break;
				default:
					break;
			}
			DataImagen logo = new DataImagen();
			logo.setFile(eleccion.getImagen().getFile());
			logo.setName(eleccion.getImagen().getName());
			logo.setTipo(eleccion.getImagen().getTipo());
			dataEleccion.setLogo(logo);
			dataElecciones.add(dataEleccion);
		}
		
		return dataElecciones;
	}
	
	@Override
	public boolean borrarEleccion(int id) {
		return eleccionDAO.borrarEleccion(id);
		
	}
	
	/********************************************************/
	/****************FUNCIONES AUXILIARES********************/
	/********************************************************/

	private List<DataDepartamento> getDeptosFromEleccion(Eleccion eleccion) {
		List<DataDepartamento> listaRetorno = new ArrayList<DataDepartamento>();
		
		for (Departamento depto : eleccion.getDepartamentos()){/*Se cargan fetch eager*/
			DataDepartamento dataDepartamento = new DataDepartamento();
			dataDepartamento.setId(depto.getId());
			dataDepartamento.setNombre(depto.getNombre());
			dataDepartamento.setNumHabilitadosVotar(depto.getNumHabilitadosVotar());
			dataDepartamento.setNumHabitantes(depto.getNumHabitantes());
			Set<FuenteDatos> fuentes = depto.getFuenteDatos();			
			for (FuenteDatos fuenteDatos : fuentes) {
				dataDepartamento.getListaFuenteDatos().add(convertToData(fuenteDatos));
			}
			
			for (Partido partido : depto.getPartidos()) {
				dataDepartamento.getColeccionIdPartidos().add(partido.getId());
			}
			
			listaRetorno.add(dataDepartamento);
		}
		
		
		return listaRetorno;
	}

	private List<DataCandidato> getCandidatosFromEleccion(Eleccion eleccion) {
		List<DataCandidato> listaRetorno = new ArrayList<DataCandidato>();
		
		for (Candidato candidatoOnlyId : eleccion.getCandidatos()) {
			Candidato candidato = candidatoDAO.findCandidatoById(candidatoOnlyId.getId());
			DataCandidato data = convertToData(candidato);
			
			DataImagen logo = new DataImagen();
			logo.setFile(candidato.getImagen().getFile());
			logo.setName(candidato.getImagen().getName());
			logo.setTipo(candidato.getImagen().getTipo());
			data.setImagen(logo);
			
			listaRetorno.add(data);
		}
		
		return listaRetorno;
	}
	
	private List<DataFuenteDatos> getNoticiasFromEleccion(Eleccion eleccion) {
		List<DataFuenteDatos> listaRetorno = new ArrayList<DataFuenteDatos>();
		
		for (FuenteDatos noticiaOnlyId : eleccion.getNoticias()) {
			FuenteDatos noticia = fuenteDatosDAO.findFuenteDatosById(noticiaOnlyId.getId());
			DataFuenteDatos data = convertToData(noticia);
			listaRetorno.add(data);
		}
		
		return listaRetorno;
	}

	private List<DataPartido> getPartidosFromEleccion(Eleccion eleccion) {
		
		List<DataPartido> listaRetorno = new ArrayList<DataPartido>();
		
		for (Partido partidoOnlyID : eleccion.getPartidos()) {
			Partido partido = partidoDAO.findPartidoById(partidoOnlyID.getId());
			DataPartido data = convertToData(partido);
			
			DataImagen logo = new DataImagen();
			logo.setFile(partido.getImagen().getFile());
			logo.setName(partido.getImagen().getName());
			logo.setTipo(partido.getImagen().getTipo());
			data.setImagen(logo);
			
			listaRetorno.add(data);	
		}
		
		
		
		return listaRetorno;
	}
	
	private DataCandidato convertToData(Candidato candidato) {
		DataCandidato data = new DataCandidato();
		data.setId(candidato.getId());
		data.setCargo(candidato.getCargo());
		data.setEdad(candidato.getEdad());
		data.setNombre(candidato.getNombre());
		data.setBiografia(candidato.getBiografia());
		
		
		Set<FuenteDatos> fuenteDatosOnlyID = candidato.getFuenteDatos();
		for (FuenteDatos fuenteDatos : fuenteDatosOnlyID) {
			FuenteDatos fuente = eleccionDAO.findFuenteDatosById(fuenteDatos.getId());
			DataFuenteDatos dataFuente = convertToData(fuente);
			data.getDataFuenteDatos().add(dataFuente);
		}
		
		Set<Lista> listasOnlyId = candidato.getListas();
		for (Lista lista : listasOnlyId) {
			Lista lista2 = eleccionDAO.findListaById(lista.getId());
			DataLista dataLista = convertToData(lista2);			
			data.getDataListas().add(dataLista);
		}
		
		/*Esto se podria modificar.. x ejemplo que candidato guarde a q partido pertenece.. y no buscar por las listas*/
		//data.setNombrePartido(candidato.g);		
		if (candidato.getListas().size() > 0){
			List<Lista> arreglo = new ArrayList<Lista>(candidato.getListas());
			if(arreglo.get(0).getPartido() != null){
				Partido partido = partidoDAO.findPartidoById(arreglo.get(0).getPartido().getId());
				data.setIdPartido(partido.getId());
				data.setNombrePartido(partido.getNombre());
			}
				
			
			Lista listaPrimera = eleccionDAO.findListaById(arreglo.get(0).getId());
			if(listaPrimera.getDepartamento() != null){ /*Solo entra aqui si es una eleccion departamental*/
				data.setIdDepto(listaPrimera.getDepartamento().getId());
				data.setNombreDepto(listaPrimera.getDepartamento().getNombre());
			}
			
		}
		
		return data;
		
	}

	private DataPartido convertToData(Partido partido) {
		DataPartido data = new DataPartido();
		data.setId(partido.getId());
		data.setNombre(partido.getNombre());
		data.setDescripcion(partido.getDescripcion());
		data.setFechaFundacion(partido.getFechaFundacion());
		data.setPresidente(partido.getPresidente());
		
		Set<FuenteDatos> fuentesDatosOnlyID = partido.getFuenteDatos();
		for (FuenteDatos fuenteDatos : fuentesDatosOnlyID) {
			FuenteDatos fuente = eleccionDAO.findFuenteDatosById(fuenteDatos.getId());
			DataFuenteDatos dataFuente = convertToData(fuente);
			data.getDataFuenteDatos().add(dataFuente);
		}
		
		Set<Lista> listasOnlyId = partido.getListas();
		for (Lista lista : listasOnlyId) {
			Lista lista2 = eleccionDAO.findListaById(lista.getId());
			DataLista dataLista = convertToData(lista2);
			dataLista.setNombrePartido(partido.getNombre());
			data.getListas().add(dataLista);
		}
		
		Set<Departamento> deptos = partido.getDepartamentos(); //recordar FetchType.EAGER
		for (Departamento departamento : deptos) {
			DataDepartamento dataDepartamento = new DataDepartamento();
			dataDepartamento.setId(departamento.getId());
			dataDepartamento.setNombre(departamento.getNombre());
//			dataDepartamento.setNumHabilitadosVotar(departamento.getNumHabilitadosVotar());
//			dataDepartamento.setNumHabitantes(departamento.getNumHabitantes());
			data.getDataDeptos().add(dataDepartamento);
		}
				
		return data;
	}

	private DataLista convertToData(Lista lista) {
		DataLista data = new DataLista();
		
		data.setId(lista.getId());
		//data.setNombrePartido(lista.ge);
		data.setNumero(lista.getNumero());
		if(lista.getPartido() != null)
			data.setIdPartido(lista.getPartido().getId());
		
		/*Solo si es eleccion departamental*/
		if (lista.getDepartamento() != null){
			DataDepartamento dataDepartamento = new DataDepartamento();
			dataDepartamento.setNombre(lista.getDepartamento().getNombre());
//			dataDepartamento.setNumHabilitadosVotar(lista.getDepartamento().getNumHabilitadosVotar());
//			dataDepartamento.setNumHabitantes(lista.getDepartamento().getNumHabitantes());
			dataDepartamento.setId(lista.getDepartamento().getId());
			data.setDataDepartamento(dataDepartamento);
		}
		
		
		return data;
	}

	private DataFuenteDatos convertToData(FuenteDatos fuente) {
		DataFuenteDatos data = new DataFuenteDatos();
		data.setTipo(fuente.getTipo());
		data.setUrl(fuente.getUrl());
		data.setId(fuente.getId());		
		
		return data;
	}	
	

}
