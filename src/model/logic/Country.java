package model.logic;

import java.util.Comparator;

import model.data_structures.ArregloDinamico;
import model.data_structures.ILista;
import model.data_structures.ListaEncadenada;
import model.utils.Ordenamiento;

public class Country implements Comparable<Country> {

	public String CountryName;
	public String CapitalName;
	public String CapitalLatitude;
	public String CapitalLongitude;
	public String CountryCode;
	public String ContinentName;
	public String Population;
	public String InternetUsers;
	public ILista<LandingPoint> landingPoints;

	public Country(String CountryName, String CapitalName, String CapitalLatitude, String CapitalLongitude,
			String CountryCode, String ContinentName, String Population, String InternetUsers) {
		this.CountryName = CountryName;
		this.CapitalName = CapitalName;
		this.CapitalLatitude = CapitalLatitude;
		this.CapitalLongitude = CapitalLongitude;
		this.CountryCode = CountryCode;
		this.ContinentName = ContinentName;
		this.Population = Population;
		this.InternetUsers = InternetUsers;
		landingPoints = new ArregloDinamico<LandingPoint>(5);
	}

	public void addLaindingPoint(LandingPoint lp) {
		landingPoints.addLast(lp);
	}
	@Override
	public int compareTo(Country arg0) {
		return CountryCode.compareTo(arg0.CountryCode);
	}
	
	@Override
	public String toString() {
		return CountryName + CountryCode;
	}

}
