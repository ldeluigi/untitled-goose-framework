package model.rules.ruleset

import model.actions.Action
import model.entities.board.Position
import model.rules.operations.Operation
import model.rules.{ActionRule, BehaviourRule, PlayerUtils}
import model.{MatchState, Player, Tile}

class PriorityRuleSet(firstTile: Set[Tile] => Position,
                      firstPlayer: Set[Player] => Player = PlayerUtils.selectRandom,
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

  override def first(players: Set[Player]): Player = firstPlayer(players)

  override def stateBasedOperations(state: MatchState): Seq[Operation] =
    for {rule <- behaviourRules; operation <- rule.applyRule(state)} yield operation
}

object PriorityRuleSet {
  def apply(startTile: Set[Tile] => Position,
            firstPlayer: Set[Player] => Player,
            actionRules: ActionRule*): PriorityRuleSet =
    new PriorityRuleSet(startTile, firstPlayer, actionRules.toSet)


}
