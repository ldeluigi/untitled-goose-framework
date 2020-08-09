package model.rules.ruleset

import model.actions.Action
import model.entities.board.Position
import model.rules.behaviours.{DialogLaunchBehaviour, TurnEndConsumer, TurnEndEventBehaviour}
import model.rules.operations.Operation
import model.rules.{ActionRule, BehaviourRule}
import model.{MutableMatchState, Player, Tile}

class PriorityRuleSet(firstPosition: Set[Tile] => Position,
                      playerOrdering: PlayerOrdering,
                      actionRules: Set[ActionRule] = Set(),
                      behaviourRules: Seq[BehaviourRule] = Seq()
                     ) extends RuleSet {

  override def startPosition(tiles: Set[Tile]): Position = firstPosition(tiles)

  override def actions(state: MutableMatchState): Set[Action] =
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

  override def stateBasedOperations(state: MutableMatchState): Seq[Operation] = {
    var opSeq: Seq[Operation] = behaviourRules.flatMap(_.applyRule(state))
    opSeq = TurnEndEventBehaviour().applyRule(state) ++ opSeq
    opSeq = opSeq ++ DialogLaunchBehaviour().applyRule(state)
    if (state.newTurnStarted) {
      opSeq = opSeq ++ TurnEndConsumer().applyRule(state)
      state.newTurnStarted = false
    }
    opSeq
  }

  override def nextPlayer(currentPlayer: Player, players: Set[Player]): Player =
    playerOrdering.next(currentPlayer, players)
}

object PriorityRuleSet {
  def apply(startTile: Set[Tile] => Position = tiles => Position(tiles.toList.sorted.take(1).head),
            playerOrdering: PlayerOrdering = PlayerOrdering.orderedRandom,
            actionRules: Set[ActionRule] = Set(),
            behaviourRule: Seq[BehaviourRule] = Seq()
           ): PriorityRuleSet =
    new PriorityRuleSet(startTile, playerOrdering, actionRules, behaviourRule)

}
