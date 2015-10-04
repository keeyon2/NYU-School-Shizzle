package nyu.heursitics;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

import org.jgrapht.*;
import org.jgrapht.graph.ClassBasedEdgeFactory;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

public class FileEditor {
	
	
	ArrayList<City> citiesArrayList = new ArrayList<City>();
	Scanner scanner = null;
	
	FileEditor(String outputFile, String inputFile) throws NumberFormatException, IOException {
		String line = null;
		
		WeightedGraph<City, DefaultWeightedEdge> OriginalGraph = 
				new SimpleWeightedGraph<City, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
		EdgeFactory<City, DefaultEdge> Efac = 
				new ClassBasedEdgeFactory<City, DefaultEdge>(DefaultWeightedEdge.class);
		
		int cityId = 0;
		int cityX = 0;
		int cityY = 0;
		int cityZ = 0;
		
		String strLine;
		
		try {
			FileReader file = new FileReader(inputFile);
			BufferedReader reader = new BufferedReader(file);
			
			while((strLine = reader.readLine())!= null) {
				String[] words = strLine.split("\\s+");
				
	        	cityId = Integer.parseInt(words[0]);
	        	cityX = Integer.parseInt(words[1]);
	        	cityY = Integer.parseInt(words[2]);
	        	cityZ = Integer.parseInt(words[3]);
	        	
	        	City tmpCity = new City(cityId, cityX, cityY, cityZ);
	            OriginalGraph.addVertex(tmpCity);
	            citiesArrayList.add(tmpCity);
	            //cities[cityId - 1] = tmpCity;
			}
		}
		
		catch (FileNotFoundException e) {
		    e.printStackTrace();  
		}
		
		City[] cities = new City[citiesArrayList.size()];
		for (City c : citiesArrayList) {
			cities[c.id - 1] = c;
		}
		 		    
	    // Create Edges in OriginalGraph
	    for (int i = 0; i < cities.length; i++) {
	    	for (int j = i + 1; j < cities.length; j++) {
	    		OriginalGraph.addEdge(cities[i], cities[j]);
	    		DefaultWeightedEdge e = OriginalGraph.getEdge(cities[i], cities[j]);
	    		OriginalGraph.setEdgeWeight(e, cities[i].DistanceToCity(cities[j]));
	    	}
	    }
	    
	    TravelingSalesman tsp = new TravelingSalesman(OriginalGraph, cities);
	    
	    try {
			PrintWriter writer = new PrintWriter(outputFile, "UTF-8");
			
			for (Integer city : tsp.finalPath) {
		    	writer.println(city);
		    }
			writer.close();
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
