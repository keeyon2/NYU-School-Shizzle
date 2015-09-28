package nyu.heursitics;

import java.util.ArrayList;
import java.util.Set;

import org.jgrapht.UndirectedGraph;
import org.jgrapht.WeightedGraph;
import org.jgrapht.alg.EulerianCircuit;
import org.jgrapht.alg.KruskalMinimumSpanningTree;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.Multigraph;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.graph.SimpleWeightedGraph;

public class TravelingSalesman {
	
	WeightedGraph<City, DefaultWeightedEdge> OrigTree = 
			new SimpleWeightedGraph<City, DefaultWeightedEdge>(DefaultWeightedEdge.class);
	
	WeightedGraph<City, DefaultWeightedEdge> TreeWithOddDegreeVertexes = 
			new SimpleWeightedGraph<City, DefaultWeightedEdge>(DefaultWeightedEdge.class);
	
	WeightedGraph<City, DefaultWeightedEdge> PerfectMatchingTree = 
			new SimpleWeightedGraph<City, DefaultWeightedEdge>(DefaultWeightedEdge.class);
	
	Multigraph<City, DefaultWeightedEdge> MatchingSpanningTreeUnion =
			new Multigraph<City, DefaultWeightedEdge>(DefaultWeightedEdge.class);


	City[] allCities;
	
	public TravelingSalesman(WeightedGraph<City, DefaultWeightedEdge> OriginalGraph, 
			City[] allCities) {
		
		this.allCities = allCities;
		
		int[] VertexCount = new int[1001];

		this.OrigTree = OriginalGraph;

		// Create Minimum Spanning Tree
		KruskalMinimumSpanningTree<City, DefaultWeightedEdge> minimumSpanningTree = 
				new KruskalMinimumSpanningTree<City, DefaultWeightedEdge>(OriginalGraph);
		
		ArrayList<Integer> OddDegreeVertexIds = new ArrayList<Integer>();
		Set<DefaultWeightedEdge> edges = minimumSpanningTree.getMinimumSpanningTreeEdgeSet();
		
		for (DefaultWeightedEdge edge : edges) {
			int sourceVertexId = OriginalGraph.getEdgeSource(edge).id;
			int targetVertexId = OriginalGraph.getEdgeTarget(edge).id;
			VertexCount[sourceVertexId] += 1;
			VertexCount[targetVertexId] += 1;
		}
		
		// Find all Verticies with Odd Degree
		for (int i = 0; i < VertexCount.length; i++) {
			if (VertexCount[i] % 2 == 1) {
				OddDegreeVertexIds.add(i);
				TreeWithOddDegreeVertexes.addVertex(allCities[i-1]);
			}
		}
		
		
		// Make Graph with only odd edges
	    for (int i = 0; i < allCities.length; i++) {
	    	for (int j = i + 1; j < allCities.length; j++) {
	    		if (OddDegreeVertexIds.contains(i + 1) && 
	    				OddDegreeVertexIds.contains(j + 1) ) {
	    			
	    			TreeWithOddDegreeVertexes.addEdge(allCities[i], allCities[j]);
		    		DefaultWeightedEdge e = TreeWithOddDegreeVertexes.getEdge(allCities[i], allCities[j]);
		    		TreeWithOddDegreeVertexes.setEdgeWeight(e, allCities[i].DistanceToCity(allCities[j]));
	    		}
	    	}
	    }
	    
	    
	    CreatePerfectMatchingTree();
	    
	    UniteMinimumSpanningAndPerfectMatchingTree(minimumSpanningTree);
	    
	    // Working until here
	    

		
	}
	
