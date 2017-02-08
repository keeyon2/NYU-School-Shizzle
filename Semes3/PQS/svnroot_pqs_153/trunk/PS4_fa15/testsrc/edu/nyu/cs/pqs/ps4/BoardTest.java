package edu.nyu.cs.pqs.ps4;

import static org.junit.Assert.*;

import java.util.UUID;
import org.junit.Before;
import org.junit.Test;

public class BoardTest {

  Board gameBoard;
  Player playerOne;
  Player playerTwo;
  int totalRows;
  int totalColumns;
  
  @Before
  public void setUp() throws Exception {
    playerOne = new Player("player1");
    playerTwo = new Player("player2");
    totalRows = 6;
    totalColumns = 7;
    gameBoard = new Board(playerOne, playerTwo, totalRows, totalColumns);
  }

  @Test
  public void isFull_false() {
    gameBoard.makeMove(0, playerOne.getUserUUID());
    gameBoard.makeMove(0, playerOne.getUserUUID());
    gameBoard.makeMove(0, playerOne.getUserUUID());
    gameBoard.makeMove(0, playerOne.getUserUUID());
    gameBoard.makeMove(0, playerOne.getUserUUID());
    gameBoard.makeMove(0, playerOne.getUserUUID());
    gameBoard.makeMove(0, playerOne.getUserUUID());
    gameBoard.makeMove(1, playerOne.getUserUUID());
    gameBoard.makeMove(2, playerOne.getUserUUID());
    gameBoard.makeMove(3, playerOne.getUserUUID());
    
    assertFalse(gameBoard.boardIsFull());
  }
  
  @Test
  public void isFull_true() {
    for (int row = 0; row < totalRows; row++) {
      for (int column = 0; column < totalColumns; column++) {
        gameBoard.makeMove(column, playerOne.getUserUUID());
      }
    }
    
    assertTrue(gameBoard.boardIsFull());
  }
  
  @Test
  public void makeMove_canHandleTooManyMoves() {
    for (int placement = 0; placement < 30; placement++) {
      gameBoard.makeMove(0, playerOne.getUserUUID());
    }
  }
  
  @Test 
  public void makeMove_movePastLastColumnReturnsFalse() {
    boolean tooHighMove = gameBoard.makeMove(totalColumns, playerOne.getUserUUID());
    assertFalse(tooHighMove);
  }
  
  @Test 
  public void makeMove_moveNegativeColumnReturnsFalse() {
    boolean negativeColumnMove = gameBoard.makeMove(-1, playerOne.getUserUUID());
    assertFalse(negativeColumnMove);
  }
  
  @Test
  public void makeMove_incorrectUUIDMakesNoMove() {
    UUID randomUUID = UUID.randomUUID();
    boolean randomPlayerMove = gameBoard.makeMove(0, randomUUID);
    assertFalse(randomPlayerMove);
  }
  
  @Test
  public void makeMove_correctPlacesSpot() {
    boolean correctMove = gameBoard.makeMove(0, playerOne.getUserUUID());
    boolean correctBoardSpace;
    BoardSpace[][] localBoardPlacement = gameBoard.getBoardLayout();
    if (localBoardPlacement[totalRows-1][0] == BoardSpace.PLAYER1 &&
        localBoardPlacement[totalRows-2][0] == BoardSpace.EMPTY) {
      correctBoardSpace = true;
    }
    else {
      correctBoardSpace = false;
    }
    
    assertTrue(correctMove);
    assertTrue(correctBoardSpace);
  }
  
