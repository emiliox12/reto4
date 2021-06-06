package model.logic;

public class Connection implements Comparable<Connection> {

	public String origin;
	public String destination;
	public String cable_name;
	public String cable_id;
	public String cable_length;
	public String cable_rfs;
	public String owners;
	public String capacityTBPS;

	public Connection(String origin, String destination, String cable_name, String cable_id, String cable_length,
			String cable_rfs, String owners, String capacityTBPS) {
		this.origin = origin;
		this.destination = destination;
		this.cable_name = cable_name;
		this.cable_id = cable_id;
		this.cable_length = cable_length;
		this.cable_rfs = cable_rfs;
		this.owners = owners;
		this.capacityTBPS = capacityTBPS;
	}

	@Override
	public int compareTo(Connection arg0) {
		return cable_id.compareTo(arg0.cable_id);
	}

	@Override
	public String toString() {
		return cable_name + " / " + origin + " / " + destination + " / " + cable_length + "km";
	}
}
