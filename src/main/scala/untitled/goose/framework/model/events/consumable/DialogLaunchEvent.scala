package untitled.goose.framework.model.events.consumable

import untitled.goose.framework.model.entities.DialogContent

case class DialogLaunchEvent(turn: Int, cycle: Int, content: DialogContent)
  extends ConsumableGameEvent
