package model.rules.ruleset

import model.actions.Action
import model.entities.board.Position
import model.game.MutableGameState
import model.rules.behaviours.{BehaviourRule, DialogLaunchBehaviour, TurnEndEventBehaviour}
import model.rules.cleanup.TurnEndConsumer
import model.rules.operations.Operation
import model.rules.{ActionRule, CleanupRule}
import model.{Player, Tile}

class PriorityRuleSet(firstPosition: Set[Tile] => Position,
                      playerOrdering: PlayerOrdering,
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
            playerOrdering: PlayerOrdering = PlayerOrdering.orderedRandom,
            actionRules: Set[ActionRule] = Set(),
            behaviourRule: Seq[BehaviourRule] = Seq(TurnEndEventBehaviour()),
            cleanupRules: Seq[CleanupRule] = Seq()
           ): PriorityRuleSet =
    new PriorityRuleSet(startTile, playerOrdering, actionRules, behaviourRule, cleanupRules)

}
