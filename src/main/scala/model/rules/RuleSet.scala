package model.rules

import engine.events.EventSink
import model.{MatchState, Player, Tile}
import model.actions.Action
import model.entities.board.{Board, Position}

trait RuleSet {

  def first(players: Set[Player]): Player

  def startPosition(tiles: Set[Tile]): Position

  def actions(state: MatchState): Set[Action]

  def resolveAction(sink: EventSink, action: Action): MatchState

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

    override def resolveAction(state: MatchState, action: Action): MatchState = {
      action.execute(sink: EventSink)
    }
  }

  def apply(allActions: Set[Action]): RuleSet = new EmptyRuleSet(allActions)
}

