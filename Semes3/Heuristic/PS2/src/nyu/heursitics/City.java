package nyu.heursitics;

public class City {
	public int x;
	public int y;
	public int z;
	public int id;
	
	City (int x, int y, int z, int id) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.id = id;
	}
	
	public double DistanceToCity(City c) {
		int xValueInt = this.x - c.x;
		double xValue = Math.pow(xValueInt, 2);
		
		int yValueInt = this.y - c.y;
		double yValue = Math.pow(yValueInt, 2);
		
		int zValueInt = this.z - c.z;
		double zValue = Math.pow(zValueInt, 2);
		
		double valuesAdded = xValue + yValue + zValue;		
		return Math.pow(valuesAdded, 0.5);
	}

}
