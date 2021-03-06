package datas;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class DataResultado implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Map<Integer, Integer> mapCandidatos;
	private Map<Integer, Integer> mapListas;
	private Map<Integer, Integer> mapPartidos;
	
	private Map<Integer, Integer> mapNivelEstudioPrimaria;
	private Map<Integer, Integer> mapNivelEstudioSecundaria;
	private Map<Integer, Integer> mapNivelEstudioTerciario;
	private Map<Integer, Integer> mapNivelEstudioNoSabe;
	
	private Map<Integer, Integer> mapEdad18a23;
	private Map<Integer, Integer> mapEdad24a30;
	private Map<Integer, Integer> mapEdad31a50;
	private Map<Integer, Integer> mapEdad51omas;
	
	private Map<Integer, Integer> mapGeneroFem;
	private Map<Integer, Integer> mapGeneroMas;
	private Map<Integer, Integer> mapGeneroOtro;
	
	private Map<Integer, Integer> mapIngresos1;//  x < 10.000
	private Map<Integer, Integer> mapIngresos2;// 10000 < x < 21.365
	private Map<Integer, Integer> mapIngresos3;// 21.366 < x < 40.000
	private Map<Integer, Integer> mapIngresos4;// 40.001 < x
	
	private Map<Integer, Integer> mapTrabaja;
	private Map<Integer, Integer> mapNoTrabaja;
	
	public DataResultado() {
		this.mapCandidatos = new HashMap<Integer, Integer>();
		this.mapListas = new HashMap<Integer, Integer>();
		this.setMapPartidos(new HashMap<Integer, Integer>());
		this.mapNivelEstudioPrimaria = new HashMap<Integer, Integer>();
		this.mapNivelEstudioSecundaria = new HashMap<Integer, Integer>();
		this.mapNivelEstudioTerciario = new HashMap<Integer, Integer>();
		this.mapNivelEstudioNoSabe = new HashMap<Integer, Integer>();
		this.mapEdad18a23  = new HashMap<Integer, Integer>();
		this.mapEdad24a30  = new HashMap<Integer, Integer>();
		this.mapEdad31a50  = new HashMap<Integer, Integer>();
		this.mapEdad51omas  = new HashMap<Integer, Integer>();
		this.mapGeneroFem = new HashMap<Integer, Integer>();
		this.mapGeneroMas = new HashMap<Integer, Integer>();
		this.mapGeneroOtro = new HashMap<Integer, Integer>();
		this.mapIngresos1 = new HashMap<Integer, Integer>();
		this.mapIngresos2 = new HashMap<Integer, Integer>();
		this.mapIngresos3 = new HashMap<Integer, Integer>();
		this.mapIngresos4 = new HashMap<Integer, Integer>();
		this.mapTrabaja = new HashMap<Integer, Integer>();
		this.mapNoTrabaja = new HashMap<Integer, Integer>();
	}

	public Map<Integer, Integer> getMapCandidatos() {
		return mapCandidatos;
	}

	public void setMapCandidatos(Map<Integer, Integer> mapCandidatos) {
		this.mapCandidatos = mapCandidatos;
	}

	public Map<Integer, Integer> getMapListas() {
		return mapListas;
	}

	public void setMapListas(Map<Integer, Integer> mapListas) {
		this.mapListas = mapListas;
	}

	public Map<Integer, Integer> getMapPartidos() {
		return mapPartidos;
	}

	public void setMapPartidos(Map<Integer, Integer> mapPartidos) {
		this.mapPartidos = mapPartidos;
	}

	public Map<Integer, Integer> getMapNivelEstudioPrimaria() {
		return mapNivelEstudioPrimaria;
	}

	public void setMapNivelEstudioPrimaria(Map<Integer, Integer> mapNivelEstudio) {
		this.mapNivelEstudioPrimaria = mapNivelEstudio;
	}

	public Map<Integer, Integer> getMapNivelEstudioSecundaria() {
		return mapNivelEstudioSecundaria;
	}

	public void setMapNivelEstudioSecundaria(Map<Integer, Integer> mapNivelEstudioSecundaria) {
		this.mapNivelEstudioSecundaria = mapNivelEstudioSecundaria;
	}

	public Map<Integer, Integer> getMapNivelEstudioTerciario() {
		return mapNivelEstudioTerciario;
	}

	public void setMapNivelEstudioTerciario(Map<Integer, Integer> mapNivelEstudioTerciario) {
		this.mapNivelEstudioTerciario = mapNivelEstudioTerciario;
	}

	public Map<Integer, Integer> getMapNivelEstudioNoSabe() {
		return mapNivelEstudioNoSabe;
	}

	public void setMapNivelEstudioNoSabe(Map<Integer, Integer> mapNivelEstudioNoSabe) {
		this.mapNivelEstudioNoSabe = mapNivelEstudioNoSabe;
	}

	public Map<Integer, Integer> getMapEdad18a23() {
		return mapEdad18a23;
	}

	public void setMapEdad18a23(Map<Integer, Integer> mapEdad18a23) {
		this.mapEdad18a23 = mapEdad18a23;
	}

	public Map<Integer, Integer> getMapEdad24a30() {
		return mapEdad24a30;
	}

	public void setMapEdad24a30(Map<Integer, Integer> mapEdad24a30) {
		this.mapEdad24a30 = mapEdad24a30;
	}

	public Map<Integer, Integer> getMapEdad31a50() {
		return mapEdad31a50;
	}

	public void setMapEdad31a50(Map<Integer, Integer> mapEdad31a50) {
		this.mapEdad31a50 = mapEdad31a50;
	}

	public Map<Integer, Integer> getMapEdad51omas() {
		return mapEdad51omas;
	}

	public void setMapEdad51omas(Map<Integer, Integer> mapEdad51omas) {
		this.mapEdad51omas = mapEdad51omas;
	}

	public Map<Integer, Integer> getMapGeneroFem() {
		return mapGeneroFem;
	}

	public void setMapGeneroFem(Map<Integer, Integer> mapGeneroFem) {
		this.mapGeneroFem = mapGeneroFem;
	}

	public Map<Integer, Integer> getMapGeneroMas() {
		return mapGeneroMas;
	}

	public void setMapGeneroMas(Map<Integer, Integer> mapGeneroMas) {
		this.mapGeneroMas = mapGeneroMas;
	}

	public Map<Integer, Integer> getMapGeneroOtro() {
		return mapGeneroOtro;
	}

	public void setMapGeneroOtro(Map<Integer, Integer> mapGeneroOtro) {
		this.mapGeneroOtro = mapGeneroOtro;
	}

	public Map<Integer, Integer> getMapIngresos1() {
		return mapIngresos1;
	}

	public void setMapIngresos1(Map<Integer, Integer> mapIngresos1) {
		this.mapIngresos1 = mapIngresos1;
	}

	public Map<Integer, Integer> getMapIngresos2() {
		return mapIngresos2;
	}

	public void setMapIngresos2(Map<Integer, Integer> mapIngresos2) {
		this.mapIngresos2 = mapIngresos2;
	}

	public Map<Integer, Integer> getMapIngresos3() {
		return mapIngresos3;
	}

	public void setMapIngresos3(Map<Integer, Integer> mapIngresos3) {
		this.mapIngresos3 = mapIngresos3;
	}

	public Map<Integer, Integer> getMapIngresos4() {
		return mapIngresos4;
	}

	public void setMapIngresos4(Map<Integer, Integer> mapIngresos4) {
		this.mapIngresos4 = mapIngresos4;
	}

	public Map<Integer, Integer> getMapTrabaja() {
		return mapTrabaja;
	}

	public void setMapTrabaja(Map<Integer, Integer> mapTrabaja) {
		this.mapTrabaja = mapTrabaja;
	}

	public Map<Integer, Integer> getMapNoTrabaja() {
		return mapNoTrabaja;
	}

	public void setMapNoTrabaja(Map<Integer, Integer> mapNoTrabaja) {
		this.mapNoTrabaja = mapNoTrabaja;
	}

}