  @Test
  public void makeMove_boardStateCorrectAfterMultipleMoves() {
    gameBoard.makeMove(0, playerOne.getUserUUID());
    gameBoard.makeMove(0, playerTwo.getUserUUID());
    gameBoard.makeMove(0, playerOne.getUserUUID());
    gameBoard.makeMove(0, playerTwo.getUserUUID());
    gameBoard.makeMove(1, playerOne.getUserUUID());
    gameBoard.makeMove(1, playerTwo.getUserUUID());
    
    BoardSpace[][] localBoardPlacement = gameBoard.getBoardLayout();
    boolean placeOne = localBoardPlacement[5][0] == BoardSpace.PLAYER1;
    boolean placeTwo = localBoardPlacement[4][0] == BoardSpace.PLAYER2;
    boolean placeThree = localBoardPlacement[3][0] == BoardSpace.PLAYER1;
    boolean placeFour = localBoardPlacement[2][0] == BoardSpace.PLAYER2;
    boolean placeFive = localBoardPlacement[1][0] == BoardSpace.EMPTY;
    boolean placeSix = localBoardPlacement[0][0] == BoardSpace.EMPTY;
    boolean placeSeven = localBoardPlacement[0][1] == BoardSpace.EMPTY;
    boolean placeEight = localBoardPlacement[5][1] == BoardSpace.PLAYER1;
    boolean placeNine = localBoardPlacement[4][1] == BoardSpace.PLAYER2;
    
    assertTrue(placeOne);
    assertTrue(placeTwo);
    assertTrue(placeThree);
    assertTrue(placeFour);
    assertTrue(placeFive);
    assertTrue(placeSix);
    assertTrue(placeSeven);
    assertTrue(placeEight);
    assertTrue(placeNine);
  }
  
  @Test
  public void checkForWinner_False() {
    // Making Verticle Moves
    gameBoard.makeMove(0, playerOne.getUserUUID());
    gameBoard.makeMove(0, playerOne.getUserUUID());
    gameBoard.makeMove(0, playerOne.getUserUUID());
    gameBoard.makeMove(0, playerTwo.getUserUUID());
    gameBoard.makeMove(0, playerTwo.getUserUUID());
    
    
    gameBoard.makeMove(1, playerOne.getUserUUID());
    gameBoard.makeMove(1, playerOne.getUserUUID());
    gameBoard.makeMove(1, playerOne.getUserUUID());
    gameBoard.makeMove(1, playerTwo.getUserUUID());
    gameBoard.makeMove(1, playerTwo.getUserUUID());
    
    gameBoard.makeMove(2, playerOne.getUserUUID());
    gameBoard.makeMove(2, playerOne.getUserUUID());
    gameBoard.makeMove(2, playerOne.getUserUUID());
    gameBoard.makeMove(2, playerTwo.getUserUUID());
    gameBoard.makeMove(2, playerTwo.getUserUUID());
    
    gameBoard.makeMove(3, playerTwo.getUserUUID());
    gameBoard.makeMove(3, playerTwo.getUserUUID());
    gameBoard.makeMove(3, playerTwo.getUserUUID());
    
    assertEquals(gameBoard.checkForWinner(), BoardSpace.EMPTY);
  }
  
  @Test
  public void checkForWinner_VerticleTrue() {
    gameBoard.makeMove(0, playerOne.getUserUUID());
    gameBoard.makeMove(0, playerOne.getUserUUID());
    gameBoard.makeMove(0, playerOne.getUserUUID());
    gameBoard.makeMove(0, playerOne.getUserUUID());
    assertEquals(gameBoard.checkForWinner(), BoardSpace.PLAYER1);
  }
  
  @Test
  public void checkForWinner_VerticleStackedTrue() {
    gameBoard.makeMove(0, playerTwo.getUserUUID());
    gameBoard.makeMove(0, playerTwo.getUserUUID());
    gameBoard.makeMove(0, playerOne.getUserUUID());
    gameBoard.makeMove(0, playerOne.getUserUUID());
    gameBoard.makeMove(0, playerOne.getUserUUID());
    gameBoard.makeMove(0, playerOne.getUserUUID());
    assertEquals(gameBoard.checkForWinner(), BoardSpace.PLAYER1);
  }
  
  @Test
  public void checkForWinner_VerticleFalse() {
    gameBoard.makeMove(0, playerOne.getUserUUID());
    gameBoard.makeMove(0, playerOne.getUserUUID());
    gameBoard.makeMove(0, playerOne.getUserUUID());
    gameBoard.makeMove(0, playerTwo.getUserUUID());
    assertEquals(gameBoard.checkForWinner(), BoardSpace.EMPTY);
  }
  
