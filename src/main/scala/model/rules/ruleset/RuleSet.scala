package model.rules.ruleset

import model.`match`.{MatchState, MutableMatchState}
import model.actions.Action
import model.entities.board.Position
import model.rules.operations.Operation
import model.{Player, Tile}

trait RuleSet {

  def stateBasedOperations(state: MatchState): Seq[Operation]

  def cleanupOperations(state: MutableMatchState): Unit

  def first(players: Set[Player]): Player

  def nextPlayer(currentPlayer: Player, players: Set[Player]): Player

  def startPosition(tiles: Set[Tile]): Position

  def actions(state: MutableMatchState): Set[Action]

}


