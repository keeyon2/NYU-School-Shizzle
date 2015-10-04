package nyu.heursitics;

import java.io.FileNotFoundException;
import java.io.IOException;



public class Main {

	public static void main(String[] args) throws NumberFormatException, IOException {
		
		// TODO Auto-generated method stub
		if (args.length != 2) {
			System.out.println("Program needs 2 inputs");
		  }
		else {
		  String outputFile = args[0];
		  String inputFile = args[1];
		  
		  FileEditor fileEditor = new FileEditor(outputFile, inputFile);
		}
	}
}