  @Test
  public void checkForWinner_HorizontalTrue() {
    gameBoard.makeMove(totalColumns - 1, playerTwo.getUserUUID());
    gameBoard.makeMove(totalColumns - 2, playerTwo.getUserUUID());
    gameBoard.makeMove(totalColumns - 3, playerTwo.getUserUUID());
    gameBoard.makeMove(totalColumns - 4, playerTwo.getUserUUID());
    assertEquals(gameBoard.checkForWinner(), BoardSpace.PLAYER2);
  }
  
  @Test
  public void checkForWinner_TopLeftBottomRightTrue() {
    gameBoard.makeMove(totalColumns - 1, playerTwo.getUserUUID());
    
    gameBoard.makeMove(totalColumns - 2, playerOne.getUserUUID());
    gameBoard.makeMove(totalColumns - 2, playerTwo.getUserUUID());
    
    gameBoard.makeMove(totalColumns - 3, playerOne.getUserUUID());
    gameBoard.makeMove(totalColumns - 3, playerOne.getUserUUID());
    gameBoard.makeMove(totalColumns - 3, playerTwo.getUserUUID());
    
    gameBoard.makeMove(totalColumns - 4, playerOne.getUserUUID());
    gameBoard.makeMove(totalColumns - 4, playerOne.getUserUUID());
    gameBoard.makeMove(totalColumns - 4, playerOne.getUserUUID());
    gameBoard.makeMove(totalColumns - 4, playerTwo.getUserUUID());
    
    assertEquals(gameBoard.checkForWinner(), BoardSpace.PLAYER2);
  }
  
  @Test
  public void checkForWinner_TopLeftBottomRightFalse() {
    gameBoard.makeMove(totalColumns - 1, playerTwo.getUserUUID());
    
    gameBoard.makeMove(totalColumns - 2, playerOne.getUserUUID());
    gameBoard.makeMove(totalColumns - 2, playerTwo.getUserUUID());
    
    gameBoard.makeMove(totalColumns - 3, playerOne.getUserUUID());
    gameBoard.makeMove(totalColumns - 3, playerOne.getUserUUID());
    gameBoard.makeMove(totalColumns - 3, playerTwo.getUserUUID());
    
    gameBoard.makeMove(totalColumns - 4, playerOne.getUserUUID());
    gameBoard.makeMove(totalColumns - 4, playerOne.getUserUUID());
    gameBoard.makeMove(totalColumns - 4, playerTwo.getUserUUID());
    gameBoard.makeMove(totalColumns - 4, playerOne.getUserUUID());
    gameBoard.makeMove(totalColumns - 4, playerTwo.getUserUUID());
    gameBoard.makeMove(totalColumns - 4, playerOne.getUserUUID());
    
    assertEquals(gameBoard.checkForWinner(), BoardSpace.EMPTY);
  }
  
  @Test
  public void checkForWinner_TopRightBottomLeftTrue() {
    gameBoard.makeMove(totalColumns - 1, playerTwo.getUserUUID());
    gameBoard.makeMove(totalColumns - 1, playerTwo.getUserUUID());
    gameBoard.makeMove(totalColumns - 1, playerTwo.getUserUUID());
    gameBoard.makeMove(totalColumns - 1, playerOne.getUserUUID());
    
    gameBoard.makeMove(totalColumns - 2, playerTwo.getUserUUID());
    gameBoard.makeMove(totalColumns - 2, playerTwo.getUserUUID());
    gameBoard.makeMove(totalColumns - 2, playerOne.getUserUUID());
    
    gameBoard.makeMove(totalColumns - 3, playerTwo.getUserUUID());
    gameBoard.makeMove(totalColumns - 3, playerOne.getUserUUID());
    
    
    gameBoard.makeMove(totalColumns - 4, playerOne.getUserUUID());
    
    assertEquals(gameBoard.checkForWinner(), BoardSpace.PLAYER1);
  }
  
}
