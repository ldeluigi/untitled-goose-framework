package model.rules.behaviours

import engine.events.ExitEvent
import engine.events.persistent.player.VictoryEvent
import model.entities.DialogContent
import model.game.GameState
import model.rules.operations.Operation

case class VictoryBehaviour() extends BehaviourRule {
  override def name: Option[String] = Some("Victory")

  override def applyRule(state: GameState): Seq[Operation] =
    state.players.flatMap(_.history.filterNotConsumed().filter(_.isInstanceOf[VictoryEvent])).toSeq.consumeAll()
      .headOption.map(_ => Seq(SpecialOperation.DialogOperation(s =>
      DialogContent(
        dialogTitle = "Victory!",
        dialogText = "Winning players: " + s.players.filter(p => p.history.exists(e => e.isInstanceOf[VictoryEvent])).map(_.name).mkString(", "),
        "Quit" -> ExitEvent,
      )
    ))).getOrElse(Seq())
}
