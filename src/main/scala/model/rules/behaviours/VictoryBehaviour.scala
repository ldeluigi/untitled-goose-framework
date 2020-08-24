package model.rules.behaviours

import engine.events.VictoryEvent
import engine.events.root.ExitEvent
import model.entities.DialogContent
import model.game.GameState
import model.game.GameStateExtensions.PimpedHistory
import model.rules.BehaviourRule
import model.rules.operations.{Operation, SpecialOperation}

/** Creates a victory related behaviour rule. */
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
