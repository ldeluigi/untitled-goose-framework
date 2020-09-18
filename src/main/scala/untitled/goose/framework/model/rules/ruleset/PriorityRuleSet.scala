package untitled.goose.framework.model.rules.ruleset

import untitled.goose.framework.model.actions.Action
import untitled.goose.framework.model.entities.runtime.{GameState, MutableGameState, Player}
import untitled.goose.framework.model.rules.actionrules.ActionRule
import untitled.goose.framework.model.rules.behaviours.{BehaviourRule, DialogLaunchBehaviour, TurnEndEventBehaviour}
import untitled.goose.framework.model.rules.cleanup.{CleanupRule, TurnEndConsumer}
import untitled.goose.framework.model.rules.operations.Operation

/**
 * A PriorityRuleSet is a RuleSet that evaluates rules in order, giving
 * more priority to the first. Action rules use ActionAvailability for priority-based
 * action availabilities, so their order is not important.
 *
 * @param playerOrdering the strategy that determines the player order.
 * @param playersRange   determines minimum and maximum number of player allowed.
 * @param actionRules    a set of action rules, that will determine the available actions for
 *                       a user, using [[untitled.goose.framework.model.rules.actionrules.ActionAvailability]].
 * @param behaviourRules a sequence of behaviour rules, ordered by priority of evaluation.
 * @param cleanupRules   a sequence of cleanup rules, ordered by priority of evaluation.
 */
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

  /**
   * Factory method that creates a new PriorityRuleSet with given parameters.
   *
   * @param playerOrdering    the strategy for the player order in the game.
   * @param admissiblePlayers the minimum and maximum number of players allowed.
   * @param actionRules       the set of action rules for the game.
   * @param behaviourRule     the sequence of behaviour rules, ordered by priority.
   * @param cleanupRules      the sequence of cleanup rules, ordered by priority.
   * @return a new PriorityRuleSet.
   */
  def apply(playerOrdering: PlayerOrdering = PlayerOrdering.randomOrder,
            admissiblePlayers: Range,
            actionRules: Set[ActionRule] = Set(),
            behaviourRule: Seq[BehaviourRule] = Seq(TurnEndEventBehaviour()),
            cleanupRules: Seq[CleanupRule] = Seq(),
           ): PriorityRuleSet =
    new PriorityRuleSet(playerOrdering, admissiblePlayers, actionRules, behaviourRule, cleanupRules)

}
