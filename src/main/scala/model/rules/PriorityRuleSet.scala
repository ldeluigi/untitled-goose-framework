package model.rules

import engine.events.EventSink
import model.actions.Action
import model.entities.board.Position
import model.{MatchState, Player, Tile}

class PriorityRuleSet(firstTile: Set[Tile] => Position,
                      firstPlayer: Set[Player] => Player = PlayerRule.selectRandom,
                      actionRules: Set[ActionRule] = Set(),
                     ) extends RuleSet {

  override def startPosition(tiles: Set[Tile]): Position = firstTile(tiles)

  override def actions(sink: EventSink): Set[Action] =
    actionRules
      .flatMap(r => r.allowedActions(sink))
      .groupMapReduce(a => a.action)(identity)(
        (a1, a2) =>
          if (a2.priority > a1.priority)
            a2 else a1
      ).view.mapValues(_.allowed)
      .filter(_._2)
      .keySet.toSet

  override def resolveAction(sink: EventSink, action: Action): Unit = action.execute(sink)

  override def first(players: Set[Player]): Player = firstPlayer(players)
}

object PriorityRuleSet {
  def apply(startTile: Set[Tile] => Position,
            firstPlayer: Set[Player] => Player,
            actionRules: ActionRule*): PriorityRuleSet =
    new PriorityRuleSet(startTile, firstPlayer, actionRules.toSet)


}
