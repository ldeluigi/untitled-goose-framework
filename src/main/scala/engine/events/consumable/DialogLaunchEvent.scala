package engine.events.consumable

import model.entities.DialogContent
import model.game.GameState

case class DialogLaunchEvent(turn: Int, cycle: Int, createDialog: GameState => DialogContent)
  extends ConsumableGameEvent
