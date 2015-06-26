package dominio;

import java.io.Serializable;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;

import utiles.TipoCargo;
import datas.DataCandidato;
import datas.DataFuenteDatos;
import datas.DataLista;
import datas.DataPartido;

@Entity
public class EleccionNacional extends Eleccion implements Serializable{

	private static final long serialVersionUID = 1L;
		
	public EleccionNacional() {
		this.partidos = new LinkedHashSet<Partido>();
		this.listas = new LinkedHashSet<Lista>();
		this.candidatos = new LinkedHashSet<Candidato>();
	}
	
	public EleccionNacional(String nombre, String descripcion, Date fecha){
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.fecha = fecha;
		this.partidos = new LinkedHashSet<Partido>();
		this.listas = new LinkedHashSet<Lista>();
		this.candidatos = new LinkedHashSet<Candidato>();
	}

	@Override
	public Set<Partido> getPartidos() {
		return partidos;
	}

	@Override
	public void setPartidos(Set<Partido> partidos) {
		this.partidos = partidos;
	}

	@Override
	public Set<Lista> getListas() {
		return listas;
	}

	@Override
	public void setListas(Set<Lista> listas) {
		this.listas = listas;
	}

	@Override
	public Set<Candidato> getCandidatos() {
		return candidatos;
	}

	@Override
	public void setCandidatos(Set<Candidato> candidatos) {
		this.candidatos = candidatos;
	}

	@Override
	public boolean asignarPartidos(List<DataPartido> dataPartidos) {
		try {
			Partido partido;
			for (DataPartido data : dataPartidos) {
				partido = new Partido();
				partido.setNombre(data.getNombre());
				partido.setDescripcion(data.getDescripcion());
				partido.setFechaFundacion(data.getFechaFundacion());
				partido.setPresidente(data.getPresidente());

				partido.setEleccion(this);
				this.getPartidos().add(partido);

				// Transformar el DataFuenteDatos a FuenteDatos y asignarle al partido
				for (DataFuenteDatos dataFuenteDatos : data.getDataFuenteDatos()) {
					FuenteDatos fuenteDatos = new FuenteDatos();
					fuenteDatos.setUrl(dataFuenteDatos.getUrl());
					partido.getFuenteDatos().add(fuenteDatos);
				}
			}
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	@Override
	public boolean asignarListas(List<DataLista> dataListas) {
		try {
			Lista lista;
			for (DataLista data : dataListas) {
				lista = new Lista();
				lista.setNumero(data.getNumero());

				// buscar partido (a esta altura el partido fue agregado en el paso1... Ver EleccionHandler)
				boolean encontre = false;
				Partido partido = null;
				Iterator<Partido> iter = this.getPartidos().iterator();
				while (!encontre && iter.hasNext()) {
					partido = iter.next();
					if (partido.getNombre().equals(data.getNombrePartido()))
						encontre = true;
				}
				lista.setPartido(partido);
				partido.getListas().add(lista);

				lista.setEleccion(this);
				this.getListas().add(lista);
			}

			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean asignarCandidatos(List<DataCandidato> dataCandidatos) {

		try {

			Candidato candidato;
			for (DataCandidato data : dataCandidatos) {
				candidato = new Candidato();
				candidato.setNombre(data.getNombre());
				candidato.setCargo(TipoCargo.PRESIDENTE); // Hardcoded, debe venir desde la vista el campo del TipoCargo
				candidato.setEdad(data.getEdad());

				// Transformar el DataFuenteDatos a FuenteDatos y asignarle al candidato
				for (DataFuenteDatos dataFuenteDatos : data.getDataFuenteDatos()) {
					FuenteDatos fuenteDatos = new FuenteDatos();
					fuenteDatos.setUrl(dataFuenteDatos.getUrl());
					candidato.getFuenteDatos().add(fuenteDatos);
				}

				// Buscar listas (a esta altura la lista fue agregado en el paso2... Ver EleccionHandler)
				// como es una eleccion Nacional entonces el candidato esta en todas las listas del partido.
				// es complicado xq es una relacion @ManyToMany (peor caso es n^3)..
				// deberia venir de la vista el partido al cual pertenece el candidato (n^2)

				for (DataLista dataLista : data.getDataListas()) {
					boolean encontre = false;
					Lista lista = null;
					Iterator<Lista> iter = this.getListas().iterator();
					while (!encontre && iter.hasNext()) {
						lista = iter.next();
						if (lista.getNumero() == dataLista.getNumero())
							encontre = true;
					}
					lista.getCandidatos().add(candidato);
					candidato.getListas().add(lista);
				}

				this.getCandidatos().add(candidato);
				candidato.setEleccion(this);

			}

			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
