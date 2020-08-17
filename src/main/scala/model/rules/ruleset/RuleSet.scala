package model.rules.ruleset

import model.actions.Action
import model.entities.board.Position
import model.game.{GameState, MutableGameState}
import model.rules.operations.Operation
import model.{Player, Tile}

trait RuleSet {

  def stateBasedOperations(state: GameState): Seq[Operation]

  def cleanupOperations(state: MutableGameState): Unit

  def first(players: Set[Player]): Player

  def nextPlayer(currentPlayer: Player, players: Set[Player]): Player

  def startPosition(tiles: Set[Tile]): Position

  def actions(state: MutableGameState): Set[Action]

}


