package nyu.heuristics;

import java.util.Arrays;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	  
	  if (args.length > 0) {
	    double N = Double.parseDouble(args[0]);
	    //ExactChange(7, 240, N);
	    CoinsExchanged(7, 240, N);
	  }
	  else {
	    System.out.println("Need atleast 1 input");
	  }
	}
	
	public static void ExactChange(int DenominationNumber, int TotalPoundValue, double N) {
	  ExactChange foo = new ExactChange(DenominationNumber, TotalPoundValue, N);
	}
	
	 public static void CoinsExchanged(int DenominationNumber, int TotalPoundValue, double N) {
	    CoinsExchanged foo = new CoinsExchanged(DenominationNumber, TotalPoundValue, N);
	  }
}
