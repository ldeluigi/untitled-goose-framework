package model.rules.ruleset

import model.actions.Action
import model.entities.board.Position
import model.game.{GameState, MutableGameState}
import model.rules.operations.Operation
import model.{Player, Tile}

/** Defines a rule set */
trait RuleSet {

  /** Returns a sequence of operation based on a GameState.
   *
   * @param state the GameState on which to base to withdraw the operations sequence
   * @return the subsequent sequence of operations
   */
  def stateBasedOperations(state: GameState): Seq[Operation]

  /** Performs a clean up operation.
   *
   * @param state the MutableGameState on which to perform the clean up operation
   */
  def cleanupOperations(state: MutableGameState): Unit

  /** Gets the first player.
   *
   * @param players the whole players set
   * @return the first player
   */
  def first(players: Set[Player]): Player

  /** Gets the next player.
   *
   * @param currentPlayer the player on which to look for the next one
   * @param players       the whole players set
   * @return the next player
   */
  def nextPlayer(currentPlayer: Player, players: Set[Player]): Player

  /** The tile's starting position.
   *
   * @param tiles the tile to be set as a starting point
   * @return the position of the tile
   */
  def startPosition(tiles: Set[Tile]): Position

  /** The MutableGameState set of actions.
   *
   * @param state from which to withdraw the actions
   * @return the MutableGameState set of actions
   */
  def actions(state: MutableGameState): Set[Action]

}