	public void UniteMinimumSpanningAndPerfectMatchingTree(
			KruskalMinimumSpanningTree<City, DefaultWeightedEdge> msp) {
		
		// Add All Vertexes
		for (int i = 0; i < allCities.length; i++) {
			this.MatchingSpanningTreeUnion.addVertex(allCities[i]);
		}
		
		// Add all Minimum Spanning Tree
		Set<DefaultWeightedEdge> MinimumEdges = msp.getMinimumSpanningTreeEdgeSet();
		for (DefaultWeightedEdge edge : MinimumEdges) {
			
			City SourceCity = this.OrigTree.getEdgeSource(edge);
			City TargetCity = this.OrigTree.getEdgeTarget(edge);
			
			this.MatchingSpanningTreeUnion.addEdge(SourceCity, TargetCity);
    		DefaultWeightedEdge e = this.MatchingSpanningTreeUnion.getEdge(SourceCity, TargetCity);
    		this.MatchingSpanningTreeUnion.setEdgeWeight(e,SourceCity.DistanceToCity(TargetCity));    		
		}
		
		
		
		// Add all PerfMatching
		Set<DefaultWeightedEdge> PerfMatchingEdges = 
				this.PerfectMatchingTree.edgeSet();
		
		for (DefaultWeightedEdge edge : PerfMatchingEdges) {
			
			City SourceCity = this.OrigTree.getEdgeSource(edge);
			City TargetCity = this.OrigTree.getEdgeTarget(edge);
			
			DefaultWeightedEdge nullTest = 
					this.MatchingSpanningTreeUnion.getEdge(SourceCity, TargetCity);
			
			this.MatchingSpanningTreeUnion.addEdge(SourceCity, TargetCity);
	    	DefaultWeightedEdge e = this.MatchingSpanningTreeUnion.getEdge(SourceCity, TargetCity);
	    	this.MatchingSpanningTreeUnion.setEdgeWeight(e,SourceCity.DistanceToCity(TargetCity));
			
			/*
			if (nullTest == null) {
				this.MatchingSpanningTreeUnion.addEdge(SourceCity, TargetCity);
	    		DefaultWeightedEdge e = this.MatchingSpanningTreeUnion.getEdge(SourceCity, TargetCity);
	    		this.MatchingSpanningTreeUnion.setEdgeWeight(e,SourceCity.DistanceToCity(TargetCity));
			}
			else {
				System.out.println("Houston we have a problem with: " + SourceCity.id  + " " +
						TargetCity.id);
			}
			*/
		}
		
		// Test if Eulerian Circ
		System.out.println("Is Eucl?: ");
		boolean Euc = EulerianCircuit.isEulerian((UndirectedGraph<City, DefaultWeightedEdge>)
				this.MatchingSpanningTreeUnion);
		
		System.out.println(Euc);
	}
	
	public void CreatePerfectMatchingTree() {
		// 1. Get all Edges.
		Set<DefaultWeightedEdge> OddDegreeEdges = this.TreeWithOddDegreeVertexes.edgeSet();
		ArrayList<Integer> IncludedVertexes = new ArrayList<Integer>();
		
		while (true) {
			
			DefaultWeightedEdge tmpEdge;
			double cost = Double.MAX_VALUE;
			int includeSourceVertexId = Integer.MAX_VALUE;
			int includeTargetVertexId = Integer.MAX_VALUE;
			
			for (DefaultWeightedEdge edge : OddDegreeEdges) {
				int sourceEdgeVertexId = this.TreeWithOddDegreeVertexes.getEdgeSource(edge).id;
				int targetEdgeVertexId = this.TreeWithOddDegreeVertexes.getEdgeTarget(edge).id;
				if ((!IncludedVertexes.contains(sourceEdgeVertexId)) &&
						(!IncludedVertexes.contains(targetEdgeVertexId))) {
					
					if (cost > this.TreeWithOddDegreeVertexes.getEdgeWeight(edge))
					{
						cost = this.TreeWithOddDegreeVertexes.getEdgeWeight(edge);
						tmpEdge = edge;
						includeSourceVertexId = sourceEdgeVertexId;
						includeTargetVertexId = targetEdgeVertexId;
					}
				}
			}
			
			// Include Vertex into Graph
			if (cost != Double.MAX_VALUE) {
				IncludedVertexes.add(includeSourceVertexId);
				IncludedVertexes.add(includeTargetVertexId);
				
				City SourceCity = allCities[includeSourceVertexId - 1];
				City TargetCity = allCities[includeTargetVertexId - 1];
				
				PerfectMatchingTree.addVertex(SourceCity);
				PerfectMatchingTree.addVertex(TargetCity);
				
				PerfectMatchingTree.addEdge(SourceCity, TargetCity);
	    		DefaultWeightedEdge e = PerfectMatchingTree.getEdge(SourceCity, TargetCity);
	    		TreeWithOddDegreeVertexes.setEdgeWeight(e,SourceCity.DistanceToCity(TargetCity));
			}
			
			else {
				break;
			}	
		}
	}
	
	

}
