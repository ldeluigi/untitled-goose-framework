package model.rules

import engine.events.EventSink
import model.{MatchState, Player, Tile}
import model.actions.Action
import model.entities.board.{Board, Position}
import model.rules.operations.Operation

trait RuleSet {

  def stateBasedOperations(state: MatchState): Seq[Operation]

  def first(players: Set[Player]): Player

  def startPosition(tiles: Set[Tile]): Position

  def actions(state: MatchState): Set[Action]

}

object RuleSet {

  private class EmptyRuleSet(allActions: Set[Action]) extends RuleSet {

    override def first(players: Set[Player]): Player = {
      PlayerRule.selectRandom(players)
    }

    override def startPosition(tiles: Set[Tile]): Position = {
      Position(tiles.toList.sorted.take(1).head)
    }

    override def actions(state: MatchState): Set[Action] = {
      allActions
    }

    override def stateBasedOperations(state: MatchState): Seq[Operation] = Seq() // TODO think about
  }

  def apply(allActions: Set[Action]): RuleSet = new EmptyRuleSet(allActions)
}

