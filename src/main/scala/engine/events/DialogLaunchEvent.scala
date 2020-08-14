package engine.events

import engine.events.root.ConsumableGameEvent
import model.game.MutableGameState
import model.entities.DialogContent

case class DialogLaunchEvent(currentTurn: Int, createDialog: MutableGameState => DialogContent)
  extends ConsumableGameEvent(currentTurn)
