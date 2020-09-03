package model.rules.ruleset

import model.actions.Action
import model.entities.runtime.{MutableGameState, Player, Position, Tile}
import model.rules.operations.Operation

trait RuleSet {

  def stateBasedOperations(state: MutableGameState): Seq[Operation]

  def cleanupOperations(state: MutableGameState): Unit

  def first(players: Set[Player]): Player

  def nextPlayer(currentPlayer: Player, players: Set[Player]): Player

  def startPosition(tiles: Set[Tile]): Position

  def actions(state: MutableGameState): Set[Action]

  def admissiblePlayers: Range

}


