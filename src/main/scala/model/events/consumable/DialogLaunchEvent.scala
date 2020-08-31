package model.events.consumable

import model.entities.DialogContent

case class DialogLaunchEvent(turn: Int, cycle: Int, content: DialogContent)
  extends ConsumableGameEvent
