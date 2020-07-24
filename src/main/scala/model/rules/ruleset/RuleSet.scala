package model.rules.ruleset

import engine.events.MovementEvent
import model.actions.{Action, StepForwardAction}
import model.entities.board.Position
import model.rules.PlayerRule
import model.rules.operations.{Operation, StepForwardOperation}
import model.{MatchState, Player, Tile}

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

    override def stateBasedOperations(state: MatchState): Seq[Operation] = {
      val movementList = state.history.filter(x => x match {
        case x: MovementEvent => x.source == state.currentPlayer
        case _ => false
      })
      if (movementList.nonEmpty) {
        Seq(new StepForwardOperation(state.currentPlayer))
      } else {
        Seq()
      }
    }
  }

  def apply(allActions: Set[Action]): RuleSet = new EmptyRuleSet(allActions)
}

