package edu.nyu.cs.pqs.ps4;

import static org.junit.Assert.*;
import java.util.UUID;
import org.junit.Before;
import org.junit.Test;
import org.junit.After;

public class ServerTest {

  Server gameServer = Server.getInstance();
  TestPlayer testPlayer1;
  TestPlayer testPlayer2;
  
  UUID testPlayer1UUID;
  UUID testPlayer2UUID;
  
  @Before
  public void setUp() throws Exception {
    testPlayer1 = new TestPlayer();
    testPlayer2 = new TestPlayer();
    testPlayer1UUID = gameServer.registerPlayer(testPlayer1, "testPlayer1");
    testPlayer2UUID = gameServer.registerPlayer(testPlayer2, "testPlayer2");
    connectPlayers();
  }
  
  @After
  public void tearDown() throws Exception {
    gameServer.setGameRunning(false);
    gameServer.removeRegisteredPlayer(testPlayer1UUID);
    gameServer.removeRegisteredPlayer(testPlayer2UUID);
  }
  
  @Test
  public void gameIsOver_false() {
    gameServer.makeMoveAtZeroIndexedColumn(testPlayer1UUID, 3);
    assertFalse(gameServer.gameIsOver());
  }
  
  @Test
  public void gameIsOver_player1Win() {
    gameServer.makeMoveAtZeroIndexedColumn(testPlayer1UUID, 0);
    gameServer.makeMoveAtZeroIndexedColumn(testPlayer2UUID, 1);
    gameServer.makeMoveAtZeroIndexedColumn(testPlayer1UUID, 0);
    gameServer.makeMoveAtZeroIndexedColumn(testPlayer2UUID, 1);
    gameServer.makeMoveAtZeroIndexedColumn(testPlayer1UUID, 0);
    gameServer.makeMoveAtZeroIndexedColumn(testPlayer2UUID, 1);
    gameServer.makeMoveAtZeroIndexedColumn(testPlayer1UUID, 0);
    assertTrue(gameServer.gameIsOver());
  }
  
  @Test
  public void gameIsOver_player2Win() {
    gameServer.makeMoveAtZeroIndexedColumn(testPlayer1UUID, 0);
    gameServer.makeMoveAtZeroIndexedColumn(testPlayer2UUID, 1);
    gameServer.makeMoveAtZeroIndexedColumn(testPlayer1UUID, 0);
    gameServer.makeMoveAtZeroIndexedColumn(testPlayer2UUID, 1);
    gameServer.makeMoveAtZeroIndexedColumn(testPlayer1UUID, 0);
    gameServer.makeMoveAtZeroIndexedColumn(testPlayer2UUID, 1);
    gameServer.makeMoveAtZeroIndexedColumn(testPlayer1UUID, 3);
    gameServer.makeMoveAtZeroIndexedColumn(testPlayer2UUID, 1);
    assertTrue(gameServer.gameIsOver());
  }
  
  @Test
  public void gameIsOver_falseWithMany() {
    gameServer.makeMoveAtZeroIndexedColumn(testPlayer1UUID, 0);
    gameServer.makeMoveAtZeroIndexedColumn(testPlayer2UUID, 1);
    gameServer.makeMoveAtZeroIndexedColumn(testPlayer1UUID, 0);
    gameServer.makeMoveAtZeroIndexedColumn(testPlayer2UUID, 1);
    gameServer.makeMoveAtZeroIndexedColumn(testPlayer1UUID, 0);
    gameServer.makeMoveAtZeroIndexedColumn(testPlayer2UUID, 1);
    gameServer.makeMoveAtZeroIndexedColumn(testPlayer1UUID, 4);
    gameServer.makeMoveAtZeroIndexedColumn(testPlayer2UUID, 4);
    gameServer.makeMoveAtZeroIndexedColumn(testPlayer1UUID, 4);
    gameServer.makeMoveAtZeroIndexedColumn(testPlayer2UUID, 4);
    assertFalse(gameServer.gameIsOver());
  }
  
  @Test
  public void moveIsValid_true() {
    gameServer.makeMoveAtZeroIndexedColumn(testPlayer1UUID, 0);
    gameServer.makeMoveAtZeroIndexedColumn(testPlayer2UUID, 1);
    gameServer.makeMoveAtZeroIndexedColumn(testPlayer1UUID, 0);
    gameServer.makeMoveAtZeroIndexedColumn(testPlayer2UUID, 1);
    
    assertTrue(gameServer.moveIsValid(0));
  }
  
  @Test
  public void moveIsValid_falseOnNegative() {
    assertFalse(gameServer.moveIsValid(-1));
  }
  
  @Test
  public void moveIsValid_falseOnTooHigh() {
    assertFalse(gameServer.moveIsValid(gameServer.getTotalColumns()));
  }
  
  @Test
  public void moveIsValid_falseOnTooManyInAColumn() {
    for (int i = 0; i < gameServer.getTotalRows() / 2; i++) {
      gameServer.makeMoveAtZeroIndexedColumn(testPlayer1UUID, 0);
      gameServer.makeMoveAtZeroIndexedColumn(testPlayer2UUID, 0);
    }
    assertFalse(gameServer.moveIsValid(0));
  }
  
  @Test
  public void UUIDtoBoardSpace_true() {
    assertEquals(gameServer.UUIDtoBoardSpace(testPlayer1UUID), BoardSpace.PLAYER1);
  }
  
  @Test
  public void UUIDtoBoardSpace_player2() {
    assertEquals(BoardSpace.PLAYER2, gameServer.UUIDtoBoardSpace(testPlayer2UUID));
  }
  
  @Test
  public void UUIDtoBoardSpace_empty() {
    assertEquals(gameServer.UUIDtoBoardSpace(UUID.randomUUID()), BoardSpace.EMPTY);
  }
  
  public void connectPlayers() {
    gameServer.playerIsReady(testPlayer1UUID, true);
    gameServer.playerIsReady(testPlayer2UUID, true);
  }
}
