package nyu.heursitics;

public class MyEdge {
	public City CityA;
	public City CityB;
	public double cost;
	
	public MyEdge(City a, City b, double c) {
		this.CityA = a;
		this.CityB = b;
		this.cost = c;		
	}
	
	public MyEdge(City a, City b) {
		this.CityA = a;
		this.CityB = b;
		this.cost = a.DistanceToCity(b);	
	}

}
