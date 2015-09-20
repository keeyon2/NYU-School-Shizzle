package nyu.heuristics;

import java.util.Arrays;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	  System.out.println("Exact Change: ");
	  ExactChange(7, 240, 13.0);
	  System.out.println("");
	  CoinsExchanged(3, 11, 1.0);
	  
	}
	
	public static void ExactChange(int DenominationNumber, int TotalPoundValue, double N) {
	  ExactChange foo = new ExactChange(DenominationNumber, TotalPoundValue, N);
	}
	
	 public static void CoinsExchanged(int DenominationNumber, int TotalPoundValue, double N) {
	    CoinsExchanged foo = new CoinsExchanged(DenominationNumber, TotalPoundValue, N);
	  }
}
