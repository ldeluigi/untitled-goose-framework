package model.rules.ruleset

import model.actions.Action
import model.entities.board.Position
import model.rules.behaviours.{TurnEndConsumer, TurnEndEventRule}
import model.rules.operations.Operation
import model.rules.{ActionRule, BehaviourRule, PlayerUtils}
import model.{MatchState, Player, Tile}

class PriorityRuleSet(firstTile: Set[Tile] => Position,
                      playerOrdering: PlayerOrdering,
                      actionRules: Set[ActionRule] = Set(),
                      behaviourRules: Seq[BehaviourRule] = Seq()
                     ) extends RuleSet {

  override def startPosition(tiles: Set[Tile]): Position = firstTile(tiles)

  override def actions(state: MatchState): Set[Action] =
    actionRules
      .flatMap(_.allowedActions(state))
      .groupBy(_.action)
      .map(group => group._2
        .reduce(
          (a1, a2) =>
            if (a2.priority > a1.priority)
              a2 else a1
        )
      )
      .filter(_.allowed)
      .map(_.action)
      .toSet

  override def first(players: Set[Player]): Player =
    playerOrdering.first(players)

  override def stateBasedOperations(state: MatchState): Seq[Operation] = {
    var opSeq: Seq[Operation] = behaviourRules.flatMap(_.applyRule(state))
    opSeq = TurnEndEventRule().applyRule(state) ++ opSeq

    if (state.newTurnStarted) {
      opSeq = opSeq ++ TurnEndConsumer().applyRule(state)
      state.newTurnStarted = false;
    }
    println(opSeq)
    opSeq
  }

  override def nextPlayer(currentPlayer: Player, players: Set[Player]): Player =
    playerOrdering.next(currentPlayer, players)
}

object PriorityRuleSet {
  def apply(startTile: Set[Tile] => Position,
            playerOrdering: PlayerOrdering,
            actionRules: Set[ActionRule],
            behaviourRule: Seq[BehaviourRule]
           ): PriorityRuleSet =
    new PriorityRuleSet(startTile, playerOrdering, actionRules, behaviourRule)


}
