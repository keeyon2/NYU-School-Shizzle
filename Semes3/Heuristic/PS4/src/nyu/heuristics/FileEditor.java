package nyu.heuristics;

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
	
	
	ArrayList<Person> personArrayList = new ArrayList<Person>();
    ArrayList<Integer> ambulanceCount = new ArrayList<Integer>();
	Scanner scanner = null;
	
	FileEditor(String inputFile) throws NumberFormatException, IOException {
		String line = null;
				
		int personX = 0;
		int personY = 0;
		int personTimeRemaining = 0;
		int personId = 0;
		
		String strLine;
		
		try {
			FileReader file = new FileReader(inputFile);
			BufferedReader reader = new BufferedReader(file);
			int indexCounter = 0;
            int personCounter = 0;
			while((strLine = reader.readLine())!= null) {
				String[] words = strLine.split("\\D+");
				
                if (words.length == 3) {
	        	    personX = Integer.parseInt(words[0]);
	        	    personY = Integer.parseInt(words[1]);
	        	    personTimeRemaining = Integer.parseInt(words[2]);
	        	    
	        	    Person tmpPerson = new Person(personX, personY, personTimeRemaining, personCounter);
	                personArrayList.add(tmpPerson);
	        	    personCounter++;
                }
                
                else if (words.length == 0) {
                    continue;
                }

                else if (words.length == 1) {
                	if(words[0] != null && !words[0].isEmpty()) {
                		ambulanceCount.add(Integer.valueOf(words[0]));
                	}
                }
			}
		}
		
		catch (FileNotFoundException e) {
		    e.printStackTrace();  
		}
		
		AmbulanceProblem ap = new AmbulanceProblem(personArrayList, ambulanceCount);
	}
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		
		// TODO Auto-generated method stub
		
		if (args.length != 1) {
			System.out.println("Program needs 1 inputs");
		}
		else {
		  String inputFile = args[0];
		  FileEditor fileEditor = new FileEditor(inputFile);
		}
	}
}
