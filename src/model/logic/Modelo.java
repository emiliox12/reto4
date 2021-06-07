package model.logic;

import java.io.FileReader;
import java.io.Reader;

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
				countries.put(CountryName, country);
				countriesCount++;
			}
			in = new FileReader("./data/landing_points.csv");
			Iterable<CSVRecord> landingPointsCsv = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
			for (CSVRecord record : landingPointsCsv) {
				String landing_point_id = record.get("landing_point_id");
				String id = record.get("id");
				String name = record.get("name");
				String latitude = record.get("latitude");
				String longitude = record.get("longitude");
				String countryName = name.split("\\s")[1].trim();
				LandingPoint lp = new LandingPoint(landing_point_id, id, name, latitude, longitude);
				landingPoints.put(id.trim(), lp);
				graph.incertVertex(id.trim(), lp);
				Country country = countries.get(countryName);
				country.addLaindingPoint(lp);
				lpsCount++;
			}
			System.out.println(graph.numVertices());
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

	public String req1(String name1, String name2) {
		NodoTS<Integer, ITablaSimbolos<String, Integer>> nodo = graph.getSCC();
		String clusters = nodo.getKey().toString();
		ITablaSimbolos<String, Integer> table = nodo.getValue();
		String sameCluster = table.get(name1) == table.get(name2) ? " sí " : " no ";
		String res = "Los langing points " + name1 + " " + name2 + sameCluster + "estan en el mismo cluster.";
		res += "\nEl grafo tiene " + clusters + " componentes conectados";
		return res;
	}

	public String req2() {
		ILista<Vertex<String, LandingPoint>> vertices = graph.vertices();
		ILista<LandingPoint> list = new ArregloDinamico<>(vertices.size() / 2);
		int count = 0;
		int max = -1;
		for (int i = 0; i < vertices.size(); i++) {
			Vertex<String, LandingPoint> vertex = vertices.getElement(i);
			int out = vertex.outdegree();
			if (out > max) {
				count = 0;
				max = out;
			}
			if (max == out) {
				list.changeInfo(count, vertex.getValue());
				count++;
			}
		}
		list = list.sublista(count);
		String res = "Con un total de cables conectados de " + max + " los cables son: ";
		for (int i = 0; i < list.size(); i++) {
			LandingPoint lp = list.getElement(i);
			res += "\nnombre: " + lp.id + " país: " + lp.country + " identificador: " + lp.landing_point_id;
		}
		return res;
	}

	public String req3(String countryName1, String countryName2) {
		String res = "";
		Country country1 = countries.get(countryName1);
		Country country2 = countries.get(countryName2);
		float minLength = -1f;
		ILista<Edge<String, LandingPoint>> edges = new ListaEncadenada<>();
		ILista<LandingPoint> lps1 = country1.landingPoints;
		ILista<LandingPoint> lps2 = country2.landingPoints;
		for (int i = 0; i < lps1.size(); i++) {
			LandingPoint lp1 = lps1.getElement(i);
			for (int j = 0; j < lps2.size(); j++) {
				LandingPoint lp2 = lps2.getElement(j);
				NodoTS<Float, ILista<Edge<String, LandingPoint>>> nodo = graph.minPath(lp1.id, lp2.id);
				float length = nodo.getKey();
				if (minLength == -1f || length < minLength) {
					edges = nodo.getValue();
					minLength = length;
				}
			}
		}
		if (minLength == -1f) { return "No hay ruta entre los dos paises"; }
		res += "La distancia total de la ruta es de " + minLength + "km";
		res += "El camino es:\n";
		for (int i = 0; i < edges.size(); i++) {
			Edge<String, LandingPoint> edge = edges.getElement(i);
			res += "landing1: " + edge.getOrigin() + "landing2: " + edge.getDestination() + " con " + edge.getWeight() + "km\n->";
		}
		return res;
	}

	public String req4(String name) {
		String res = "";
		Vertex<String, LandingPoint> vertex = graph.getVertex(name);
		ILista<Vertex<String, LandingPoint>> neighbours = vertex.getNeighbourVertices();
		ITablaSimbolos<String, Country> affectedcountries = new TablaHashLinearProbing<>(neighbours.size());
		for (int i = 0; i < neighbours.size(); i++) {
			Vertex<String, LandingPoint> curr = neighbours.getElement(i);
			LandingPoint lp = curr.getValue();
			affectedcountries.put(lp.country, countries.get(lp.country));
		}
		ILista<Country> list = affectedcountries.valueSet();
		res += "El numero de paises afectados es " + vertex.outdegree() + "\n";
		res += list.toString();
		return res;
	}

	@Override
	public String toString() {
		String res = "";
		return res;
	}
}
