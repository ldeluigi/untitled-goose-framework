package untitled.goose.framework.model.rules.ruleset

import untitled.goose.framework.model.actions.Action
import untitled.goose.framework.model.entities.runtime.{GameState, MutableGameState, Player}
import untitled.goose.framework.model.rules.operations.Operation

/** The rule set collects all rules for a game. */
trait RuleSet {

  /**
   * Computes a sequence of operations based on the state.
   * This method can alter the state buffers or histories.
   *
   * @param state the mutable game state.
   * @return a sequence of operations
   */
  def stateBasedOperations(state: MutableGameState): Seq[Operation]

  /**
   * Applies cleanup operations on the state.
   *
   * @param state the mutable game state.
   */
  def cleanupOperations(state: MutableGameState): Unit

  /**
   * Select the first player from a user-defined list.
   *
   * @param players the players in the order they were defined by the user.
   * @return who should go first.
   */
  def first(players: Seq[Player]): Player

  /**
   * Select the next player based on the entire state.
   *
   * @param state the current state.
   * @return the player that should go next.
   */
  def nextPlayer(state: MutableGameState): Player

  /**
   * Returns the set of actions current player can do in this state.
   *
   * @param state the current game state.
   * @return the set of action available to current player.
   */
  def actions(state: GameState): Set[Action]

  /**
   * Returns the minimum and maximum number of players.
   *
   * @return a range of players.
   */
  def allowedPlayers: Range

}


