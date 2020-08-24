package model.rules.behaviours

import engine.events.consumable.VictoryEvent
import engine.events.special.ExitEvent
import model.entities.DialogContent
import model.rules.behaviours.BehaviourRule.BehaviourRuleImpl
import model.rules.operations.Operation.DialogOperation

case class VictoryBehaviour() extends BehaviourRuleImpl[VictoryEvent](
  operationsStrategy = (events, _) => {
    Seq(DialogOperation(DialogContent(
      dialogTitle = "Victory!",
      dialogText = "Winning players: " + events.map(_.player.name).mkString(", "),
      "Quit" -> ExitEvent,
    )))
  },
  consume = true,
  save = true
)
