package edu.nyu.cs.pqs.ps4;

import java.util.UUID;

/**
 * @author Keeyon
 * Object used by the board to indicate what moves to make
 * Many functions are package private so it can be used by 
 * board and not outside this package
 */
class Player {
  
  private String userName;
  private UUID userUUID;
  private boolean ready;

  /** Initializes values
   * 
   * @param name
   */
  Player(String name) {
    this.userName = name;
    UUID randomUUID = UUID.randomUUID();
    setUserUUID(randomUUID);
    setReady(false);
  }
  
  /** Ready getter
   * 
   * @return
   */
  boolean isReady() {
    return ready;
  }

  /** Ready setter
   * @param ready
   */
  void setReady(boolean ready) {
    this.ready = ready;
  }

  /** User Name getter
   * @return
   */
  String getUserName() {
    return userName;
  }

  /** UUID getter
   * @return
   */
  UUID getUserUUID() {
    return userUUID;
  }

  /**
   * @param userUUID
   */
  private void setUserUUID(UUID userUUID) {
    this.userUUID = userUUID;
  }
}
