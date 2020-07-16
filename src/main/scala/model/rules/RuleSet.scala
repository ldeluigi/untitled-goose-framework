package model.rules

import model.{MatchState, Player, Tile}
import model.actions.Action
import model.entities.board.{Board, Position}

trait RuleSet {

  def first(players: Set[Player]): Player

  def startPosition(tiles: List[Tile]): Position

  def actions(state: MatchState): Set[Action]

  def resolveAction(state: MatchState, action: Action): MatchState

}

object RuleSet {

  private class EmptyRuleSet(allActions: Set[Action]) extends RuleSet {

    override def first(players: Set[Player]): Player = {
      players.head
    }

    override def startPosition(tiles: List[Tile]): Position = {
      Position(tiles.take(1).head)
    }

    override def actions(state: MatchState): Set[Action] = {
      allActions
    }

    override def resolveAction(state: MatchState, action: Action): MatchState = {
      action.execute(state)
    }
  }

  def apply(allActions: Set[Action]): RuleSet = new EmptyRuleSet(allActions)
}

