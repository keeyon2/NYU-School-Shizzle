package edu.nyu.cs.pqs.ps4;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ComputerPlayerTest {

  ComputerPlayer computerPlayer1;
  ComputerPlayer computerPlayer2;
  Server gameServer = Server.getInstance();
  
  @Before
  public void setUp() throws Exception {
    computerPlayer1 = ComputerPlayer.newInstance("computerPlayer1");
    computerPlayer2 = ComputerPlayer.newInstance("computerPlayer2");
    computerPlayer1.connectToServer();
    computerPlayer2.connectToServer();
  }
  
  @After
  public void tearDown() throws Exception{
    gameServer.setGameRunning(false);
    gameServer.removeRegisteredPlayer(computerPlayer1.getMyUUID());
    gameServer.removeRegisteredPlayer(computerPlayer2.getMyUUID());
  }

  @Test
  public void sameWinnerts() {
    assertEquals(computerPlayer1.getWinningPlayer(), computerPlayer2.getWinningPlayer());
    assertNotEquals(BoardSpace.EMPTY, computerPlayer1.getWinningPlayer());
  }
}
