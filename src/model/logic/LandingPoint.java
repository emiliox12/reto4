package model.logic;

public class LandingPoint implements Comparable<LandingPoint> {

	public String landing_point_id;
	public String id;
	public String name;
	public String latitude;
	public String longitude;
	public String country;
	public String city;

	public LandingPoint(String landing_point_id, String id, String name, String latitude, String longitude) {
		this.landing_point_id = landing_point_id;
		this.id = id;
		this.name = name;
		this.latitude = latitude;
		this.longitude = longitude;
		city = name.split("\\s")[0].trim();
		country = name.split("\\s")[1].trim();
	}

	@Override
	public int compareTo(LandingPoint lp) {
		return id.compareTo(lp.id);
	}

}
