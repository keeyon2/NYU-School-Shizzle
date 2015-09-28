package nyu.heursitics;

import java.util.ArrayList;
import org.jgrapht.*;


public class Graph {
	
	//public ArrayList<Edge> Edges = new ArrayList<Edge>();
	public ArrayList<City> Cities = new ArrayList<City>();
	
	public Graph() {
		
	}
	
	public Graph(Graph g) {
		//g.Edges = (ArrayList<Edge>) this.Edges.clone();
		g.Cities = (ArrayList<City>) this.Cities.clone();
	}
	

}
