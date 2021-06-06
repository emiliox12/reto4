package model.logic;

import java.io.FileReader;
import java.io.Reader;
import java.util.Comparator;
import java.util.Random;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import model.data_structures.ArregloDinamico;
import model.data_structures.Edge;
import model.data_structures.ILista;
import model.data_structures.ITablaSimbolos;
import model.data_structures.ListaEncadenada;
import model.data_structures.NoDirectedGraph;
import model.data_structures.NodoTS;
import model.data_structures.TablaHashLinearProbing;
import model.data_structures.TablaHashSeparateChaining;
import model.data_structures.Vertex;
import model.utils.Ordenamiento;

/**
 * Definicion del modelo del mundo
 *
 */
public class Modelo {
	/**
	 * Atributos del modelo del mundo
	 */
	private ITablaSimbolos<String, LandingPoint> landingPoints;
	private ITablaSimbolos<String, Country> countries;
	private ITablaSimbolos<String, Connection> connections;
	private NoDirectedGraph<String, LandingPoint> graph;

	/**
	 * Constructor del modelo del mundo con capacidad predefinida
	 */
	public Modelo() {
		landingPoints = new TablaHashSeparateChaining<>(1000);
		countries = new TablaHashLinearProbing<>(1000);
		connections = new TablaHashSeparateChaining<>(1000);
		graph = new NoDirectedGraph<String, LandingPoint>(500);
		// datos = new ListaEncadenada<YoutubeVideo>();
		cargar();
	}

	/**
	 * Servicio de consulta de numero de elementos presentes en el modelo
	 * 
	 * @return numero de elementos presentes en el modelo
	 */
	public int darTamano() {
		return 0;
	}

	public void cargar() {
		System.out.println("Start upload");
		int lpsCount = 0;
		int countriesCount = 0;
		int cablesCount = 0;
		float maxWeight = 0f;
		Connection maxCon = null;
		float minWeight = 100000000f;
		Connection minCon = null;
		Reader in;
		long start = System.currentTimeMillis();
		try {
			in = new FileReader("./data/landing_points.csv");
			Iterable<CSVRecord> landingPointsCsv = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
			for (CSVRecord record : landingPointsCsv) {
				String landing_point_id = record.get("landing_point_id");
				String id = record.get("id");
				String name = record.get("name");
				String latitude = record.get("latitude");
				String longitude = record.get("longitude");
				LandingPoint lp = new LandingPoint(landing_point_id, id, name, latitude, longitude);
				landingPoints.put(id, lp);
				graph.incertVertex(landing_point_id.trim(), lp);
				lpsCount++;
			}
			System.out.println(graph.numVertices());
			// System.out.println(graph.vertices());
			in = new FileReader("./data/countries.csv");
			Iterable<CSVRecord> countriesCsv = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
			for (CSVRecord record : countriesCsv) {
				String CountryName = record.get("CountryName");
				String CapitalName = record.get("CapitalName");
				String CapitalLatitude = record.get("CapitalLatitude");
				String CapitalLongitude = record.get("CapitalLongitude");
				String CountryCode = record.get("CountryCode");
				String ContinentName = record.get("ContinentName");
				String Population = record.get("Population");
				String InternetUsers = record.get("InternetUsers");
				Country country = new Country(CountryName, CapitalName, CapitalLatitude, CapitalLongitude, CountryCode,
						ContinentName, Population, InternetUsers);
				countries.put(CountryCode, country);
				countriesCount++;
			}
			in = new FileReader("./data/connections.csv");
			Iterable<CSVRecord> connectionsCsv = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
			for (CSVRecord record : connectionsCsv) {
				String origin = record.get("origin");
				String destination = record.get("destination");
				String cable_name = record.get("cable_name");
				String cable_id = record.get("cable_id");
				String cable_length = record.get("cable_length").split("\\s")[0].replaceAll(",", "");
				if (cable_length.compareTo("n.a.") == 0) {
					cable_length = "0";
				}
				String cable_rfs = record.get("cable_rfs");
				String owners = record.get("owners");
				String capacityTBPS = record.get("capacityTBPS");
				Connection cable = new Connection(origin, destination, cable_name, cable_id, cable_length, cable_rfs,
						owners, capacityTBPS);
				connections.put(cable_id, cable);
				if (maxWeight < Float.parseFloat(cable_length)) {
					maxCon = cable;
				} else if (minWeight > Float.parseFloat(cable_length)) {
					minCon = cable;
				}
				graph.addEdge(origin, destination, Float.parseFloat(cable_length));
				cablesCount++;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long end = System.currentTimeMillis();
		System.out.println("Time (ms): " + (end - start));
		System.out.println(
				"Landing points #: " + lpsCount + " Countries #: " + countriesCount + " Cables #: " + cablesCount);
		System.out.println("Min: " + minCon);
		System.out.println("Max: " + maxCon);
	}

	public String req1(String name) {
		String res = "";
		LandingPoint lp = landingPoints.get(name);
		Vertex<String, LandingPoint> vertex = graph.getVertex(lp.id);
		res = "" + lp + " " + vertex.outdegree();
		return res;
	}

	public String req2(String country) {
		String res = "";
		return res;
	}

	public String req3(String category_name) {
		String res = "";
		return res;
	}

	public String req4(String tag, int n) {
		String res = "";
		return res;
	}

	@Override
	public String toString() {
		String res = "";
		return res;
	}
}
