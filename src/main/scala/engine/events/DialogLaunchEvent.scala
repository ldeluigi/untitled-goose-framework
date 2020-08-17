package engine.events

import engine.events.root.ConsumableGameEvent
import model.entities.DialogContent
import model.game.MutableGameState

case class DialogLaunchEvent(currentTurn: Int, createDialog: MutableGameState => DialogContent)
  extends ConsumableGameEvent(currentTurn)
