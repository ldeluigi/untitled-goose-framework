package untitled.goose.framework.model.rules.ruleset

import untitled.goose.framework.model.actions.Action
import untitled.goose.framework.model.entities.runtime.{GameState, MutableGameState, Player}
import untitled.goose.framework.model.rules.actionrules.ActionRule
import untitled.goose.framework.model.rules.behaviours.{BehaviourRule, DialogLaunchBehaviour, TurnEndEventBehaviour}
import untitled.goose.framework.model.rules.cleanup.{CleanupRule, TurnEndConsumer}
import untitled.goose.framework.model.rules.operations.Operation

// TODO scaladoc afterwards
class PriorityRuleSet(playerOrdering: PlayerOrdering,
                      playersRange: Range,
                      actionRules: Set[ActionRule],
                      behaviourRules: Seq[BehaviourRule],
                      cleanupRules: Seq[CleanupRule]
                     ) extends RuleSet {


  override def actions(state: GameState): Set[Action] =
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

  override def first(players: Seq[Player]): Player =
    playerOrdering.first(players)

  override def stateBasedOperations(state: MutableGameState): Seq[Operation] =
    TurnEndEventBehaviour().applyRule(state) ++
      behaviourRules.flatMap(_.applyRule(state)) ++
      DialogLaunchBehaviour().applyRule(state)

  override def nextPlayer(state: MutableGameState): Player =
    playerOrdering.next(state.currentPlayer, state.players)

  override def cleanupOperations(state: MutableGameState): Unit = {
    cleanupRules.foreach(_.applyRule(state))
    TurnEndConsumer.applyRule(state)
  }

  override def allowedPlayers: Range = playersRange
}

object PriorityRuleSet {

  def apply(playerOrdering: PlayerOrdering = PlayerOrdering.randomOrder,
            admissiblePlayers: Range,
            actionRules: Set[ActionRule] = Set(),
            behaviourRule: Seq[BehaviourRule] = Seq(TurnEndEventBehaviour()),
            cleanupRules: Seq[CleanupRule] = Seq(),
           ): PriorityRuleSet =
    new PriorityRuleSet(playerOrdering, admissiblePlayers, actionRules, behaviourRule, cleanupRules)

}
