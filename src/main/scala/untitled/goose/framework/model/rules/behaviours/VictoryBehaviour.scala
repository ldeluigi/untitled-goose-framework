package untitled.goose.framework.model.rules.behaviours

import untitled.goose.framework.model.entities.DialogContent
import untitled.goose.framework.model.events.consumable.VictoryEvent
import untitled.goose.framework.model.events.special.ExitEvent
import untitled.goose.framework.model.rules.behaviours.BehaviourRule.BehaviourRuleImpl
import untitled.goose.framework.model.rules.operations.Operation.DialogOperation

// TODO scaladoc
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
