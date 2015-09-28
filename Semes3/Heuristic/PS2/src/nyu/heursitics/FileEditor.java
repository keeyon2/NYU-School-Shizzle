package nyu.heursitics;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.util.Scanner;

import org.jgrapht.*;
import org.jgrapht.graph.ClassBasedEdgeFactory;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

/*
import org.jgrapht.EdgeFactory;
import org.jgrapht.WeightedGraph;
import org.jgrapht.graph.UndirectedWeightedSubgraph;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.ClassBasedEdgeFactory;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.graph.UndirectedWeightedSubgraph;
*/


public class FileEditor {
	
	City[] cities = new City[1000];
	Scanner scanner = null;
	
	FileEditor(String outputFile, String inputFile) throws FileNotFoundException {
		String line = null;
		System.out.println("Working Directory = " +
	              System.getProperty("user.dir"));
		
		WeightedGraph<City, DefaultWeightedEdge> OriginalGraph = 
				new SimpleWeightedGraph<City, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
		EdgeFactory<City, DefaultEdge> Efac = 
				new ClassBasedEdgeFactory<City, DefaultEdge>(DefaultWeightedEdge.class);
		
		int cityId = 0;
		int cityX = 0;
		int cityY = 0;
		int cityZ = 0;
		
		try {
			// Jar Working
			// InputStream inStream = new FileInputStream("./" + inputFile);
			// scanner = new Scanner(inStream);
			
			// Debug
			File file = new File(inputFile);
			System.out.println(file.exists());
			System.out.println(file.canRead());
			InputStream inStream = new FileInputStream(inputFile);
			scanner = new Scanner(inStream);
		} 
		
		catch (FileNotFoundException e) {
		    e.printStackTrace();  
		}
		
		int index = 0;
	    while (scanner.hasNextLine()) {
            Scanner wordScanner = new Scanner(scanner.nextLine());
	        while (wordScanner.hasNext()) {
	        	cityZ = Integer.parseInt(wordScanner.next());
	        	cityId = Integer.parseInt(wordScanner.next());
	        	cityX = Integer.parseInt(wordScanner.next());
	        	cityY = Integer.parseInt(wordScanner.next());
	        	
	        	
	        	City tmpCity = new City(cityId, cityX, cityY, cityZ);
	        	OriginalGraph.addVertex(tmpCity);
	        	cities[index] = tmpCity;
	        	index += 1;
	        }
	        wordScanner.close();
	    }
	    scanner.close();
	    
	    // Create Edges in OriginalGraph
	    for (int i = 0; i < cities.length; i++) {
	    	for (int j = i + 1; j < cities.length; j++) {
	    		OriginalGraph.addEdge(cities[i], cities[j]);
	    		DefaultWeightedEdge e = OriginalGraph.getEdge(cities[i], cities[j]);
	    		OriginalGraph.setEdgeWeight(e, cities[i].DistanceToCity(cities[j]));
	    	}
	    }
	    
	    TravelingSalesman tsp = new TravelingSalesman(OriginalGraph, cities);
	    
	}
	
	public void TestCitiesWithPrint() {
	    for (int i = 0; i < 5; i ++) {
		    System.out.println("CityID: " + cities[i].id);
		    System.out.println("CityX: " + cities[i].x);
		    System.out.println("CityY: " + cities[i].y);
		    System.out.println("CityZ: " + cities[i].z);
		    
		    System.out.println();
	    }
	}
	

}
