package model.rules.ruleset

import model.actions.{Action, StepForwardAction}
import model.entities.board.Position
import model.rules.PlayerUtils
import model.rules.behaviours.StepForwardRule
import model.rules.operations.Operation
import model.{MatchState, Player, Tile}

trait RuleSet {

  def stateBasedOperations(state: MatchState): Seq[Operation]

  def first(players: Set[Player]): Player

  def startPosition(tiles: Set[Tile]): Position

  def actions(state: MatchState): Set[Action]

}

object RuleSet {

  private class StepForwardRuleSet() extends RuleSet {

    override def first(players: Set[Player]): Player = {
      PlayerUtils.selectRandom(players)
    }

    override def startPosition(tiles: Set[Tile]): Position = {
      Position(tiles.toList.sorted.take(1).head)
    }

    override def actions(state: MatchState): Set[Action] = {
      Set(StepForwardAction())
    }

    override def stateBasedOperations(state: MatchState): Seq[Operation] = {
      StepForwardRule().applyRule(state)
    }
  }

  def apply(): RuleSet = new StepForwardRuleSet()
}

