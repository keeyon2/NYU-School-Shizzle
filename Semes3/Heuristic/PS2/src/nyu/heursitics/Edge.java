package nyu.heursitics;

public class Edge {
	public City CityA;
	public City CityB;
	public double cost;
	
	public Edge(City a, City b, double c) {
		this.CityA = a;
		this.CityB = b;
		this.cost = c;		
	}
	
	public Edge(City a, City b) {
		this.CityA = a;
		this.CityB = b;
		this.cost = a.DistanceToCity(b);	
	}

}
