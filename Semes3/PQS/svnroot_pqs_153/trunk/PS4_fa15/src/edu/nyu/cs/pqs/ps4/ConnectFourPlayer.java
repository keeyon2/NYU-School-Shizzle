package edu.nyu.cs.pqs.ps4;

/**
 * @author Keeyon
 * Messages to be passed from the Server to players
 */
public interface ConnectFourPlayer {
  
  /** Indication that a player has won
   * @param playerName
   * @param winningPlayer
   */
  public void PlayerHasWon(String playerName, BoardSpace winningPlayer);
  
  /** Indication that a player has successfully made a move
   * @param playerName
   * @param playerMadeMove
   * @param column
   */
  public void PlayerHasMadeMove(String playerName, BoardSpace playerMadeMove, int column);
  
  /** Indication that it is a player's turn to make a move
   * @param playerName
   * @param playersTurn
   */
  public void PlayerTurnToMakeMove(String playerName, BoardSpace playersTurn);
}
