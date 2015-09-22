package nyu.heuristics;

import java.util.Arrays;

public class CoinsExchanged {
  
  double N;
  int TotalPoundValue;
  int[] InitialStartValuesWithTotal;
  int[] StartingDenominations;
  int[] FinalDenominations;
  
  // THIS IS THE BABY I NEED YOU
  public CoinsExchanged(int DenominationNumber, int TotalPoundValue, double N){
    this.TotalPoundValue = TotalPoundValue;
    this.N = N;
    
    StartingDenominations = new int[DenominationNumber];
    
    for (int i = 0; i < DenominationNumber; i++) {
      StartingDenominations[i] = i + 1;
    }
    
    InitializeStartValues(TotalPoundValue);
    
    FindBestDenomination();
  }
  
  private void InitializeStartValues(int TotalPoundValue) {
    this.InitialStartValuesWithTotal = new int[TotalPoundValue];
    
    Arrays.fill(this.InitialStartValuesWithTotal, 1000);
    this.InitialStartValuesWithTotal[0] = 0;
    for (int i = 1; i < this.StartingDenominations.length + 1; i++) {
      this.InitialStartValuesWithTotal[i] = 1;
    }
  }
  
  // Denominatinos should always start at 1
  public double FindTotalScore(int[] Denomination) {
    Arrays.sort(Denomination);
    int[] CoinValues = new int[this.TotalPoundValue];
    Arrays.fill(CoinValues, 1000);
    CoinValues[0] = 0;
    
    double totalSum = 0.0;
    
    for (int i = 0; i < Denomination.length; i++) {
      CoinValues[Denomination[i]] = 1;
    }
    
    for (int i = 2; i < CoinValues.length; i++) {
      for (int j = 0; j < Denomination.length; j++) {
        int CurrentDen = Denomination[j];
        int CoinValueBasedOnPreviousDen = Integer.MAX_VALUE;
        if ((i - CurrentDen) > 0) {
          CoinValueBasedOnPreviousDen = CoinValues[i - CurrentDen] + 1;
        }
        else if ((i - CurrentDen) < 0) {
          CoinValueBasedOnPreviousDen = CoinValues[CurrentDen - i] + 1;  
        }
        
        if (CoinValues[i] > CoinValueBasedOnPreviousDen) {
          CoinValues[i] = CoinValueBasedOnPreviousDen;
        }
        
      }
    }
    
    for (int i = 1; i < CoinValues.length; i++) {
      if (i % 5 == 0) {
        totalSum += (this.N * CoinValues[i]);
      }
      else {
        totalSum += CoinValues[i];
      }
    }
    
    return totalSum;
  }
  
  public void FindBestDenomination() {
    
    int[] CoinValues = new int[this.InitialStartValuesWithTotal.length];
    int[] Denominations = new int[this.StartingDenominations.length];
    int[] TestDenominations = new int[this.StartingDenominations.length];
    double finalScore = 0.0;
    
    System.arraycopy(this.InitialStartValuesWithTotal, 0, CoinValues, 0, 
        this.InitialStartValuesWithTotal.length);
    
    System.arraycopy(this.StartingDenominations, 0, Denominations, 0, 
        this.StartingDenominations.length);
    
    for (int i = Denominations.length + 1; i < CoinValues.length; i++) {
      
      double score = FindTotalScore(Denominations);
      int MinIndex = Integer.MAX_VALUE;
      
      for (int j = 1; j < Denominations.length; j++) {
        System.arraycopy(Denominations, 0, TestDenominations, 0, 
            Denominations.length);
        
        TestDenominations[j] = i;
        double testScore = FindTotalScore(TestDenominations);
        
        if (testScore < score) {
          score = testScore;
          MinIndex = j;
        }
      }
      
      if (MinIndex < Integer.MAX_VALUE) {
        Denominations[MinIndex] = i;
      }
      
      finalScore = score;
    }
    
    this.FinalDenominations = Denominations;
    for (int i = 0; i < this.FinalDenominations.length; i++) {
      if (i != this.FinalDenominations.length - 1) {
        System.out.print(this.FinalDenominations[i] + " ");
      }
      else {
        System.out.print(this.FinalDenominations[i]);
      }
    }
  }
} 

