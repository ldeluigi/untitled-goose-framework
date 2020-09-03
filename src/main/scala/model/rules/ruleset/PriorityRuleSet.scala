package model.rules.ruleset

import model.actions.Action
import model.entities.runtime.{MutableGameState, Player, Position, Tile}
import model.rules.actionrules.ActionRule
import model.rules.behaviours.{BehaviourRule, DialogLaunchBehaviour, TurnEndEventBehaviour}
import model.rules.cleanup.{CleanupRule, TurnEndConsumer}
import model.rules.operations.Operation

// TODO consider making a builder of this

class PriorityRuleSet(firstPosition: Set[Tile] => Position,
                      playerOrdering: PlayerOrdering,
                      playersRange: Range,
                      actionRules: Set[ActionRule],
                      behaviourRules: Seq[BehaviourRule],
                      cleanupRules: Seq[CleanupRule]
                     ) extends RuleSet {


  override def startPosition(tiles: Set[Tile]): Position = firstPosition(tiles)


  override def actions(state: MutableGameState): Set[Action] =
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

  override def stateBasedOperations(state: MutableGameState): Seq[Operation] =
    TurnEndEventBehaviour().applyRule(state) ++
      behaviourRules.flatMap(_.applyRule(state)) ++
      DialogLaunchBehaviour().applyRule(state)

  override def nextPlayer(currentPlayer: Player, players: Set[Player]): Player =
    playerOrdering.next(currentPlayer, players)

  override def cleanupOperations(state: MutableGameState): Unit = {
    cleanupRules.foreach(_.applyRule(state))
    TurnEndConsumer.applyRule(state)
  }

  override def admissiblePlayers: Range = playersRange
}

object PriorityRuleSet {

  /** A factory that produces a new priority rule set.
   *
   * @param startTile      the starting tile
   * @param playerOrdering a random player ordering
   * @param actionRules    a set of action rules
   * @param behaviourRule  behaviour rule
   * @param cleanupRules   cleanup rule
   */
  def apply(startTile: Set[Tile] => Position = tiles => Position(tiles.toList.sorted.take(1).head),
            playerOrdering: PlayerOrdering = PlayerOrdering.randomOrder(7),
            admissiblePlayers: Range,
            actionRules: Set[ActionRule] = Set(),
            behaviourRule: Seq[BehaviourRule] = Seq(TurnEndEventBehaviour()),
            cleanupRules: Seq[CleanupRule] = Seq(),
           ): PriorityRuleSet =
    new PriorityRuleSet(startTile, playerOrdering, admissiblePlayers, actionRules, behaviourRule, cleanupRules)

}
