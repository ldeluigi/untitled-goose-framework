package model.rules.ruleset

import model.actions.{Action, RollDice, StepForwardAction}
import model.entities.Dice
import model.entities.board.Position
import model.rules.PlayerUtils
import model.rules.behaviours.{MovementWithDiceBehaviour, MultipleStepBehaviour, TurnEndConsumer, TurnEndEventBehaviour}
import model.rules.operations.Operation
import model.{MatchState, Player, Tile}

trait RuleSet {

  def stateBasedOperations(state: MatchState): Seq[Operation]

  def first(players: Set[Player]): Player

  def nextPlayer(currentPlayer: Player, players: Set[Player]): Player

  def startPosition(tiles: Set[Tile]): Position

  def actions(state: MatchState): Set[Action]

}

object RuleSet {

  private class DiceForwardRuleSet() extends RuleSet {

    override def first(players: Set[Player]): Player = {
      PlayerUtils.selectRandom(players)
    }

    override def startPosition(tiles: Set[Tile]): Position = {
      Position(tiles.toList.sorted.take(1).head)
    }

    override def actions(state: MatchState): Set[Action] = {
      Set(RollDice(Dice[Int]((1 to 6).toSet, "six face")))
    }

    override def stateBasedOperations(state: MatchState): Seq[Operation] = {
      var opSeq: Seq[Operation] =
        MovementWithDiceBehaviour().applyRule(state) ++
          MultipleStepBehaviour().applyRule(state) ++
          TurnEndEventBehaviour().applyRule(state)

      if (state.newTurnStarted) {
        opSeq = opSeq ++ TurnEndConsumer().applyRule(state)
        state.newTurnStarted = false
      }
      opSeq
    }

    override def nextPlayer(currentPlayer: Player, players: Set[Player]): Player = {
      PlayerUtils.selectRandom(players)
    }
  }

  def apply(): RuleSet = new DiceForwardRuleSet()
}

