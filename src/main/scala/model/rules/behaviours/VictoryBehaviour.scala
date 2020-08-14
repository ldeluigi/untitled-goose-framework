package model.rules.behaviours

import engine.events.VictoryEvent
import engine.events.root.ExitEvent
import model.`match`.MatchState
import model.entities.DialogContent
import model.rules.BehaviourRule
import model.rules.operations.{Operation, SpecialOperation}

case class VictoryBehaviour() extends BehaviourRule {
  override def name: Option[String] = Some("Victory")

  override def applyRule(state: MatchState): Seq[Operation] = {
    if (state.playerPieces.keySet.exists(p => p.history.exists(e => e.isInstanceOf[VictoryEvent] && !e.isConsumed))) {
      state.playerPieces.keySet.foreach(p =>
        p.history.filter(e => e.isInstanceOf[VictoryEvent] && !e.isConsumed)
          .foreach(_.consume())
      )
      Seq(SpecialOperation.DialogOperation(s =>
        DialogContent(
          dialogTitle = "Victory!",
          dialogText = "Winning players: " + s.playerPieces.keySet.filter(p => p.history.exists(e => e.isInstanceOf[VictoryEvent])).map(_.name).mkString(", "),
          "Quit" -> ExitEvent,
        )
      ))
    } else Seq()
  }
}
